package dev.cworldstar.hotbarpets.impl;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

public class TimedFeedingPet extends HotbarPet implements TimedFeeder {

	private int feedDelay;
	private Consumer<Player> run;
	
	public TimedFeedingPet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, ItemStack[] recipe, int seconds, Consumer<Player> toRun) {
		super(itemGroup, item, food, recipe);
		feedDelay = seconds;
		run = toRun;
	}
	
	public TimedFeedingPet(ItemGroup itemGroup, SlimefunItemStack item, ItemStack food, ItemStack[] recipe, Consumer<Player> toRun) {
		this(itemGroup, item, food, recipe, 30, toRun);
	}

	@Override
	public int getFeedDelay() {
		return feedDelay;
	}
	
	@Override
	public void run(Player player) {
		if(isFeedTime(this, player) && tryFeed(this, player)) {
			run.accept(player);
		}
	}

}
