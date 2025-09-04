package dev.cworldstar.hotbarpets.pets;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.dre.brewery.api.BreweryApi;

import dev.cworldstar.FormatUtils;
import dev.cworldstar.anosf.items.Items;
import dev.cworldstar.anosf.items.Items.ItemTier;
import dev.cworldstar.builders.PlayerHeadBuilder;
import dev.cworldstar.hotbarpets.impl.PetActivatedMarker;
import dev.cworldstar.hotbarpets.impl.StaticActivationHolder;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import dev.cworldstar.libs.cwlib.ItemRegistry;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class XPet extends HotbarPet implements TimedFeeder, PetActivatedMarker, Listener, StaticActivationHolder {

	private static final ItemStack XMPRIX_HEAD = new PlayerHeadBuilder().player("a8449c57-df24-4d90-9faf-b84574d9ecc4").name("<gradient:gray:red>Xmprix Pet").lore(new String[] {
			"<gray>Why can't I be in the plugin?",
			"<gray>I'm awesome!",
			"",
			"<gray>Favorite Food: <gradient:#F28E1C:#FE8C12:#F78F1E>Skunky Beer (1 Star)",
			"",
			"<gray>- <yellow>Burps when poked.",
			"<gray>- Will additionally act as a totem of undying,",
			"<gray> but only while fed.",
			"",
			ItemTier.COSMIC.makeItemString("Hotbar Pet")
	}).item();
	
	private static final ItemStack SKUNKY_BEER = BreweryApi.createBrewItem("beer", 1);
	
	public XPet() {
		super(HotbarPets.addon().getItemGroup(), new SlimefunItemStack("XMPRIX_PET", XMPRIX_HEAD), BreweryApi.createBrewItem("beer", 1), Items.MOLECULAR_CRAFTER_RECIPE_TYPE, new ItemStack[] {
				SKUNKY_BEER, SKUNKY_BEER, SKUNKY_BEER, new ItemStack(Material.TOTEM_OF_UNDYING), SKUNKY_BEER, SKUNKY_BEER, SKUNKY_BEER,
				ItemRegistry.getRegistryItem("COSMIC_ALLOY_ROD").getItem(), SlimefunItems.NECROTIC_SKULL.asOne(), CWPet.SNIPERKAOS_HEAD.asOne(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), ItemRegistry.getRegistryItem("COSMIC_ALLOY_ROD").getItem(),
				ItemRegistry.getRegistryItem("COSMIC_ALLOY_ROD").getItem(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(), EnchantingTablePet.ENCHANTING_TABLE_PET_HEAD.asOne(), SlimefunItems.NECROTIC_SKULL.asOne(), ItemRegistry.getRegistryItem("COSMIC_ALLOY_ROD").getItem(),
				SKUNKY_BEER, SKUNKY_BEER, SKUNKY_BEER, new ItemStack(Material.TOTEM_OF_UNDYING), SKUNKY_BEER, SKUNKY_BEER, SKUNKY_BEER,
		});
		Bukkit.getPluginManager().registerEvents(this, HotbarPets.addon());
		addItemHandler(new ItemUseHandler() {
			@Override
			public void onRightClick(PlayerRightClickEvent e) {
				Player p = e.getPlayer();
				if(activated(XPet.this, e.getPlayer())) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
					BreweryApi.setPlayerDrunk(p, 5, 10);
				} else {
					p.sendMessage(FormatUtils.mm("<gray>Your xmprix pet is hungry. Remaining delay: <aqua>" + String.valueOf(delay(XPet.this, p))));
				}
			};
		});
	}

	@Override
	public int getFeedDelay() {
		return 180;
	}
	
	@Override
	public void run(Player player) {
		tick(this, player);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDamageEvent e) {
		Entity damaged = e.getEntity();
		if(!(damaged instanceof Player)) return;
		if(e.isCancelled()) return;
		Player hurt = (Player) damaged;
		if(!active(this, hurt)) {
			return;
		}
		if((hurt.getHealth() - e.getDamage()) <= 0) {
			// trigger the totem
			hurt.playEffect(EntityEffect.PROTECTED_FROM_DEATH);
			hurt.spawnParticle(Particle.TOTEM_OF_UNDYING, hurt.getLocation(), 4);
			hurt.sendMessage(FormatUtils.mm("<rainbow>You would have died, but XMPRIX saved you!"));
			
			e.setCancelled(true);
			
			hurt.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 4000, 3));
			hurt.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4000, 4));
			hurt.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 4000, 4));
			hurt.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4000, 2));
			
			deactivate(this, hurt);
		}
	}

	@Override
	public void onPetActivated(Player player) {
		run(player);
	}

}
