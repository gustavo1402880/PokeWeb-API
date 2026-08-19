package com.mi80.pokeweb.module.game.application.service.result;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Battle turn record
 *
 * <p>Represents a turn in a Pokémon
 * battle between an attacker and a defender</p>
 *
 * @param turn Battle turn index
 * @param attacker Battle turn attacker ID
 * @param defender Battle turn defender ID
 * @param damage Amount of damage the defender sustained
 * @param defenderRemainingHealth Amount of health the defender remaining
 * @param defenderFainted Boolean indicator the defender fainted
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
