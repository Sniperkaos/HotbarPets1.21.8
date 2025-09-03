package dev.cworldstar.hotbarpets.pets;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.cworldstar.FormatUtils;
import dev.cworldstar.builders.PlayerHeadBuilder;
import dev.cworldstar.hotbarpets.impl.PetActivatedMarker;
import dev.cworldstar.hotbarpets.impl.PetRemovalMarker;
import dev.cworldstar.hotbarpets.impl.SequencedPet;
import dev.cworldstar.hotbarpets.impl.TimedFeeder;
import io.github.thebusybiscuit.hotbarpets.HotbarPet;
import io.github.thebusybiscuit.hotbarpets.HotbarPets;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public class PistonPet extends HotbarPet implements SequencedPet, PetRemovalMarker, TimedFeeder, PetActivatedMarker {

	private static final AttributeModifier PISTON_PET_ATTRIBUTE_MODIFIER = new AttributeModifier(new NamespacedKey(HotbarPets.addon(), "PISTON_PET_ATTRIBUTE_MODIFIER"), 1, Operation.ADD_NUMBER);
	
	private static final ItemStack PISTON_PET_HEAD = new PlayerHeadBuilder().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQyMGExMjVjMmNjMWE2NDQzNjNhYzNlMDg1MDRkNjYwNTRiMmE3ZTRlMDVlYTU2MGYyNDNkZjVmNzdiYWY0MyJ9fX0=").name("<gradient:dark_gray:gray:#964B00>Piston Pet").lore(new String[] {
			"<gray>Why can't I be in the plugin?",
			"<gray>I'm awesome!",
			"",
			"<gray>Favorite Food: <red>Redstone Dust",
			"",
			"<gray>- <white>While in your hotbar, grants you",
			"<white> an additional block of reach. ",
			"",
			"<gray>Must be fed every few minutes."
	}).item();
	
	public PistonPet() {
		super(HotbarPets.addon().getItemGroup(), new SlimefunItemStack("PISTON_PET", PISTON_PET_HEAD), new ItemStack(Material.REDSTONE), new ItemStack[] {
				new ItemStack(Material.PISTON),new ItemStack(Material.PISTON),new ItemStack(Material.PISTON),
				SlimefunItems.NECROTIC_SKULL.asOne(), SlimefunItems.ELECTRIC_MOTOR.asOne(), SlimefunItems.INFUSED_MAGNET.asOne(),
				SlimefunItems.FUEL_BUCKET.asOne(), SlimefunItems.ANDROID_MEMORY_CORE.asOne(), SlimefunItems.RAINBOW_CONCRETE.asOne()
		});
		addItemHandler(new ItemUseHandler() {
			@Override
			public void onRightClick(PlayerRightClickEvent e) {
				Player player = e.getPlayer();
				AttributeInstance instance = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
				if(instance.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) != null) {
					player.sendMessage(FormatUtils.mm("<gray>This piston pet is activated!"));
				} else {
					boolean success = tryFeed(PistonPet.this, player);
					if(success) {
						player.sendMessage(FormatUtils.mm("<gray>Your piston pet was fed!"));
						AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
						if(attribute.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) == null) {
							attribute.addModifier(PISTON_PET_ATTRIBUTE_MODIFIER);
						}
					} else {
						player.sendMessage(FormatUtils.mm("<gray>You do not have enough <red>Redstone Dust<gray>."));
					}
				}
			}	
		});
	}

	@Override
	public void run(Player player) {
		boolean success = tryFeed(this, player);
		if(success) {
			AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
			if(attribute.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) == null) {
				attribute.addModifier(PISTON_PET_ATTRIBUTE_MODIFIER);
			}
		} else {
			AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
			if(attribute.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) != null) {
				attribute.removeModifier(PISTON_PET_ATTRIBUTE_MODIFIER);
			}
		}
	}

	@Override
	public void onPetRemoval(Player player) {
		player.sendMessage(FormatUtils.mm("<red>You have lost the effects of the piston pet."));
		AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
		if(attribute.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) != null) {
			attribute.removeModifier(PISTON_PET_ATTRIBUTE_MODIFIER);
		}
	}

	@Override
	public void onPetActivated(Player player) {
		player.sendMessage(FormatUtils.mm("<gray>You have gained the effects of the piston pet!"));
		AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
		if(attribute.getModifier(PISTON_PET_ATTRIBUTE_MODIFIER.key()) == null) {
			attribute.addModifier(PISTON_PET_ATTRIBUTE_MODIFIER);
		}
	}
}
