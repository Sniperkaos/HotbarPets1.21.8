package dev.cworldstar.hotbarpets.pets;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.cworldstar.anosf.items.Items;
import dev.cworldstar.anosf.items.Items.ItemTier;
import dev.cworldstar.anosf.items.recipes.AcceleratorRecipe;
import dev.cworldstar.builders.PlayerHeadBuilder;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import dev.cworldstar.libs.cwlib.ItemRegistry;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.hotbarpets.SimpleBasePet;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class CWPet extends HotbarPet implements TimedFeeder {

	public static final SlimefunItemStack SNIPERKAOS_HEAD = new SlimefunItemStack("SNIPERKAOS_PET", new PlayerHeadBuilder().player("f0979360-8222-4baa-8249-42c23ecb32d9").name("<gradient:yellow:#787607>Sniperkaos Pet").lore(new String[] {
			"<gray>Why can't I be in the plugin?",
			"<gray>I'm awesome!",
			"",
			"<gray>Favorite Food: <gradient:dark_green:green>Boosted Uranium",
			"",
			"<gray>- <aqua>Squeaks when poked.",
			"<gray>- Will additionally grant <gradient:green:yellow>Luck II <gray>randomly.",
			"",
			ItemTier.STRANGE.makeItemString("Hotbar Pet"),
	}).item());
	
	public CWPet() {
		super(HotbarPets.addon().getItemGroup(), 
			SNIPERKAOS_HEAD, 
			SlimefunItems.BOOSTED_URANIUM.asOne(), 
			Items.ACCELERATOR_RECIPE_TYPE, 
			null
		);
		
		addItemHandler(new ItemUseHandler() {
			@Override
			public void onRightClick(PlayerRightClickEvent e) {
				Player p = e.getPlayer();
				Location playerLoc = p.getLocation();
				World world = playerLoc.getWorld();
				// play a funny little sound
				world.playSound(playerLoc, Sound.ENTITY_GLOW_SQUID_HURT, 1, RandomUtils.nextFloat(0, 2) - 1);				
			}
		});
		
		// register accelerator recipe
		new AcceleratorRecipe(
				ItemRegistry.getRegistryItem("FALLEN_SPARK_PARTICLE").getItem(),
				SNIPERKAOS_HEAD.asOne(),
				ItemRegistry.getRegistryItem("DEVIATED_ITEM").getItem(),
				69420,
				"SNIPERKAOS_PET_SYNTHESIS"
		);
	}
	
	@Override
	public int getFeedDelay() {
		return RandomUtils.nextInt(60, 180);
	}

	@Override
	public void run(Player p) {
		tick(this, p);
		if(ThreadLocalRandom.current().nextInt(1, 5) >= 4) {
			if(active(this, p)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 600, 1));
			}
		}
	}

}
