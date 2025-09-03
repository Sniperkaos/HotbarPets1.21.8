package dev.cworldstar.hotbarpets.impl;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * Denotes that this pet uses logic that would require player specific boolean
 * values.
 * @author cworldstar
 *
 */
public interface StaticActivationHolder {
	public static HashMap<UUID, Boolean> activated = new HashMap<UUID, Boolean>();
	
	public default void init(Player who) {
		activated.putIfAbsent(who.getUniqueId(), false);
	}
	
	public default void init(Player who, boolean bool) {
		activated.putIfAbsent(who.getUniqueId(), bool);
	}
	
	public default boolean activated(Player who) {
		if(!activated.containsKey(who.getUniqueId())) {
			init(who);
			return false;
		}
		return activated.get(who.getUniqueId());
	}
	
	public default boolean active(Player who) {
		return activated(who);
	}
	
	public default void activate(Player who) {
		if(!activated.containsKey(who.getUniqueId())) {
			init(who, true);
			return;
		}
		activated.put(who.getUniqueId(), true);
	}
	
	public default void deactivate(Player who) {
		if(!activated.containsKey(who.getUniqueId())) {
			init(who);
			return;
		}
		activated.put(who.getUniqueId(), false);
	}
	
}
