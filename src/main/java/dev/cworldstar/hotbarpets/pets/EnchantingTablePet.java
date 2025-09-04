package dev.cworldstar.hotbarpets.pets;

import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.view.EnchantmentView;
import dev.cworldstar.builders.PlayerHeadBuilder;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.hotbarpets.SimpleBasePet;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class EnchantingTablePet extends SimpleBasePet implements Listener {

	public static final SlimefunItemStack ENCHANTING_TABLE_PET_HEAD = new SlimefunItemStack("ENCHANTING_TABLE_PET", new PlayerHeadBuilder().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg5NTEzOTlkMGViZDBkZmU4N2U1MGYwZDZkZWUyNTI3NGQ5M2YxZmJiMzg1MDVlYzk3MWI2MDFkMWMyY2I5In19fQ==").name("<gradient:dark_purple:light_purple>Enchantment Table Pet").lore(new String[] {
			"<gray>Why can't I be in the plugin?",
			"<gray>I'm awesome!",
			"",
			"<gray>Favorite Food: <white>Book",
			"",
			"<gray>- <red>Right-clicking<gray> opens an",
			" <aqua>enchantment table<gray>."
	}).item());
	
	public EnchantingTablePet() {
		super(HotbarPets.addon().getItemGroup(), ENCHANTING_TABLE_PET_HEAD, new ItemStack(Material.BOOK), new ItemStack[] {
				new ItemStack(Material.OBSIDIAN), new ItemStack(Material.BOOK), new ItemStack(Material.OBSIDIAN),
				new ItemStack(Material.OBSIDIAN), SlimefunItems.SYNTHETIC_DIAMOND.asOne(), new ItemStack(Material.OBSIDIAN),
				new ItemStack(Material.OBSIDIAN), SlimefunItems.GOLD_24K_BLOCK.asOne(), new ItemStack(Material.OBSIDIAN)
		});
	}

	@Override
	public void onUseItem(Player p) {
		EnchantmentView view = MenuType.ENCHANTMENT.builder().build(p);
		view.setEnchantmentSeed(69420);
		view.open();
	}
	
	@EventHandler
	public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
		EnchantmentView view = event.getView();
		if(view.getEnchantmentSeed() == 69420) {
			view.setEnchantmentSeed(RandomUtils.nextInt(0, Integer.MAX_VALUE));
			event.getOffers()[0].setEnchantmentLevel(RandomUtils.nextInt(2, 4));
			event.getOffers()[1].setEnchantmentLevel(RandomUtils.nextInt(15, 18));
			event.getOffers()[2].setEnchantmentLevel(30);
		}
	}

}
