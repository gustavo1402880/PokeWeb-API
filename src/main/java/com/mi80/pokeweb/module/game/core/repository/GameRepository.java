package com.mi80.pokeweb.module.game.core.repository;

import com.mi80.pokeweb.module.game.core.entity.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository {
    Game save(Game game);

    Optional<Game> findById(UUID id);
}
