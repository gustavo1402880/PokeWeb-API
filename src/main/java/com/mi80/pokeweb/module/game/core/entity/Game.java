package com.mi80.pokeweb.module.game.core.entity;

import com.mi80.pokeweb.module.gym.core.entity.Gym;
import com.mi80.pokeweb.module.game.core.enums.ItemType;
import com.mi80.pokeweb.module.game.core.enums.Trainer;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

/**
 * Game entity
 *
 * <p>Represents the entity responsible for the current STATE of the game, not the rules</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Entity
@Table(name = "games")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(
        name = "Game entity",
        description = """
                Represents the entity responsible
                for the current STATE of the game, not the rules
                """
)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Trainer trainer = Trainer.ASH;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "current_gym_id",
            foreignKey = @ForeignKey(
                    name = "fk_game_gym"
            )
    )
    private Gym currentGym;

    @Builder.Default
    @Column(nullable = false)
    private int coins = 1000;

    @Builder.Default
    @Column(name = "pokemon_team")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @OrderColumn(name = "slot_position")
    @JoinColumn(
            name = "game_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_game_pokemon"
            )
    )
    private List<Pokemon> team =
            new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "game_inventory",
            joinColumns = @JoinColumn(
                    name = "game_id",
                    foreignKey = @ForeignKey(
                            name = "fk_inventory_game"
                    )
            )
    )
    @MapKeyColumn(name = "item_type")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "item_count")
    private Map<ItemType, Integer> inventory =
            new EnumMap<>(ItemType.class);
}
