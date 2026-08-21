package com.mi80.pokeweb.module.game.infrastructure.persistence.jpa;

import com.mi80.pokeweb.module.game.core.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJpaGameRepository extends JpaRepository<Game, UUID> {
}
