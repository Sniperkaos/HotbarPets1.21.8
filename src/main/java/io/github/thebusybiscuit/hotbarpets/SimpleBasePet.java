package io.github.thebusybiscuit.hotbarpets;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

public abstract class SimpleBasePet extends HotbarPet {

    public SimpleBasePet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, ItemStack[] recipe) {
        super(itemGroup, item, food, recipe);
    }
    
    public SimpleBasePet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, food, recipeType, recipe);
    }

    public abstract void onUseItem(Player p);

    @Override
    public void preRegister() {
        super.preRegister();
        addItemHandler(onClick());
    }

    private ItemUseHandler onClick() {
        return e -> {
            if (checkAndConsumeFood(e.getPlayer())) {
                onUseItem(e.getPlayer());
            }
        };
    }

}
