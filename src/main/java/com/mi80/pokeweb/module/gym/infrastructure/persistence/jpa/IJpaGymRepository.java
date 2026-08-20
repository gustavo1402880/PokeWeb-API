package com.mi80.pokeweb.module.gym.infrastructure.persistence.jpa;

import com.mi80.pokeweb.module.gym.core.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IJpaGymRepository extends JpaRepository<Gym, UUID> {
    Optional<Gym> findByGymOrder(int gymOrder);
}
