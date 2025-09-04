package io.github.thebusybiscuit.hotbarpets;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.cworldstar.ANOSFIntegration;
import dev.cworldstar.MoreHotbarPets;
import dev.cworldstar.PluginIntegrations;
import dev.cworldstar.ProtocolLibIntegration;
import io.github.thebusybiscuit.hotbarpets.groups.BossMobs;
import io.github.thebusybiscuit.hotbarpets.groups.FarmAnimals;
import io.github.thebusybiscuit.hotbarpets.groups.HostileMobs;
import io.github.thebusybiscuit.hotbarpets.groups.PassiveMobs;
import io.github.thebusybiscuit.hotbarpets.groups.PeacefulAnimals;
import io.github.thebusybiscuit.hotbarpets.groups.SpecialPets;
import io.github.thebusybiscuit.hotbarpets.groups.UtilityPets;
import io.github.thebusybiscuit.hotbarpets.listeners.DamageListener;
import io.github.thebusybiscuit.hotbarpets.listeners.FoodListener;
import io.github.thebusybiscuit.hotbarpets.listeners.GeneralListener;
import io.github.thebusybiscuit.hotbarpets.listeners.PhantomListener;
import io.github.thebusybiscuit.hotbarpets.listeners.ProjectileListener;
import io.github.thebusybiscuit.hotbarpets.listeners.SoulPieListener;
import io.github.thebusybiscuit.hotbarpets.listeners.TNTListener;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

public class HotbarPets extends JavaPlugin implements Listener, SlimefunAddon {

    private ItemGroup itemGroup;
    private static HotbarPets addon;
    private Config config;
    
    public Config config() {
    	return config;
    }
    
    public void setItemGroup(ItemGroup group) {
    	this.itemGroup = group;
    }
    
    @Override
    public void onEnable() {
    
    	addon = this;
    	Config cfg = new Config(this);
    	config = cfg;
    	
    	if(PluginIntegrations.isANOSFActive()) {
    		ANOSFIntegration.setup(this);
    	} else {
            itemGroup = new ItemGroup(
                new NamespacedKey(this, "pets"), 
        		CustomItemStack.create(
        			PetTexture.CATEGORY.getAsItem(), 
        			"&dHotbar Pets", 
        			"", 
        			"&a> Click to open"
        		)
        	);
    	}
    	
    	if(PluginIntegrations.isProtocolLibActive()) {
    		ProtocolLibIntegration.setup(this);
    	}


        // Add all the Pets via their Group class
        new FarmAnimals(this);
        new PeacefulAnimals(this);
        new PassiveMobs(this);
        new HostileMobs(this);
        new BossMobs(this);
        new UtilityPets(this);
        new SpecialPets(this);

        // Registering the Listeners
        new DamageListener(this);
        new FoodListener(this);
        new GeneralListener(this);
        new PhantomListener(this);
        new ProjectileListener(this);
        new SoulPieListener(this);
        new TNTListener(this);

        MoreHotbarPets.registerAll(this);
        
        // Registering our task
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new HotbarPetsRunnable(), 0L, 2L);
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Sniperkaos/HotbarPets1.21.8/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

	public static HotbarPets addon() {
		return addon;
	}
}
