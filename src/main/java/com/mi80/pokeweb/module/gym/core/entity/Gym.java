package com.mi80.pokeweb.module.gym.core.entity;

import com.mi80.pokeweb.module.gym.core.enums.GymLeader;
import com.mi80.pokeweb.module.pokemon.core.enums.PokemonType;
import com.mi80.pokeweb.module.game.core.enums.Trainer;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gym entity
 *
 * <p>Represents the entity responsible for storing data of a Pokémon gym</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "gyms")
@Tag(
        name = "Gym entity",
        description = """
                Represents the entity responsible for
                storing data of a pokémon gym
                """
)
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "gym_order", nullable = false, unique = true)
    private int gymOrder;

    @Column(nullable = false)
    private String name;

    @Column(name = "gym_leader", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GymLeader leader;

    @Enumerated(value = EnumType.STRING)
    private Trainer challenger;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private PokemonType type = leader.getSpeciality();

    @Builder.Default
    @Column(name = "gym_pokemon_team")
    @OneToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "slot_position")
    @JoinColumn(
            name = "gym_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_gym_pokemon"
            )
    )
    private List<Pokemon> pokemon =
            new ArrayList<>();
}
