package dev.cworldstar.hotbarpets.impl;

import org.bukkit.entity.Player;

/**
 * Provides an abstract function to run when
 * a {@link HotbarPet} is no longer found in a player's hotbar.
 * @author cworldstar
 *
 */
public interface PetRemovalMarker {
	public abstract void onPetRemoval(Player player);
}
