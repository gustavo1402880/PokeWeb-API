package com.mi80.pokeweb.module.gym.core.repository;

import com.mi80.pokeweb.module.gym.core.entity.Gym;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GymRepository {
    List<Gym> listAll();

    Optional<Gym> findById(UUID id);

    Optional<Gym> findByGymOrder(int order);

    boolean existsByGymOrder(int order);

    boolean existsByGymId(UUID id);
}
