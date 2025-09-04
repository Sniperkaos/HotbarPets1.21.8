package dev.cworldstar.hotbarpets.impl;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.github.thebusybiscuit.hotbarpets.HotbarPet;

/**
 * Denotes that this pet uses logic that would require player specific boolean
 * values.
 * @author cworldstar
 *
 */
public interface StaticActivationHolder {
	public static HashMap<HotbarPet, HashMap<UUID, Boolean>> activated = new HashMap<HotbarPet, HashMap<UUID, Boolean>>();
	
	default HashMap<UUID, Boolean> getPet(final HotbarPet pet) {
		if(!activated.containsKey(pet)) {
			activated.put(pet, new HashMap<UUID, Boolean>());
		}
		return activated.get(pet);
	}
	
	default void init(final HotbarPet pet, Player who) {
		
		getPet(pet).putIfAbsent(who.getUniqueId(), false);
	}
	
	 default void init(final HotbarPet pet, Player who, boolean bool) {
		 getPet(pet).putIfAbsent(who.getUniqueId(), bool);
	}
	
	default boolean activated(final HotbarPet pet, Player who) {
		if(!getPet(pet).containsKey(who.getUniqueId())) {
			init(pet, who);
			return false;
		}
		return getPet(pet).get(who.getUniqueId());
	}
	
	default boolean active(final HotbarPet pet, Player who) {
		return activated(pet, who);
	}
	
	default void activate(final HotbarPet pet, Player who) {
		if(!getPet(pet).containsKey(who.getUniqueId())) {
			init(pet, who, true);
			return;
		}
		getPet(pet).put(who.getUniqueId(), true);
	}
	
	default void deactivate(final HotbarPet pet, Player who) {
		if(!getPet(pet).containsKey(who.getUniqueId())) {
			init(pet, who);
			return;
		}
		getPet(pet).put(who.getUniqueId(), false);
	}
	
}
