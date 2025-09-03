package io.github.thebusybiscuit.hotbarpets;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.FormatUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.math.RandomUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound.Source;

public class HotbarPet extends SlimefunItem {

    private static final long MESSAGE_DELAY = 2000;
    private static final Map<UUID, Long> messageDelay = new HashMap<>();

    private final ItemStack food;

    public HotbarPet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, ItemStack[] recipe) {
        super(itemGroup, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.food = food;
    }
    
    public HotbarPet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.food = food;
    }

    public ItemStack getFavouriteFood() {
        return this.food;
    }
    
    protected boolean canSendMessage(Player player) {
    	long msgDelay = messageDelay.getOrDefault(player.getUniqueId(), 0L);
    	if(msgDelay <= System.currentTimeMillis()) {
    		return true;
    	}
    	return false;
    }
    
    protected void sendMessage(Player player, String message) {
        player.sendMessage(FormatUtils.convert(message));
        messageDelay.put(player.getUniqueId(), System.currentTimeMillis() + MESSAGE_DELAY);
    }

    /**
     * Tries to consume the pets favourite food, if this was successful it will return true. Otherwise false.
     * If it fails to consume food it will send a message to the player, this is on a delay as to not spam the player.
     * The default delay is {@link #MESSAGE_DELAY}.
     *
     * @param player
     *            The {@link Player} who owns this pet
     * 
     * @return If the food consumption was successful
     */
    public boolean checkAndConsumeFood(Player player, boolean sendMessage) {
        if (!player.getInventory().containsAtLeast(getFavouriteFood(), 1)) {
            if (canSendMessage(player) && sendMessage) {
            	sendMessage(player, "<blue>Your " + getItemName() + " would have helped you, but it was not fed.");
            }
            return false;
        }

    	player.playSound(net.kyori.adventure.sound.Sound.sound(Key.key("minecraft:entity.generic.eat"), Source.AMBIENT, 0.5f, RandomUtils.nextFloat()));
        player.getInventory().removeItem(getFavouriteFood());
        return true;
    }
    
    public boolean checkAndConsumeFood(Player player) {
    	return checkAndConsumeFood(player, true);
    }

    public static Map<UUID, Long> getMessageDelay() {
        return messageDelay;
    }
}