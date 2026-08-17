package com.mi80.pokeweb.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Battle position enum
 *
 * <p>Represents the battle position of a Pokémon</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Battle position enum",
        description = """
                Represents the battle position of a Pokémon
                """
)
public enum BattlePosition {
    FRONT,
    BACK
}
