package com.mi80.pokeweb.module.pokemon.infrastructure.persistence.impl;

import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import com.mi80.pokeweb.module.pokemon.core.repository.PokemonRepository;
import com.mi80.pokeweb.module.pokemon.infrastructure.persistence.jpa.IJpaPokemonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PokemonRepositoryImpl implements PokemonRepository {
    private final IJpaPokemonRepository jpaPokemonRepository;

    public PokemonRepositoryImpl(IJpaPokemonRepository jpaPokemonRepository) {
        this.jpaPokemonRepository = jpaPokemonRepository;
    }

    @Override
    public List<Pokemon> findAll() {
        return jpaPokemonRepository.findAll();
    }

    @Override
    public Optional<Pokemon> findById(UUID id) {
        return jpaPokemonRepository.findById(id);
    }

    @Override
    public Optional<Pokemon> findByNumberDex(int numberDex) {
        return jpaPokemonRepository.findByNumberDex(numberDex);
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        return jpaPokemonRepository.save(pokemon);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaPokemonRepository.existsById(id);
    }
}
