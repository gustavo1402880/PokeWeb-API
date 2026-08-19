package com.mi80.pokeweb.module.pokemon.infrastructure.persistence.jpa;

import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IJpaPokemonRepository extends JpaRepository<Pokemon, UUID> {

    Optional<Pokemon> findByNumberDex(int numberDex);
}
