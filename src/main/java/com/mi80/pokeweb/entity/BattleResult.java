package com.mi80.pokeweb.entity;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Battle result record
 *
 * <p>Represents the result of a
 * Pokémon battle, including the battle history</p>
 *
 * @param winner
 * @param loser
 * @param turns
 * @param battleHistory
 */
@Tag(
        name = "Battle result record",
        description = """
                Represents the result of a
                Pokémon battle, including the battle history
                """
)
public record BattleResult(
        String winner,
        String loser,
        int turns,
        List<BattleTurn> battleHistory
) {}
