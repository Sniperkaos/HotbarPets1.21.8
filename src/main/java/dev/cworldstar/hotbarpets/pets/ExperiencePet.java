package dev.cworldstar.hotbarpets.pets;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.builders.PlayerHeadBuilder;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

public class ExperiencePet extends HotbarPet implements Listener, TimedFeeder {

	private static HashMap<UUID, Boolean> active = new HashMap<UUID, Boolean>();
	
	private static final ItemStack EXPERIENCE_PET_HEAD = new PlayerHeadBuilder().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkODg1YjMyYjBkZDJkNmI3ZjFiNTgyYTM0MTg2ZjhhNTM3M2M0NjU4OWEyNzM0MjMxMzJiNDQ4YjgwMzQ2MiJ9fX0=").name("<gradient:green:white>Experience Pet").lore(new String[] {
			"<gray>Why can't I be in the plugin?",
			"<gray>I'm awesome!",
			"",
			"<gray>Favorite Food: <gradient:white:yellow>Experience Bottle",
			"",
			"<gray>- <white>While active in your hotbar, increases your",
			"<white> <green>experience</green> gain by 50%."
	}).item();
	
	public ExperiencePet() {
		super(
				HotbarPets.addon().getItemGroup(), 
				new SlimefunItemStack("EXPERIENCE_PET", EXPERIENCE_PET_HEAD), 
				new ItemStack(Material.EXPERIENCE_BOTTLE), 
				new ItemStack[] {
						
				}
		);
	}

	@Override
	public void run(Player player) {
		tick(this, player);
	}
	
	@EventHandler
	public void onExperienceGainEvent(PlayerExpChangeEvent event) {
		int amount = event.getAmount();
		Boolean isActive = active.get(event.getPlayer().getUniqueId());
		if(isActive == null) {
			isActive = false;
		}
		if(Math.signum(amount) >= 1.0) {
			if(isActive == true) {
				event.setAmount((int) Math.round(event.getAmount() + ((double) event.getAmount() * 0.25))); // increase the experience by 50%
			}
		}
	}
}
