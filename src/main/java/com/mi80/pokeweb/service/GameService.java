package com.mi80.pokeweb.service;

import com.mi80.pokeweb.entity.*;
import com.mi80.pokeweb.enums.ItemType;
import com.mi80.pokeweb.enums.Trainer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GameService {

    private static final int MAX_TEAM_SIZE = 6;
    private static final int MAX_BATTLE_TURNS = 100;
    private static final int[] STARTERS_DEX_ARRAY = {1, 4, 7, 25, 133};

    private final List<Game> games = new CopyOnWriteArrayList<>();

    private final PokemonService pokemonService;
    private final GymService gymService;

    public GameService(
            PokemonService pokemonService,
            GymService gymService
    ) {
        this.pokemonService = pokemonService;
        this.gymService = gymService;
    }

    public Game createNewGame(Trainer trainer) {
        Game newGame = new Game(
                trainer,
                gymService.findByGymOrder(1),
                1000
        );

        Random random = new Random();
        int selectedIndex = random.nextInt(STARTERS_DEX_ARRAY.length);

        newGame.getTeam().add(
                pokemonService.findByDex(
                        STARTERS_DEX_ARRAY[selectedIndex]
                )
        );
        newGame.getTeam().add(
                pokemonService.findByDex(133)
        );

        games.add(newGame);

        return newGame;
    }

    public Game findGameById(UUID id) {
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Game was not found"));
    }

    public List<Pokemon> findGameTeam(UUID id) {
        Game game = findGameById(id);

        return List.copyOf(game.getTeam());
    }

    public Map<ItemType, Integer> findGameInventory(UUID id) {
        Game game = findGameById(id);

        return Map.copyOf(game.getInventory());
    }

    public int findGameCoins(UUID id) {
        Game game = findGameById(id);

        return game.getCoins();
    }

    public Game moveToGym(UUID id) {
        Game game = findGameById(id);
        int nextGymOrder =
                game.getCurrentGym()
                        .getGymOrder() + 1;

        Gym nextGym = gymService.findByGymOrder(nextGymOrder);
        nextGym.setChallenger(game.getTrainer());

        game.setCurrentGym(nextGym);

        return game;
    }

    public Game capturePokemon(UUID id, UUID firstPokemonId, UUID wildPokemonId) {
        Game game = findGameById(id);

        Pokemon pokemon = pokemonService.findById(wildPokemonId);

        if (game.getTeam().size() >= MAX_TEAM_SIZE) {
            throw new RuntimeException("Team already has the maximum number of Pokémon");
        }

        boolean hasFled = pokemonService.flee(
                wildPokemonId,
                firstPokemonId
        );

        if (hasFled) {
            throw new RuntimeException("Wild Pokémon fled the battle");
        }

        pokemon.movePosition();
        game.getTeam().add(pokemon);

        return game;
    }

    public Game buyItem(UUID id, ItemType item) {
        Game game = findGameById(id);
        int price = item.getPrice();

        if (game.getCoins() < price) {
            throw new RuntimeException("Insufficient coins. Required: "+price+
                    ", Available: "+game.getCoins());
        }

        game.setCoins(game.getCoins() - price);

        game.getInventory().merge(item, 1, Integer::sum);

        return game;
    }

    public Pokemon useItem(UUID id, UUID pokemonId, ItemType item) {
        Game game = findGameById(id);
        requirePokemonInTeam(game, pokemonId);

        int quantity = game.getInventory().getOrDefault(item, 0);
        if (quantity <= 0) {
            throw new RuntimeException("Item does not exists in the inventory");
        }

        Pokemon healedPokemon = pokemonService.heal(pokemonId, item.getHealingAmount());

        game.getInventory().put(item, quantity - 1);
        savePokemonStatus(game, healedPokemon);

        return healedPokemon;
    }

    public BattleResult startBattle(UUID teamPokemonId, UUID opponentPokemonId) {
        if (teamPokemonId.equals(opponentPokemonId)) {
            throw new RuntimeException("A battle requires two different Pokémon");
        }

        Pokemon firstPokemon = pokemonService.findById(teamPokemonId);
        Pokemon secondPokemon = pokemonService.findById(opponentPokemonId);

        if (firstPokemon.isFainted() || secondPokemon.isFainted()) {
            throw new RuntimeException("A fainted Pokémon cannot start a battle");
        }

        Pokemon attacker;
        Pokemon defender;

        if (secondPokemon.getSpeed() > firstPokemon.getSpeed()) {
            attacker = secondPokemon;
            defender = firstPokemon;
        } else {
            attacker = firstPokemon;
            defender = secondPokemon;
        }

        List<BattleTurn> battleHistory = new ArrayList<>();
        int turns = 0;

        while (!firstPokemon.isFainted()
            && !secondPokemon.isFainted()
            && turns < MAX_BATTLE_TURNS) {

            turns++;

            AttackResult attack = pokemonService.attack(
                    attacker.getId(),
                    defender.getId()
            );

            battleHistory.add(new BattleTurn(
                    turns,
                    attack.attackerNickname(),
                    attack.defenderNickname(),
                    attack.damage(),
                    attack.defenderRemainingHealth(),
                    attack.defenderFainted())
            );

            if (attack.defenderFainted()) {
                break;
            }

            Pokemon previousAttacker = attacker;
            attacker = defender;
            defender = previousAttacker;
        }

        Pokemon winner;
        Pokemon loser;

        if (firstPokemon.isFainted()) {
            winner = secondPokemon;
            loser = firstPokemon;
        } else if (secondPokemon.isFainted()) {
            winner = firstPokemon;
            loser = secondPokemon;
        } else {
            throw new RuntimeException("The battle has reached the maximum turn limit");
        }

        return new BattleResult(
                winner.getName(),
                loser.getName(),
                turns,
                List.copyOf(battleHistory)
        );
    }

    private List<BattleResult> challengeGymLeader(UUID id) {
        Game game = findGameById(id);
        List<BattleResult> battleResults = new ArrayList<>();

        List<Pokemon> trainerTeam = game.getTeam();

        Gym gym = game.getCurrentGym();
        List<Pokemon> gymTeam = gym.getPokemon();

        while (
                hasAvailablePokemon(trainerTeam) &&
                        hasAvailablePokemon(gymTeam)) {
            Pokemon trainerPokemon = getNextAvailablePokemon(trainerTeam);
            Pokemon gymPokemon = getNextAvailablePokemon(gymTeam);

            Pokemon attacker;
            Pokemon defender;

            if (gymPokemon.getSpeed() > trainerPokemon.getSpeed()) {
                attacker = gymPokemon;
                defender = trainerPokemon;
            } else {
                attacker = trainerPokemon;
                defender = gymPokemon;
            }

            List<BattleTurn> battleHistory = new ArrayList<>();
            int turns = 0;

            while (!trainerPokemon.isFainted()
                    && !gymPokemon.isFainted()
                    && turns < MAX_BATTLE_TURNS) {

                turns++;

                AttackResult attack = pokemonService.attack(
                        attacker.getId(),
                        defender.getId()
                );

                battleHistory.add(new BattleTurn(
                        turns,
                        attack.attackerNickname(),
                        attack.defenderNickname(),
                        attack.damage(),
                        attack.defenderRemainingHealth(),
                        attack.defenderFainted())
                );

                if (defender.isFainted()) {
                    break;
                }

                Pokemon previousAttacker = attacker;
                attacker = defender;
                defender = previousAttacker;
            }

            Pokemon winner;
            Pokemon loser;

            if (trainerPokemon.isFainted()) {
                winner = gymPokemon;
                loser = trainerPokemon;
            } else if (gymPokemon.isFainted()) {
                winner = trainerPokemon;
                loser = gymPokemon;
            } else {
                throw new RuntimeException("The battle has reached the maximum turn limit");
            }
        }

        return battleResults;
    }

    private Pokemon getNextAvailablePokemon(List<Pokemon> team) {
        return team.stream()
                .filter(pokemon -> !pokemon.isFainted())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no active Pokémon"));
    }

    private boolean hasAvailablePokemon(List<Pokemon> team) {
        return team.stream()
                .anyMatch(pokemon -> !pokemon.isFainted());
    }

    private Pokemon requirePokemonInTeam(Game game, UUID pokemonId) {
        return game.getTeam().stream()
                .filter(pokemon -> pokemon.getId().equals(pokemonId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pokémon is not on the team"));
    }

    private void savePokemonStatus(Game game, Pokemon pokemon) {
        requirePokemonInTeam(game, pokemon.getId());

        int pokeIndex = game.getTeam().indexOf(pokemon);

        game.getTeam().set(pokeIndex, pokemon);
    }
}
