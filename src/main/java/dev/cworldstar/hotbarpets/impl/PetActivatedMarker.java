package dev.cworldstar.hotbarpets.impl;

import org.bukkit.entity.Player;

/**
 * Provides a method that fires when a {@link HotbarPet} is moved into the hotbar. (slots 0-8)
 * @author cworldstar
 *
 */
public interface PetActivatedMarker {
	public abstract void onPetActivated(Player player);
}
