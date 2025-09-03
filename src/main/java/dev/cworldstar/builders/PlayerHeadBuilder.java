package dev.cworldstar.builders;

import org.bukkit.Material;
import dev.cworldstar.SkullCreator;

public class PlayerHeadBuilder extends ItemStackBuilder {

	public PlayerHeadBuilder() {
		super(Material.PLAYER_HEAD);
	}
	
	public PlayerHeadBuilder texture(String texture) {
		SkullCreator.loadBase64(item, texture);
		return this;
	}
	
	public PlayerHeadBuilder player(String uuid) {
		SkullCreator.loadPlayerHead(item, uuid);
		return this;
	}

}
