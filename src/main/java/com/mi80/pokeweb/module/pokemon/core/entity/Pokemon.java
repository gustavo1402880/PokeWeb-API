package com.mi80.pokeweb.module.pokemon.core.entity;

import com.mi80.pokeweb.module.pokemon.core.enums.BattlePosition;
import com.mi80.pokeweb.module.pokemon.core.enums.EvolutionStage;
import com.mi80.pokeweb.module.pokemon.core.enums.PokemonType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Pokémon entity
 *
 * <p>Represents the entity responsible for storing the data of a single Pokémon</p>
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
@Table(name = "pokemons")
@Tag(
        name = "Pokémon entity",
        description = """
                Represents the entity responsible
                for storing the data of a single Pokémon
                """
)
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number_dex")
    private int numberDex;

    @Column(nullable = false)
    private String name;

    @Column
    private String nickname;

    @Column(name = "primary_type")
    @Enumerated(EnumType.STRING)
    private PokemonType primaryType;

    @Column(name = "secondary_type")
    @Enumerated(EnumType.STRING)
    private PokemonType secondaryType;

    @Column(name = "max_health", nullable = false)
    private int maxHealth;

    @Builder.Default
    @Column(name = "current_health", nullable = false)
    private int currentHealth = maxHealth;

    @Column(nullable = false)
    private int attack;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false)
    private String classification;

    @Column(nullable = false)
    private int level;

    @Column(name = "evolution_stage", nullable = false)
    @Enumerated(EnumType.STRING)
    private EvolutionStage evolutionStage;

    @Column(name = "battle_position", nullable = false)
    @Enumerated(EnumType.STRING)
    private BattlePosition battlePosition;

    /**
     * Checks if the Pokémon has fainted.
     *
     * @return Boolean value if the Pokémon has fainted
     */
    public boolean isFainted() {
        return this.currentHealth == 0;
    }

    /**
     * Process the damage taken by the Pokémon.
     *
     * <p>Calculates the damage taken by the Pokémon,
     * if currentHealth drops below zero, currentHealth will
     * be reset automatically.</p>
     *
     * @param damage Amount of damage the Pokémon received
     */
    public void takeDamage(int damage) {
        this.currentHealth = Math.max(0, this.currentHealth - Math.max(0, damage));
    }

    /**
     * Process the healed taken by the Pokémon
     *
     * <p>Calculates the health taken by the Pokémon,
     * if currentHealth exceeds the maxHealth, currentHealth
     * will automatically match maximum health.</p>
     *
     * @param amount Amount of heal the Pokémon received
     */
    public void heal(int amount) {
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + Math.max(0, amount));
    }

    /**
     * Process full heal teken by the Pokémon
     *
     * <p>Sets the Pokémon's currenthealth to
     * it's maxHealth</p>
     */
    public void fullHeal() {
        this.currentHealth = this.maxHealth;
    }

    /**
     * Checks the Pokémon can attack
     *
     * @return Boolean if the Pokémon is battling
     */
    public boolean canAttack() {
        return this.battlePosition == BattlePosition.FRONT;
    }

    /**
     * Change the Pokémon position
     *
     * <p>Change the battlePosition of a Pokémon
     * based on his current position</p>
     */
    public void movePosition() {
        this.battlePosition = this.battlePosition == BattlePosition.FRONT
                ? BattlePosition.BACK
                : BattlePosition.FRONT;
    }

    /**
     * Increases the Pokémon's level
     *
     * <p>Increases the Pokémon's level improving its
     * battle attributes</p>
     */
    public void levelUp() {
        this.level += 1;
        this.attack += 2;
        this.defense += 2;
        this.speed += 2;
        this.maxHealth += 5;
    }
}
