package com.mi80.pokeweb.entity;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Battle result record
 *
 * <p>Represents the result of a
 * Pokémon battle, including the battle history</p>
 *
 * @param winner Battle winner ID
 * @param loser Battle loser ID
 * @param turns Amount of turn the battle lasted
 * @param battleHistory List of BattleTurn representing battle history
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
