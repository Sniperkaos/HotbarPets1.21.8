package dev.cworldstar;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import dev.cworldstar.hotbarpets.impl.StaticActivationHolder;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import net.kyori.adventure.text.Component;

/**
 * 
 * ProtocolLib integration for displaying feed timer and activation values for
 * pets.
 * 
 * @author cworldstar
 *
 */
public class ProtocolLibIntegration {
	public static void setup(JavaPlugin plugin) {
		ItemEditorProtocol.passthrough((item, player) -> {
			SlimefunItem sfItem = SlimefunItem.getByItem(item);
			if(sfItem == null) return item;
			if(!(sfItem instanceof HotbarPet)) return item;
			
			List<Component> lore = item.lore();
			lore.add(FormatUtils.mm(""));
			HotbarPet pet = (HotbarPet) sfItem;
			if(pet instanceof TimedFeeder) {
				int feedTime = ((TimedFeeder) pet).delay(pet, player);
				lore.add(FormatUtils.mm("<gray>Feed Timer: <aqua>" + String.valueOf(feedTime)));
			}
			if(pet instanceof StaticActivationHolder) {
				boolean active = ((StaticActivationHolder) pet).active(pet, player);
				lore.add(FormatUtils.mm("<gray>Active: " + (active == true ? "<green>Yes<gray>." : "<red>No<gray>.") + ""));
			}

			item.lore(lore);
			return item;
		});
		ItemEditorProtocol.start();
	}
}
