package com.mi80.pokeweb.entity;

import com.mi80.pokeweb.enums.ItemType;
import com.mi80.pokeweb.enums.Trainer;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Game entity
 *
 * <p>Represents the entity responsible for the current STATE of the game, not the rules</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Game entity",
        description = """
                Represents the entity responsible
                for the current STATE of the game, not the rules
                """
)
public class Game {
    private UUID id;
    private Trainer trainer;
    private Gym currentGym;
    private int coins;
    private List<Pokemon> team;
    private Map<ItemType, Integer> inventory;

    public Game() {}

    public Game(Trainer trainer, Gym currentGym, int coins) {
        this.trainer = trainer;
        this.currentGym = currentGym;
        this.coins = coins;
        this.team = new CopyOnWriteArrayList<>();
        this.inventory = new EnumMap<>(ItemType.class);

        for (ItemType item : ItemType.values()) {
            inventory.put(item, 0);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Gym getCurrentGym() {
        return currentGym;
    }

    public void setCurrentGym(Gym currentGym) {
        this.currentGym = currentGym;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = Math.max(0, coins);
    }

    public List<Pokemon> getTeam() {
        return team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }

    public Map<ItemType, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<ItemType, Integer> inventory) {
        this.inventory = inventory;
    }
}
