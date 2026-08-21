package com.mi80.pokeweb.module.game.infrastructure.persistence.impl;

import com.mi80.pokeweb.module.game.core.entity.Game;
import com.mi80.pokeweb.module.game.core.repository.GameRepository;
import com.mi80.pokeweb.module.game.infrastructure.persistence.jpa.IJpaGameRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final IJpaGameRepository jpaGameRepository;

    @Override
    public Game save(Game game) {
        return jpaGameRepository.save(game);
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return jpaGameRepository.findById(id);
    }
}
