package com.mi80.pokeweb.entity;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

/**
 * Attack result record
 *
 * <p>Represents the act of attacking between
 * an attacker and a defender in a Pokémon battle</p>
 *
 * @param attackerId
 * @param attackerNickname
 * @param defenderId
 * @param defenderNickname
 * @param damage
 * @param defenderRemainingHealth
 * @param defenderFainted
 */
@Tag(
        name = "Attack result record",
        description = """
                Represents the act of attacking between
                an attacker and a defender in a Pokémon battle
                """
)
public record AttackResult(
        UUID attackerId,
        String attackerNickname,
        UUID defenderId,
        String defenderNickname,
        int damage,
        int defenderRemainingHealth,
        boolean defenderFainted
) {}
