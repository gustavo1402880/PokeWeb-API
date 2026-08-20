package com.mi80.pokeweb.module.gym.infrastructure.persistence.impl;

import com.mi80.pokeweb.module.gym.core.entity.Gym;
import com.mi80.pokeweb.module.gym.core.repository.GymRepository;
import com.mi80.pokeweb.module.gym.infrastructure.persistence.jpa.IJpaGymRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GymRepositoryImpl implements GymRepository {

    private final IJpaGymRepository jpaGymRepository;

    @Override
    public List<Gym> listAll() {
        return jpaGymRepository.findAll();
    }

    @Override
    public Optional<Gym> findById(UUID id) {
        return jpaGymRepository.findById(id);
    }

    @Override
    public Optional<Gym> findByGymOrder(int order) {
        return jpaGymRepository.findByGymOrder(order);
    }

    @Override
    public boolean existsByGymOrder(int order) {
        return false;
    }

    @Override
    public boolean existsByGymId(UUID id) {
        return false;
    }
}
