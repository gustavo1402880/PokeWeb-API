package com.mi80.pokeweb.module.game.application.service.result;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

/**
 * Attack result record
 *
 * <p>Represents the act of attacking between
 * an attacker and a defender in a Pokémon battle</p>
 *
 * @param attackerId Pokémon attacker ID
 * @param attackerNickname Pokémon attacker nickname
 * @param defenderId Pokémon defender ID
 * @param defenderNickname Pokémon defender nickname
 * @param damage Amount of damage the defender sustained
 * @param defenderRemainingHealth Amount of health the defender remaining
 * @param defenderFainted Boolean indicator the defender fainted
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
