package dev.cworldstar.hotbarpets.pets;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.builders.PlayerHeadBuilder;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class KnightPet extends HotbarPet implements TimedFeeder, Listener {

	public static final SlimefunItemStack KNIGHT_PET_ITEM = new SlimefunItemStack("HOTBAR_PET_KNIGHT", new PlayerHeadBuilder()
			.texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmEwMmE1MDY1Yjk0MmIxMzY1NjY3OGI5MmZmN2VkMjQ2ZGIwNjNmOTc5N2QyNDU4NDdjZGMzOTVmOTAyMGMzYiJ9fX0=")
			.name("<gradient:gray:dark_gray>Knight Pet")
			.lore(new String[] {
					"<gray>Why can't I be in the plugin?",
					"<gray>I'm awesome!",
					"",
					"<gray>Favorite Food: <blue>Reinforced Alloy",
					"",
					"<gray>If this pet is active:",
					"<gray>Deal an extra <red>75%<gray> damage while",
					"<gray>holding a sword."
			})
	.item());
	
	public KnightPet() {
		super(HotbarPets.addon().getItemGroup(), KNIGHT_PET_ITEM, SlimefunItems.REINFORCED_ALLOY_INGOT.asOne(), new ItemStack[] {
				new ItemStack(Material.IRON_SWORD), new ItemStack(Material.CHAINMAIL_HELMET), SlimefunItems.NECROTIC_SKULL.asOne(),
				SlimefunItems.SOULBOUND_RUNE.asOne(), SlimefunItems.ANDROID_MEMORY_CORE.asOne(), SlimefunItems.PROGRAMMABLE_ANDROID_BUTCHER.asOne(),
				new ItemStack(Material.CHAINMAIL_LEGGINGS),  SlimefunItems.CARBONADO_JETBOOTS.asOne(), SlimefunItems.MAGICAL_ZOMBIE_PILLS.asOne()
		});
	}

	@Override
	public void run(Player player) {
		tick(this, player);
	}
	
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if(damager instanceof Player) {
			Player player = (Player) damager;
			if(active(this, player)) {
				event.setDamage(event.getDamage() + (event.getDamage() * 0.75)); 
			}
		}
	}

}
