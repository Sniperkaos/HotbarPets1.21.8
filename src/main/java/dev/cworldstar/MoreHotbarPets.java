package dev.cworldstar;

import dev.cworldstar.hotbarpets.pets.CWPet;
import dev.cworldstar.hotbarpets.pets.EnchantingTablePet;
import dev.cworldstar.hotbarpets.pets.ExperiencePet;
import dev.cworldstar.hotbarpets.pets.HappyGhastPet;
import dev.cworldstar.hotbarpets.pets.KnightPet;
import dev.cworldstar.hotbarpets.pets.PistonPet;
import dev.cworldstar.hotbarpets.pets.XPet;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

public class MoreHotbarPets {
	public static void registerAll(SlimefunAddon addon) {
		if(PluginIntegrations.isANOSFActive()) {
			new CWPet().register(addon);
		}

		if(PluginIntegrations.isBreweryXActive() && PluginIntegrations.isANOSFActive()) {
			new XPet().register(addon);
		}
		
		new ExperiencePet().register(addon);
		new PistonPet().register(addon);
		new EnchantingTablePet().register(addon);
		new KnightPet().register(addon);
		new HappyGhastPet().register(addon);
	}
}
