package com.mi80.pokeweb.module.game.core.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Item type enum
 *
 * <p>Represents the item can be used to heal a Pokémon</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Item type enum",
        description = """
                Represents the item can be used to heal a Pokémon
                """
)
public enum ItemType {
    POTION(100, 20),
    SUPER_POTION(300, 50),
    HYPER_POTION(500, 100);

    private final int price;
    private final int healingAmount;

    ItemType(int price, int heallingAmount){
        this.price = price;
        this.healingAmount = heallingAmount;
    }

    public int getPrice() {
        return price;
    }

    public int getHealingAmount() {
        return healingAmount;
    }
}
