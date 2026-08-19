package com.mi80.pokeweb.module.pokemon.core.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Evolution stage enum
 *
 * <p>Represents the evolution stage of a Pokémon</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Evolution stage enum",
        description = """
                Represents the evolution stage of a Pokémon
                """
)
public enum EvolutionStage {
    BASIC,
    STAGE_1,
    STAGE_2
}
