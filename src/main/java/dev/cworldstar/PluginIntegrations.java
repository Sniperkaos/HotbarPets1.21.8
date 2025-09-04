package dev.cworldstar;

import org.bukkit.Bukkit;

public class PluginIntegrations {

	public static boolean isANOSFActive() {
		return Bukkit.getServer().getPluginManager().isPluginEnabled("ANOSF");
	}

	public static boolean isBreweryXActive() {
		return Bukkit.getServer().getPluginManager().isPluginEnabled("BreweryX");
	}

	public static boolean isProtocolLibActive() {
		return Bukkit.getServer().getPluginManager().isPluginEnabled("ProtocolLib");
	}

}
