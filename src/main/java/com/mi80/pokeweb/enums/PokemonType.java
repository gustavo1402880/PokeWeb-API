package com.mi80.pokeweb.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Pokemon type enum
 *
 * <p>Represents the type of a Pokémon</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Pokemon type enum",
        description = """
                Represents the type of a Pokémon
                """
)
public enum PokemonType {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY
}
