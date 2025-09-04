package io.github.thebusybiscuit.hotbarpets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.ItemEditorProtocol;
import dev.cworldstar.PluginIntegrations;
import dev.cworldstar.hotbarpets.impl.PetActivatedMarker;
import dev.cworldstar.hotbarpets.impl.PetRemovalMarker;
import dev.cworldstar.hotbarpets.impl.SequencedPet;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

public class HotbarPetsRunnable implements Runnable {

    private HashMap<UUID, List<HotbarPet>> activeHotbarPets = new HashMap<UUID, List<HotbarPet>>(); 
    
    public boolean isHotbarPetActive(Player p, HotbarPet pet) {
    	if(!activeHotbarPets.containsKey(p.getUniqueId())) {
    		activeHotbarPets.put(p.getUniqueId(), new ArrayList<HotbarPet>());
    	}
    	
    	List<HotbarPet> pets = activeHotbarPets.get(p.getUniqueId());
    	if(pets.contains(pet)) {
    		return true;
    	}
    	return false;
    }
    
    public void makeHotbarPetActive(Player p, HotbarPet pet) {
    	if(!activeHotbarPets.containsKey(p.getUniqueId())) {
    		activeHotbarPets.put(p.getUniqueId(), new ArrayList<HotbarPet>());
    	}
    	
    	Bukkit.getLogger().log(Level.INFO, "making pet active " + pet.getId());
    	
    	List<HotbarPet> pets = activeHotbarPets.get(p.getUniqueId());
    	if(!pets.contains(pet)) {
    		if(pet instanceof PetActivatedMarker) {
    			((PetActivatedMarker) pet).onPetActivated(p);
    		}
    		pets.add(pet);
    	}
    }	
    
    public void validateHotbarPets(Player p, List<HotbarPet> pets) {
    	if(!activeHotbarPets.containsKey(p.getUniqueId())) {
    		activeHotbarPets.put(p.getUniqueId(), new ArrayList<HotbarPet>());
    	}
    	
    	List<HotbarPet> activePets = activeHotbarPets.get(p.getUniqueId()).stream().toList();
    	for(HotbarPet pet : activePets) {
    		if(!pets.contains(pet)) {
    			if(pet instanceof PetRemovalMarker) {
    				((PetRemovalMarker) pet).onPetRemoval(p);
    			}
    			Bukkit.getLogger().log(Level.INFO, "lost pet " + pet.getId());
    			makeHotbarPetInactive(p, pet);
    		}
    	}
    }
    
    public void makeHotbarPetInactive(Player p, HotbarPet pet) {
    	if(!activeHotbarPets.containsKey(p.getUniqueId())) {
    		activeHotbarPets.put(p.getUniqueId(), new ArrayList<HotbarPet>());
    	}
    	
    	Bukkit.getLogger().log(Level.INFO, "making pet inactive " + pet.getId());
    	
    	List<HotbarPet> pets = activeHotbarPets.get(p.getUniqueId());
    	if(pets.contains(pet)) {
    		pets.remove(pet);
    	}
    	activeHotbarPets.put(p.getUniqueId(), pets);
    }
    
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
        	List<HotbarPet> pets = new ArrayList<HotbarPet>();
            for (int i = 0; i < 9; ++i) {
                ItemStack item = p.getInventory().getItem(i);

                if (item == null || item.getType() == Material.AIR) {
                	ItemEditorProtocol.updateItem(i, new ItemStack(Material.AIR), p);
                    continue;
                }
                
                /**
                 *  handle {@link dev.cworldstarimpl.SequencedPet}
                 */
                if(isPet(item)) {
                	SlimefunItem sfItem = SlimefunItem.getByItem(item);
                	if(sfItem != null) {
                		
                    	HotbarPet pet = (HotbarPet) sfItem;
                    	pets.add(pet);
                    	
                    	if(!isHotbarPetActive(p, pet)) {
                    		makeHotbarPetActive(p, pet);
                    	}
                    	
                    	if(sfItem instanceof SequencedPet) {
                    		if(sfItem instanceof TimedFeeder) {
                    			// timed feeders implement a different type of checkAndConsumeFood, so we just run the SequencedPet.
                    			((SequencedPet) sfItem).run(p);
                    		} else {
                            	if(pet.checkAndConsumeFood(p)) {
                            		((SequencedPet) sfItem).run(p);
                        		}
                    		}
                    	}
                	}
                }
                
        		if(PluginIntegrations.isProtocolLibActive()) {
        			ItemEditorProtocol.updateItem(i, item, p);
        		}
            }
            validateHotbarPets(p, pets);
        }
    }

    @SuppressWarnings("unused")
	private boolean isPet(ItemStack item, HotbarPet pet) {
        return pet != null && SlimefunUtils.isItemSimilar(item, pet.getItem(), true);
    }
    
    private boolean isPet(ItemStack item) {
    	SlimefunItem sfItem = SlimefunItem.getByItem(item);
    	if(item == null) {
    		return false;
    	}
    	return (sfItem instanceof HotbarPet);
    }
}
