package dev.cworldstar.hotbarpets.impl;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.github.thebusybiscuit.hotbarpets.HotbarPet;

/**
 * Implementation of a ticking pet which does not feed every tick.
 * Requires the superclass to inherit the abstract methods of {@link SequencedPet}.
 * @author cworldstar
 *
 */
public interface TimedFeeder extends SequencedPet {
	public static HashMap<UUID, FeedingInformation> feedInformation = new HashMap<UUID, FeedingInformation>();
	
	public default int getFeedDelay() {
		return 300;
	}
	
	public default boolean isFeedTime(Player p) {
		if(!feedInformation.containsKey(p.getUniqueId())) {
			FeedingInformation info = new FeedingInformation(getFeedDelay());
			feedInformation.put(p.getUniqueId(), info);
		}
		
		return feedInformation.get(p.getUniqueId()).isFeedTime();
	}
	
	public default boolean tryFeed(HotbarPet pet, Player p) {
		if(!feedInformation.containsKey(p.getUniqueId())) {
			if(pet.checkAndConsumeFood(p, false)) {
				FeedingInformation info = new FeedingInformation(getFeedDelay());
				feedInformation.put(p.getUniqueId(), info);
			} else {
				return false;
			}
		} else {
			FeedingInformation info = feedInformation.get(p.getUniqueId());
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
