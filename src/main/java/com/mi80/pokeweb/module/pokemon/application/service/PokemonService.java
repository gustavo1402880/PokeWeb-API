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
            UUID attackerId,
            UUID defenderId
    ) {
        if (attackerId.equals(defenderId)) {
            throw new SamePokemonException("A Pokémon cannot attack itself");
        }

        Pokemon attacker = findById(attackerId);
        Pokemon defender = findById(defenderId);

        if (attacker.isFainted()) {
            throw new FaintedPokemonException("A Fainted Pokémon cannot attack");
        }

        int damage = calculateDamage(attacker, defender);

        defender.takeDamage(damage);

        defender = repository.save(defender);

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
            throw  new FaintedPokemonException("A Fainted Pokémon cannot dodge");
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
            throw  new FaintedPokemonException("A Fainted Pokémon cannot flee");
        }

        double fleeChance = ((double) (pokemon.getSpeed() + pokemon.getLevel())
                / (pokemon.getSpeed() + pokemon.getLevel()) + (opponent.getSpeed() + opponent.getLevel()) * 0.2);

        return Math.random() < fleeChance;
    }

    public Pokemon levelUp(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.levelUp();

        repository.save(pokemon);

        return repository.save(pokemon);
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
                    throw new BelowRequiredLevelException("16 level is required to evolve a BASIC Pokémon");
                }
                pokemon = pokemonEvolved;
                pokemon.setEvolutionStage(EvolutionStage.STAGE_1);
                pokemon.setBattlePosition(battlePosition);
            }
            case STAGE_1 -> {
                if (pokemon.getLevel() < 36) {
                    throw  new BelowRequiredLevelException("36 level is required to evolve a STAGE_1 Pokémon");
                }
                pokemon = pokemonEvolved;
                pokemon.setEvolutionStage(EvolutionStage.STAGE_2);
                pokemon.setBattlePosition(battlePosition);
            }
            case STAGE_2 -> throw new MaxEvolutionStageException("Pokémon is already on max evolution stage");
        }

        return repository.save(pokemon);
    }

    public Pokemon movePosition(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.movePosition();

        return repository.save(pokemon);
    }

    public Pokemon heal(
            UUID pokemonId,
            int amount
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.heal(amount);

        return repository.save(pokemon);
    }

    public Pokemon fullHeal(
            UUID pokemonId
    ) {
        Pokemon pokemon = findById(pokemonId);
        pokemon.fullHeal();

        return repository.save(pokemon);
    }

    private static int calculateDamage(Pokemon attacker, Pokemon defender) {
        int rawDamage =
                attacker.getAttack() - (defender.getDefense() / 2);
        return Math.max(1, rawDamage);
    }
}
