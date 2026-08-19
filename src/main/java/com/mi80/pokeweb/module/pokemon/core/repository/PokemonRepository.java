package com.mi80.pokeweb.module.pokemon.core.repository;

import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PokemonRepository {
    List<Pokemon> findAll();

    Optional<Pokemon> findById(UUID id);

    Optional<Pokemon> findByNumberDex(int numberDex);

    Pokemon save(Pokemon pokemon);

    boolean  existsById(UUID id);
}
