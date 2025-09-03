package dev.cworldstar;

import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

public class SkullCreator {
	public static ItemStack skullFromBase64(String base64) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		UUID profileID = UUID.randomUUID();
		PlayerProfile profile = Bukkit.getServer().createProfileExact(profileID, profileID.toString().substring(0, 16));
		profile.setProperty(new ProfileProperty("textures", base64));
		skull.editMeta(SkullMeta.class, meta -> {
			meta.setPlayerProfile(profile);
		});
		return skull;
	}

	public static ItemStack loadBase64(ItemStack item, String texture) {
		Validate.isInstanceOf(SkullMeta.class, item.getItemMeta(), "#loadBase64(item, texture) passed an item without SkullMeta.");
		UUID profileID = UUID.randomUUID();
		PlayerProfile profile = Bukkit.getServer().createProfileExact(profileID, profileID.toString().substring(0, 16));
		profile.setProperty(new ProfileProperty("textures", texture));
		item.editMeta(SkullMeta.class, meta -> {
			meta.setPlayerProfile(profile);
		});
		return item;
	}
	
	public static ItemStack loadPlayerHead(ItemStack item, String uuid) {
		Validate.isInstanceOf(SkullMeta.class, item.getItemMeta(), "#loadPlayerHead(item, texture) passed an item without SkullMeta.");
		UUID ownerUUID = UUID.fromString(uuid);
		Bukkit.getLogger().log(Level.INFO, "UUID: " + uuid);
		OfflinePlayer player = Bukkit.getOfflinePlayer(ownerUUID);
		Bukkit.getLogger().log(Level.INFO, "OfflinePlayer: " + player.toString());
		PlayerProfile profile = player.getPlayerProfile();
		profile.complete();
		
		item.editMeta(SkullMeta.class, meta -> {
			meta.setPlayerProfile(profile);
		});
		return item;
	}
}
