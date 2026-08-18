package com.mi80.pokeweb.service;

import com.mi80.pokeweb.entity.AttackResult;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.enums.BattlePosition;
import com.mi80.pokeweb.enums.EvolutionStage;
import com.mi80.pokeweb.enums.PokemonType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PokemonService {

    private static final List<Pokemon> pokedex =
            new CopyOnWriteArrayList<>();

    public PokemonService() {
        seedPokemons();
    }

    public List<Pokemon> findAll() {
        return List.copyOf(pokedex);
    }

    public Pokemon findById(UUID id) {
        return pokedex.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pokémon not found"));
    }

    public AttackResult attack(
            UUID attackerId,
            UUID defenderId
    ) {
        if (attackerId.equals(defenderId)) {
            throw new RuntimeException("A Pokémon cannot attack itself");
        }

        Pokemon attacker = findById(attackerId);
        Pokemon defender = findById(defenderId);

        if (attacker.isFainted()) {
            throw new RuntimeException("A Fainted Pokémon cannot attack");
        }

        int damage = calculateDamage(attacker, defender);

        return new AttackResult(
                attacker.getId(),
                attacker.getNickname(),
                defender.getId(),
                defender.getNickname(),
                damage,
                defender.getCurrentHealth(),
                defender.isFainted()
        );
    }

    public boolean dodge(
            UUID defenderId,
            UUID attackerId
    ) {
        Pokemon defender = findById(defenderId);
        Pokemon attacker = findById(attackerId);

        if (defender.isFainted()) {
            throw  new RuntimeException("A Fainted Pokémon cannot dodge");
        }

        double dodgeChance = ((double) defender.getSpeed()
                / defender.getSpeed() + attacker.getSpeed()) * 0.5;

        return Math.random() < dodgeChance;
    }

    public boolean flee(
            UUID pokemonId,
            UUID opponentId
    ) {
        Pokemon pokemon = findById(pokemonId);
        Pokemon opponent = findById(opponentId);

        if (pokemon.isFainted()) {
            throw  new RuntimeException("A Fainted Pokémon cannot flee");
        }

        double dodgeChance = ((double) pokemon.getSpeed()
                / pokemon.getSpeed() + opponent.getSpeed()) * 0.2;

        return Math.random() < dodgeChance;
    }

    public Pokemon levelUp(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.levelUp();
        return pokemon;
    }

    public Pokemon evolve(
           UUID pokemonId,
           Pokemon pokemonEvolved
    ) {
        Pokemon pokemon = findById(pokemonId);
        BattlePosition battlePosition = pokemon.getBattlePosition();

        switch (pokemon.getEvolutionStage()) {
            case BASIC -> {
                if (pokemon.getLevel() < 16) {
                    throw new RuntimeException("16 level is required to evolve a BASIC Pokémon");
                }
                pokemon = pokemonEvolved;
                pokemon.setEvolutionStage(EvolutionStage.STAGE_1);
                pokemon.setBattlePosition(battlePosition);
            }
            case STAGE_1 -> {
                if (pokemon.getLevel() < 36) {
                    throw  new RuntimeException("36 level is required to evolve a STAGE_1 Pokémon");
                }
                pokemon = pokemonEvolved;
                pokemon.setEvolutionStage(EvolutionStage.STAGE_2);
                pokemon.setBattlePosition(battlePosition);
            }
            case STAGE_2 -> throw new RuntimeException("Pokémon is already on max evolution stage");
        }

        return pokemon;
    }

    public Pokemon movePosition(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.movePosition();
        return pokemon;
    }

    public Pokemon heal(
            UUID pokemonId,
            int amount
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.heal(amount);
        return pokemon;
    }

    public Pokemon fullHeal(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.fullHeal();
        return pokemon;
    }

    private static void seedPokemons() {
        pokedex.add(
                new Pokemon(
                        0025, "Pikachu", null,
                        PokemonType.ELECTRIC, null,
                        49, 49, 27, 31, 40,
                        "Mouse Pokémon", 15,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        0133, "Eevee", null,
                        PokemonType.NORMAL, null,
                        43, 43, 19, 20, 21,
                        "Evolution Pokémon", 14,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        0123, "Scyther", null,
                        PokemonType.BUG, PokemonType.FLYING,
                        74, 74, 64, 48, 61,
                        "Mantis Pokémon", 27,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
    }

    private static int calculateDamage(Pokemon attacker, Pokemon defender) {
        int rawDamage = attacker.getAttack() - (defender.getDefense() / 2);
        return Math.max(1, rawDamage);
    }
}
