package dev.cworldstar.hotbarpets.impl;

import org.bukkit.entity.Player;

/**
 * Denotes that the superclass pet is a ticking one.
 * @author cworldstar
 */
public interface SequencedPet {
	public abstract void run(Player player);
}
