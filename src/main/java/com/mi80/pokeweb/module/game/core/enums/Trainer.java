package com.mi80.pokeweb.module.game.core.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Trainer enum
 *
 * <p>Represents the Trainer who acts as the game's protagonist</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Trainer enum",
        description = """
                Represents the Trainer who acts
                as the game's protagonist
                """
)
public enum Trainer {
    ASH,
    RED,
    GOLD,
    BRENDAN,
    MAY,
    CARLOS
}
