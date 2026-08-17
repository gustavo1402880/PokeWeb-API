package com.mi80.pokeweb.entity;

import com.mi80.pokeweb.enums.BattlePosition;
import com.mi80.pokeweb.enums.EvolutionStage;
import com.mi80.pokeweb.enums.PokemonType;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

/**
 * Pokémon entity
 *
 * <p>Represents the entity responsible for storing the data of a single Pokémon</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Pokémon entity",
        description = """
                Represents the entity responsible
                for storing the data of a single Pokémon
                """
)
public class Pokemon {
    private UUID id;
    private int numberDex;
    private String name;
    private String nickname;
    private PokemonType primaryType;
    private PokemonType secondaryType;
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int defense;
    private int speed;
    private String classification;
    private int level;
    private EvolutionStage evolutionStage;
    private BattlePosition battlePosition;

    public Pokemon() {}

    public Pokemon(int numberDex,
                   String name,
                   String nickname,
                   PokemonType primaryType,
                   PokemonType secondaryType,
                   int maxHealth, int currentHealth,
                   int attack,
                   int defense,
                   int speed,
                   String classification,
                   int level,
                   EvolutionStage evolutionStage,
                   BattlePosition battlePosition
    ) {
        this.numberDex = numberDex;
        this.name = name;
        this.nickname = nickname;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.maxHealth = maxHealth;
        this.currentHealth = Math.clamp(currentHealth, 0, maxHealth);
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.classification = classification;
        this.level = level;
        this.evolutionStage = evolutionStage;
        this.battlePosition = battlePosition;
    }

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
        this.currentHealth = Math.max(0, currentHealth - Math.max(0, damage));
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
        this.currentHealth = Math.min(maxHealth, currentHealth + Math.max(0, amount));
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

    public UUID getId() {
        return id;
    }

    public int getNumberDex() {
        return numberDex;
    }

    public void setNumberDex(int numberDex) {
        this.numberDex = numberDex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(PokemonType primaryType) {
        this.primaryType = primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(PokemonType secondaryType) {
        this.secondaryType = secondaryType;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.clamp(currentHealth, 0, maxHealth);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public EvolutionStage getEvolutionStage() {
        return evolutionStage;
    }

    public void setEvolutionStage(EvolutionStage evolutionStage) {
        this.evolutionStage = evolutionStage;
    }

    public BattlePosition getBattlePosition() {
        return battlePosition;
    }

    public void setBattlePosition(BattlePosition battlePosition) {
        this.battlePosition = battlePosition;
    }
}
