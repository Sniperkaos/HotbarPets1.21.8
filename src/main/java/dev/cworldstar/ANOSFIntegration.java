package dev.cworldstar;

import dev.cworldstar.anosf.ANOSF;
import dev.cworldstar.libs.cwlib.builders.ItemGroupBuilder;
import dev.cworldstar.libs.cwlib.groups.InvisibleGroup;
import dev.cworldstar.libs.cwlib.groups.MultiGroup;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.hotbarpets.PetTexture;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

public class ANOSFIntegration {
	
	
	public static void setup(HotbarPets plugin) {
		
		MultiGroup DEFAULT_ITEM_GROUP = new MultiGroup(ANOSF.key("DEFAULT_ITEM_GROUP"), PetTexture.CATEGORY.getAsItem(), "<light_purple>Hotbar Pets");
		DEFAULT_ITEM_GROUP.register(HotbarPets.addon());
		
		InvisibleGroup newGroup = new ItemGroupBuilder().setID("pets").setItem(CustomItemStack.create(
				PetTexture.CATEGORY.getAsItem(), 
				"&dHotbar Pets", 
				"", 
				"&a> Click to open"
			)
		).build();
		
		DEFAULT_ITEM_GROUP.add(newGroup.proxy());
		plugin.setItemGroup(newGroup);
	}
}
