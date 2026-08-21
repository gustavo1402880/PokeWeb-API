package com.mi80.pokeweb.module.pokemon.application.service;

import com.mi80.pokeweb.module.game.application.service.result.AttackResult;
import com.mi80.pokeweb.module.pokemon.application.exception.*;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import com.mi80.pokeweb.module.pokemon.core.enums.BattlePosition;
import com.mi80.pokeweb.module.pokemon.core.enums.EvolutionStage;
import com.mi80.pokeweb.module.pokemon.core.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PokemonService {

    private final PokemonRepository repository;

    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    public Pokemon create(Pokemon pokemon) {
        return repository.save(pokemon);
    }

    public List<Pokemon> findAll() {
        return repository.findAll();
    }

    public Pokemon findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new PokemonNotFoundException(
                                "Pokémon was not found by ID: "+id)
                );
    }

    public Pokemon findByDex(int dex) {
        return repository.findByNumberDex(dex)
                .orElseThrow(
                        () -> new PokemonNotFoundException(
                                "Pokemon was not found by dex number: "+dex)
                );
    }

    public AttackResult attack(
            Pokemon attacker,
            Pokemon defender
    ) {
        if (attacker.isFainted()) {
            throw new FaintedPokemonException("A Fainted Pokémon cannot attack");
        }

        int damage = calculateDamage(attacker, defender);

        defender.takeDamage(damage);

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
            Pokemon defender,
            Pokemon attacker
    ) {
        if (defender.isFainted()) {
            throw  new FaintedPokemonException("A Fainted Pokémon cannot dodge");
        }

        double dodgeChance = ((double) defender.getSpeed()
                / defender.getSpeed() + attacker.getSpeed()) * 0.5;

        return Math.random() < dodgeChance;
    }

    public boolean flee(
            Pokemon pokemon,
            Pokemon opponent
    ) {
        if (pokemon.isFainted()) {
            throw  new FaintedPokemonException("A Fainted Pokémon cannot flee");
        }

        double fleeChance = ((double) (pokemon.getSpeed() + pokemon.getLevel())
                / (pokemon.getSpeed() + pokemon.getLevel()) + (opponent.getSpeed() + opponent.getLevel()) * 0.2);

        return Math.random() < fleeChance;
    }

    public Pokemon levelUp(
            Pokemon pokemon
    ) {
        pokemon.levelUp();

        return pokemon;
    }

    public Pokemon evolve(
           Pokemon pokemon,
           Pokemon pokemonEvolved
    ) {
        switch (pokemon.getEvolutionStage()) {
            case BASIC -> {
                if (pokemon.getLevel() < 16) {
                    throw new BelowRequiredLevelException("16 level is required to evolve a BASIC Pokémon");
                }
                applyEvolution(
                        pokemon,
                        pokemonEvolved,
                        EvolutionStage.STAGE_1,
                        pokemon.getBattlePosition()
                );
            }
            case STAGE_1 -> {
                if (pokemon.getLevel() < 36) {
                    throw  new BelowRequiredLevelException("36 level is required to evolve a STAGE_1 Pokémon");
                }
                applyEvolution(
                        pokemon,
                        pokemonEvolved,
                        EvolutionStage.STAGE_2,
                        pokemon.getBattlePosition()
                );
            }
            case STAGE_2 -> throw new MaxEvolutionStageException("Pokémon is already on max evolution stage");
        }

        return pokemon;
    }

    public Pokemon movePosition(
            Pokemon pokemon
    ) {
        pokemon.movePosition();

        return pokemon;
    }

    public Pokemon heal(
            Pokemon pokemon,
            int amount
    ) {
        pokemon.heal(amount);

        return pokemon;
    }

    public Pokemon fullHeal(
            Pokemon pokemon
    ) {
        pokemon.fullHeal();

        return pokemon;
    }

    private static int calculateDamage(Pokemon attacker, Pokemon defender) {
        int rawDamage =
                attacker.getAttack() - (defender.getDefense() / 2);
        return Math.max(1, rawDamage);
    }

    private void applyEvolution(
            Pokemon pokemon,
            Pokemon evolved,
            EvolutionStage newStage,
            BattlePosition battlePosition
    ) {
        pokemon.setNumberDex(
                evolved.getNumberDex()
        );

        pokemon.setName(
                evolved.getName()
        );

        pokemon.setPrimaryType(
                evolved.getPrimaryType()
        );

        pokemon.setSecondaryType(
                evolved.getSecondaryType()
        );

        pokemon.setMaxHealth(
                evolved.getMaxHealth()
        );

        pokemon.setCurrentHealth(
                evolved.getMaxHealth()
        );

        pokemon.setAttack(
                evolved.getAttack()
        );

        pokemon.setDefense(
                evolved.getDefense()
        );

        pokemon.setSpeed(
                evolved.getSpeed()
        );

        pokemon.setClassification(
                evolved.getClassification()
        );

        pokemon.setEvolutionStage(
                newStage
        );

        pokemon.setBattlePosition(
                battlePosition
        );
    }
}
