package dev.cworldstar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.api.ChatColor;

public class FormatUtils {
	
	public static enum MessageType {
			MINI_MESSAGE,
			COLOR_CODES
	}
	
	public static String convertFromColorCodes(String from) {
		for(Character c : ChatColor.ALL_CODES.toCharArray()) {
			from = from.replace("" + ChatColor.COLOR_CHAR + c, getChatColorReplacement(c));
		}
		return from;
	}
	
	private static Map<Character, String> replacement = new HashMap<Character, String>();
	static {
		replacement.put('0', "<black>");
		replacement.put('1', "<dark_blue>");
        replacement.put('2', "<dark_green>");
        replacement.put('3', "<dark_aqua>");
        replacement.put('4', "<dark_red>");
        replacement.put('5', "<dark_purple>");
        replacement.put('6', "<gold>");
        replacement.put('7', "<gray>");
        replacement.put('8', "<dark_gray>");
        replacement.put('9', "<blue>");
        replacement.put('a', "<green>");
        replacement.put('b', "<aqua>");
        replacement.put('c', "<red>");
        replacement.put('d', "<light_purple>");
        replacement.put('e', "<yellow>");
        replacement.put('f', "<white>");
        replacement.put('m', "<st>");
        replacement.put('k', "<obf>");
        replacement.put('o', "<i>");
        replacement.put('l', "<b>");
        replacement.put('r', "<r>");
	}
	
	private static String getChatColorReplacement(char c) {
		return replacement.getOrDefault(c, "<red>");
	}

	private static final Pattern cmatcher = Pattern.compile("(§[a-zA-Z0-9])|(&[a-zA-Z0-9])");
	
	private static final MiniMessage MINI_MESSAGE_FORMATTER = MiniMessage.builder()
			.tags(TagResolver.standard())
			.build();
	
	public static String makeMachineCompletion(int work, int requiredWork) {
		double workCompletedPercent = ((double) work) / requiredWork;
		if(workCompletedPercent > 1) {
			return "<green>||||||||||||";
		}
		String processItemName = "||||||||||||";
		int substr = (int) Math.round(processItemName.length() * workCompletedPercent);
		String completed = "<green>" + processItemName.substring(0, substr) + "<red>";
		completed.replaceAll("|", "I");
		for(int x=substr; x<= processItemName.length(); x++) {
			completed = completed + "|";
		}
		return completed;
	}
	
	public FormatUtils() {
		throw new UnsupportedOperationException("This is a static class!");
	}
	
	public static Component convert(String text) {
		return mm(convertFromColorCodes(text));
	}
	
	/**
	 * This method creates a MiniMessage component from
	 * a text input. It supports legacy tags with &, and
	 * modern kyori tags as well.
	 * 
	 * @param text The minimessage text
	 * @return The resulting component from deserializing with standard tags, 
	 * or from translating color codes if legacy color codes are matched.
	 */
	public static Component createMiniMessageComponent(String text) {
		MessageType type = findMessageType(text);
		if(type.equals(MessageType.COLOR_CODES)) {
			return createComponent(text);
		}
		return MINI_MESSAGE_FORMATTER.deserialize(text).decoration(TextDecoration.ITALIC, false);
	}
	
	public static MessageType findMessageType(String string) {
		Matcher matcher = cmatcher.matcher(string);
		if(matcher.find()) {
			return MessageType.COLOR_CODES;
		}
		return MessageType.MINI_MESSAGE;
	}
	
	public static Component mm(String text) {
		return createMiniMessageComponent(text);
	}
	
	public static TextComponent createComponent(String text) {
		return Component.text(ChatColor.translateAlternateColorCodes('&', text));
	}
	
	public static List<Component> loreComponent(List<String> lore) {
		return lore.stream().map(str -> mm(str)).collect(Collectors.toList());
	}
	
	public static String formatString(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static Component formatAndCast(String s) {
		return Component.text(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static List<TextComponent> getLore(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		List<TextComponent> lore = meta.lore().stream().map(component->(TextComponent) component).collect(Collectors.toList());
		return lore;
	}
	
	public static TextComponent replace(TextComponent toReplace, String pattern, String newValue) {
		return (TextComponent) toReplace.replaceText(TextReplacementConfig.builder().match(pattern).replacement(newValue).build());
	}
	
	public static List<TextComponent> replaceAll(List<TextComponent> toReplace, String pattern, String newValue) {
		toReplace.replaceAll(textComponent -> {
			 return replace(textComponent, pattern, newValue);
		});
		return toReplace;
	}
	
	public static TextComponent asText(String s) {
		return Component.text(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static TextComponent format(String s) {
		return asText(s);
	}

	public static @NotNull List<Component> lore(String[] lore) {
		return loreComponent(Arrays.asList(lore));
	}
}
