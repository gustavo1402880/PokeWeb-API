package com.mi80.pokeweb.module.gym.application.service;

import com.mi80.pokeweb.module.gym.application.exception.EmptyGymTeamException;
import com.mi80.pokeweb.module.gym.application.exception.GymNotFoundException;
import com.mi80.pokeweb.module.gym.core.entity.Gym;
import com.mi80.pokeweb.module.gym.core.repository.GymRepository;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GymService {

    private final GymRepository gymRepository;

    public GymService(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public List<Gym> listAll() {
        return gymRepository.listAll();
    }

    public Gym findById(UUID id) {
        return gymRepository.findById(id).orElseThrow(
                () -> new GymNotFoundException(
                        "Gym was not found by ID: "+id
                )
        );
    }

    public Gym findByGymOrder(int order) {
        return gymRepository.findByGymOrder(order).orElseThrow(
                () -> new GymNotFoundException(
                        "Gym was not found by order: "+order
                )
        );
    }

    public List<Pokemon> findPokemon(UUID id) {
        Gym gym = findById(id);

        if (gym.getPokemon().isEmpty()) {
            throw new EmptyGymTeamException(
                    "Gym has no Pokémon assigned to it."
            );
        }

        return gym.getPokemon();
    }
}
