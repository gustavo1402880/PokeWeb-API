package com.mi80.pokeweb.service;

import com.mi80.pokeweb.entity.AttackResult;
import com.mi80.pokeweb.entity.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PokemonService {

    public List<Pokemon> findAll() {

    }

    public Pokemon findById(UUID id) {

    }

    public AttackResult attack(
            UUID attackerId,
            UUID defenderId
    ) {

    }

    public boolean dodge(
            UUID defenderId,
            UUID attackerId
    ) {

    }

    public boolean flee(
            UUID pokemonId,
            UUID opponentId
    ) {

    }

    public Pokemon levelUp(
            UUID pokemonId
    ) {

    }

    public Pokemon evolve(
           UUID pokemonId,
           Pokemon pokemonEvolved
    ) {

    }

    public Pokemon movePosition(
            UUID pokemonId
    ) {

    }
}
