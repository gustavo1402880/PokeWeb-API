package com.mi80.pokeweb.module.gym.core.enums;

import com.mi80.pokeweb.module.pokemon.core.enums.PokemonType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

/**
 * Gym leader enum
 *
 * <p>Represents the GymLeader who defends the Gym against Trainer</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Getter
@Tag(
        name = "Gym leader enum",
        description = """
                Represents the GymLeader who
                defends the Gym against Trainer
                """
)
public enum GymLeader {
    BROCK(PokemonType.ROCK),
    MISTY(PokemonType.WATER),
    LT_SURGE(PokemonType.ELECTRIC),
    ERIKA(PokemonType.GRASS),
    KOGA(PokemonType.POISON),
    SABRINA(PokemonType.PSYCHIC),
    BLAINE(PokemonType.FIRE),
    GIOVANNI(PokemonType.GROUND);

    private final PokemonType speciality;

    GymLeader(PokemonType speciality) {this.speciality = speciality;}
}
