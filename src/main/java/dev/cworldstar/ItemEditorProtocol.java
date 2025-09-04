package dev.cworldstar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import dev.cworldstar.libs.cwlib.AbstractSFAddon;

public class ItemEditorProtocol {
	
	protected static final List<BiFunction<ItemStack, Player, ItemStack>> passthroughs = new ArrayList<BiFunction<ItemStack, Player, ItemStack>>();
	
	
	/**
	 * 
	 * @param passthrough Adds a {@link BiFunction} passthrough handler to the LoreEditorProtocol. The BiFunction is Nullable.
	 * @return
	 */
	
	public static void passthrough(BiFunction<ItemStack, Player, ItemStack> passthrough) {
		passthroughs.add(passthrough);
	}
	
	private static ItemStack passthrough(Player p, ItemStack item) {
		ItemStack newItem = item.clone();
		 for(BiFunction<ItemStack, Player, ItemStack> passthrough : passthroughs) {
			 passthrough.apply(newItem, p);
		 }
		 return newItem;
	}
	
	/**
	 * Method to dynamically update itemstacks in a player's inventory. Will not work
	 * if in creative mode.
	 * @param slot The integer slot to update the new item in. Works off NMS values, so hotbar slot 0 is equal to 36 instead.
	 * @param newItem The item to update. The given item is affected by Passthrough, and it is not edited directly.
	 * @param who The player to send the packet to.
	 * 
	 * @author cworldstar
	 */
	public static void updateItem(int slot, ItemStack newItem, Player who) {
		if(who.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		PacketContainer container = new PacketContainer(PacketType.Play.Server.SET_SLOT);
		StructureModifier<Integer> intModifiers = container.getIntegers();
		intModifiers.writeSafely(0, -2); //-- window ID
		intModifiers.writeSafely(2, slot); //-- slot ID
		StructureModifier<ItemStack> itemModifiers = container.getItemModifier();
		itemModifiers.writeSafely(0, newItem); //-- item, wrapper for Slot Data
		ProtocolLibrary.getProtocolManager().sendServerPacket(who, container);
	}
	
	public static void start() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(PacketAdapter.params()
				.plugin(AbstractSFAddon.get())
				.listenerPriority(ListenerPriority.HIGH)
				.types(PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS)) 
			{
				@Override
				public void onPacketSending(PacketEvent event) {
					PacketContainer container = event.getPacket();
					PacketType type = event.getPacketType();
					Player p = event.getPlayer();					
					if(p.getGameMode() == GameMode.CREATIVE) {
						return;
					}
					if(type == PacketType.Play.Server.SET_SLOT) {
						StructureModifier<ItemStack> modifier = container.getItemModifier();
						 if(modifier.size() > 0) {
							 ItemStack item = modifier.readSafely(0);
							 item = passthrough(p, item);
							 modifier.writeSafely(0, item);
							 event.setPacket(container);
						 }
					} else {
						 StructureModifier<List<ItemStack>> modifier = container.getItemListModifier();
						 if(modifier.size() > 0) {
							 List<ItemStack> items = modifier.readSafely(0); 
							 items = items.stream().map(item -> passthrough(p, item)).collect(Collectors.toList());
							 modifier.writeSafely(0, items);
							 event.setPacket(container);
						 }
					}
				}
			});
	}
}
