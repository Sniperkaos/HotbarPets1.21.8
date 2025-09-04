package dev.cworldstar.hotbarpets.pets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import dev.cworldstar.builders.PlayerHeadBuilder;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.hotbarpets.SimpleBasePet;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class HappyGhastPet extends SimpleBasePet  {

	public static final SlimefunItemStack HAPPY_GHAST_PET_ITEM = new SlimefunItemStack("HOTBAR_PET_HAPPY_GHAST", new PlayerHeadBuilder()
			.texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I5YWMzMDgyNmFmMmI2MzVjZmFkYjA3ZWVlODA5ZmIzMjVhYjA4ZmExNTllODY4Yjk1YzQwN2YwYzcxOGY1MiJ9fX0=")
			.name("<gradient:dark_red:white>Happy Ghast Pet")
			.lore(new String[] {
					"<gray>Why can't I be in the plugin?",
					"<gray>I'm awesome!",
					"",
					"<gray>Favorite Food: <white>Wind Charge",
					"",
					"<red>Right-Click</red><gray> with this pet to",
					"<gray>fly up into the air!",
					"(provides <white>Slow Falling I<gray>)"
			})
	.item());
	
	public HappyGhastPet() {
		super(HotbarPets.addon().getItemGroup(), HAPPY_GHAST_PET_ITEM, new ItemStack(Material.WIND_CHARGE), new ItemStack[] {
				SlimefunItems.AIR_RUNE.asOne(), new ItemStack(Material.DRIED_GHAST), SlimefunItems.AIR_RUNE.asOne(),
				new ItemStack(Material.WIND_CHARGE), SlimefunItems.NECROTIC_SKULL.asOne(), SlimefunItem.getById("HOTBAR_PET_GHAST").getItem(),
				SlimefunItems.AIR_RUNE.asOne(), SlimefunItems.EXPLOSIVE_BOW.asOne(), SlimefunItems.AIR_RUNE.asOne(),
		});
	}

	@Override
	public void onUseItem(Player p) {
		p.setVelocity(new Vector(0, 35, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 600, 0));
	}

}
