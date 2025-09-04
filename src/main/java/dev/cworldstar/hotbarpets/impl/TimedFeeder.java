package dev.cworldstar.hotbarpets.impl;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.ItemEditorProtocol;
import dev.cworldstar.PluginIntegrations;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

/**
 * Implementation of a ticking pet which does not feed every tick.
 * Requires the superclass to inherit the abstract methods of {@link SequencedPet}.
 * @author cworldstar
 *
 */
public interface TimedFeeder extends SequencedPet, StaticActivationHolder {
	public static HashMap<HotbarPet, HashMap<UUID, FeedingInformation>> feedInformation = new HashMap<HotbarPet, HashMap<UUID, FeedingInformation>>();
	
	public default int getFeedDelay() {
		return 30;
	}
	
	public default HashMap<UUID, FeedingInformation> feedInfo(HotbarPet pet) {
		if(!feedInformation.containsKey(pet)) {
			feedInformation.put(pet, new HashMap<UUID, FeedingInformation>());
		}
		return feedInformation.get(pet);
	}
	
	public default boolean isFeedTime(final HotbarPet pet, Player p) {
		if(!feedInfo(pet).containsKey(p.getUniqueId())) {
			return true;
		}
		return feedInfo(pet).get(p.getUniqueId()).isFeedTime();
	}
	
	public default int delay(final HotbarPet pet, Player p) {
		if(!feedInfo(pet).containsKey(p.getUniqueId())) {
			return 0;
		}
		int ticks = feedInfo(pet).get(p.getUniqueId()).getRemainingTicks();
		if(ticks < 0) ticks = 0;
		return ticks;
	}
	
	public default void tick(HotbarPet pet, Player player) {
		if(isFeedTime(pet, player)) {
			if(feed(pet, player)) {
				activate(pet, player);
			} else {
				deactivate(pet, player);
			}
		}
	}
	
	public default boolean tryFeed(HotbarPet pet, Player p) {
		if(!feedInfo(pet).containsKey(p.getUniqueId())) {
			if(pet.checkAndConsumeFood(p, false)) {
				FeedingInformation info = new FeedingInformation(getFeedDelay());
				feedInfo(pet).put(p.getUniqueId(), info);
			} else {
				return false;
			}
		} else {
			FeedingInformation info = feedInfo(pet).get(p.getUniqueId());
			if(info.isFeedTime()) {
				if(pet.checkAndConsumeFood(p, false)) {
					info.reset(getFeedDelay());
					return true;
				}
				return false;
			}
		}
		return true;
	}
	
	public default boolean feed(HotbarPet pet, Player p) {
		return tryFeed(pet, p);
	}
	
}
