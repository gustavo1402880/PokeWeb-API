package com.mi80.pokeweb.module.game.application.service;

import com.mi80.pokeweb.module.game.application.exception.EmptyPokemonTeamException;
import com.mi80.pokeweb.module.game.application.exception.GameNotFoundException;
import com.mi80.pokeweb.module.game.application.service.result.AttackResult;
import com.mi80.pokeweb.module.game.application.service.result.BattleResult;
import com.mi80.pokeweb.module.game.application.service.result.BattleTurn;
import com.mi80.pokeweb.module.game.core.entity.Game;
import com.mi80.pokeweb.module.game.core.enums.ItemType;
import com.mi80.pokeweb.module.game.core.enums.Trainer;
import com.mi80.pokeweb.module.game.core.repository.GameRepository;
import com.mi80.pokeweb.module.gym.core.entity.Gym;
import com.mi80.pokeweb.module.pokemon.application.service.PokemonService;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import com.mi80.pokeweb.module.gym.application.service.GymService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GameService {

    private static final int MAX_TEAM_SIZE = 6;
    private static final int MAX_BATTLE_TURNS = 100;
    private static final int[] STARTERS_DEX_ARRAY = {1, 4, 7, 25, 133};

    private final GameRepository repository;
    private final GymService gymService;
    private final PokemonService pokemonService;

    public GameService(
            GameRepository repository,
            GymService gymService,
            PokemonService pokemonService
    ) {
        this.repository = repository;
        this.gymService = gymService;
        this.pokemonService = pokemonService;
    }

    public Game createNewGame(Trainer trainer) {
        Random random = new Random();

        int selectedIndex =
                random.nextInt(STARTERS_DEX_ARRAY.length);

        int starterDex =
                STARTERS_DEX_ARRAY[selectedIndex];

        Pokemon pokemonStarter =
                pokemonService.findByDex(starterDex);

        Pokemon pokemonSecondStarter =
                pokemonService.findByDex(133);


        Gym firstGym =
                gymService.findByGymOrder(1);

        Game newGame = Game.builder()
                .trainer(trainer)
                .currentGym(firstGym)
                .coins(1000)
                .build();

        newGame.getTeam().add(pokemonStarter);
        newGame.getTeam().add(pokemonSecondStarter);

        return repository.save(newGame);
    }

    public Game findGameById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new GameNotFoundException(
                        "Game was not found"
                )
        );
    }

    public List<Pokemon> findGameTeam(UUID id) {
        Game game = findGameById(id);

        if (game.getTeam() == null || game.getTeam().isEmpty()) {
            throw new EmptyPokemonTeamException(
                    "Pokémon team has been found empty or null"
            );
        }

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

        game =  repository.save(game);

        return game;
    }

    public Game capturePokemon(UUID id, UUID wildPokemonId) {
        Game game = findGameById(id);

        Pokemon trainerPokemon = getNextAvailablePokemon(game.getTeam());
        Pokemon pokemon = pokemonService.findById(wildPokemonId);


        if (game.getTeam().size() >= MAX_TEAM_SIZE) {
            throw new RuntimeException("Team already has the maximum number of Pokémon");
        }

        boolean hasFled = pokemonService.flee(
                pokemon,
                trainerPokemon
        );

        if (hasFled) {
            throw new RuntimeException("Wild Pokémon fled the battle");
        }

        pokemon.movePosition();
        game.getTeam().add(pokemon);

        game = repository.save(game);

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

        game = repository.save(game);

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

    public Pokemon heal(UUID id, UUID pokemonId) {
        Game game = findGameById(id);
        requirePokemonInTeam(game, pokemonId);

        return pokemonService.fullHeal(pokemonId);
    }

    public BattleResult battle(Pokemon firstPokemon, Pokemon secondPokemon) {
        if (firstPokemon.getId().equals(
                secondPokemon.getId())
        ) {
            throw new RuntimeException(
                    "A battle requires two different Pokémon"
            );
        }

        if (firstPokemon.isFainted()
                || secondPokemon.isFainted()) {
            throw new RuntimeException(
                    "A fainted Pokémon cannot start a battle"
            );
        }

        Pokemon attacker;
        Pokemon defender;

        if (secondPokemon.getSpeed()
                > firstPokemon.getSpeed()) {
            attacker = secondPokemon;
            defender = firstPokemon;
        } else {
            attacker = firstPokemon;
            defender = secondPokemon;
        }

        List<BattleTurn> battleHistory =
                new ArrayList<>();
        int turns = 0;

        while (!firstPokemon.isFainted()
            && !secondPokemon.isFainted()
            && turns < MAX_BATTLE_TURNS
        ) {
            turns++;

            boolean dogded =
                    pokemonService.dodge(
                            defender,
                            attacker
                    );

            if (dogded) {

                battleHistory.add(
                        new BattleTurn(
                                turns,
                                attacker.getNickname(),
                                defender.getNickname(),
                                0,
                                defender.getCurrentHealth(),
                                false
                        )
                );
            } else {
                AttackResult attack = pokemonService.attack(
                        attacker,
                        defender
                );

                battleHistory.add(new BattleTurn(
                        turns,
                        attack.attackerNickname(),
                        attack.defenderNickname(),
                        attack.damage(),
                        attack.defenderRemainingHealth(),
                        attack.defenderFainted())
                );
            }

            if (defender.isFainted()) {
                break;
            }

            Pokemon previousAttacker = attacker;

            attacker = defender;
            defender = previousAttacker;
        }

        if (!firstPokemon.isFainted()
                && !secondPokemon.isFainted()) {
            throw new RuntimeException(
                    "The battle has reached the maximum turn limit"
            );
        }

        Pokemon winner =
                firstPokemon.isFainted()
                        ? secondPokemon
                        : firstPokemon;
        Pokemon loser =
                firstPokemon.isFainted()
                        ? firstPokemon
                        : secondPokemon;

        return new BattleResult(
                winner.getName(),
                loser.getName(),
                turns,
                List.copyOf(battleHistory)
        );
    }

    public BattleResult startbattle(
            UUID id,
            UUID firstPokemonId,
            UUID secondPokemonId
    ) {

    }

    public List<BattleResult> challengeGymLeader(UUID id) {
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

            battleResults.add( new BattleResult(
                    winner.getName(),
                    loser.getName(),
                    turns,
                    List.copyOf(battleHistory)
            ));
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
