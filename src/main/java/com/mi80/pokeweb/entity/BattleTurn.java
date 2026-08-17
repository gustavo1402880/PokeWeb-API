package com.mi80.pokeweb.entity;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Battle turn record
 *
 * <p>Represents a turn in a Pokémon
 * battle between an attacker and a defender</p>
 *
 * @param turn
 * @param attacker
 * @param defender
 * @param damage
 * @param defenderRemainingHealth
 * @param defenderFainted
 */
@Tag(
        name = "Battle turn record",
        description = """
                Represents a turn in a Pokémon battle
                between an attacker and a defender
                """
)
public record BattleTurn(
        int turn,
        String attacker,
        String defender,
        int damage,
        int defenderRemainingHealth,
        boolean defenderFainted
) {}
