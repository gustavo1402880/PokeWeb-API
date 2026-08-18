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
                .orElseThrow(() -> new RuntimeException("Pokémon was not found"));
    }

    public Pokemon findByDex(int dex) {
        return pokedex.stream()
                .filter(pokemon ->
                        pokemon.getNumberDex() == dex)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pokémon was not found"));
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
                        25, "Pikachu", null,
                        PokemonType.ELECTRIC, null,
                        49, 49, 27, 31, 40,
                        "Mouse Pokémon", 15,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        133, "Eevee", null,
                        PokemonType.NORMAL, null,
                        43, 43, 19, 20, 21,
                        "Evolution Pokémon", 14,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        123, "Scyther", null,
                        PokemonType.BUG, PokemonType.FLYING,
                        74, 74, 64, 48, 61,
                        "Mantis Pokémon", 27,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        74, "Geodude", null,
                        PokemonType.ROCK, PokemonType.GROUND,
                        45, 60, 20, 15, 30,
                        "Rock Head Pokémon", 12,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        95, "Onix", null,
                        PokemonType.ROCK, PokemonType.GROUND,
                        45, 90, 25, 40, 45,
                        "Rock Snake Pokémon", 14,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        120, "Staryu", null,
                        PokemonType.WATER, null,
                        30, 45, 50, 60, 35,
                        "Star Shape Pokémon", 18,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        121, "Starmie", null,
                        PokemonType.WATER, PokemonType.PSYCHIC,
                        55, 75, 85, 95, 50,
                        "Mysterious Pokémon", 21,
                        EvolutionStage.STAGE_1, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        100, "Voltorb", null,
                        PokemonType.ELECTRIC, null,
                        30, 30, 45, 80, 30,
                        "Ball Pokémon", 21,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        26, "Raichu", null,
                        PokemonType.ELECTRIC, null,
                        70, 55, 70, 90, 55,
                        "Mouse Pokémon", 24,
                        EvolutionStage.STAGE_1, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        114, "Tangela", null,
                        PokemonType.GRASS, null,
                        45, 85, 70, 50, 45,
                        "Vine Pokémon", 29,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        45, "Vileplume", null,
                        PokemonType.GRASS, PokemonType.POISON,
                        65, 75, 85, 40, 60,
                        "Flower Pokémon", 29,
                        EvolutionStage.STAGE_2, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        109, "Koffing", null,
                        PokemonType.POISON, null,
                        45, 75, 45, 25, 35,
                        "Poison Gas Pokémon", 37,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        89, "Muk", null,
                        PokemonType.POISON, null,
                        80, 50, 55, 35, 70,
                        "Sludge Pokémon", 39,
                        EvolutionStage.STAGE_1, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        64, "Kadabra", null,
                        PokemonType.PSYCHIC, null,
                        35, 25, 85, 75, 40,
                        "Psi Pokémon", 38,
                        EvolutionStage.STAGE_1, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        65, "Alakazam", null,
                        PokemonType.PSYCHIC, null,
                        50, 40, 105, 95, 50,
                        "Psi Pokémon", 43,
                        EvolutionStage.STAGE_2, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        58, "Growlithe", null,
                        PokemonType.FIRE, null,
                        65, 45, 50, 55, 50,
                        "Puppy Pokémon", 42,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        59, "Arcanine", null,
                        PokemonType.FIRE, null,
                        95, 70, 80, 85, 80,
                        "Legendary Pokémon", 47,
                        EvolutionStage.STAGE_1, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        111, "Rhyhorn", null,
                        PokemonType.GROUND, PokemonType.ROCK,
                        70, 65, 25, 25, 60,
                        "Spikes Pokémon", 45,
                        EvolutionStage.BASIC, BattlePosition.FRONT
                )
        );
        pokedex.add(
                new Pokemon(
                        34, "Nidoking", null,
                        PokemonType.POISON, PokemonType.GROUND,
                        81, 65, 75, 75, 70,
                        "Drill Pokémon", 50,
                        EvolutionStage.STAGE_2, BattlePosition.FRONT
                )
        );
    }

    private static int calculateDamage(Pokemon attacker, Pokemon defender) {
        int rawDamage = attacker.getAttack() - (defender.getDefense() / 2);
        return Math.max(1, rawDamage);
    }
}
