package org.Prison.Punish;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Menu {

	public static void openInventory1(String uuid, String name, Player opening, String reason){
		MenuItem.whoBan.put(opening.getName(), name);
		MenuItem.whoBanUUID.put(opening.getName(), UUID.fromString(uuid));
		MenuItem.reason.put(opening.getName(), reason);
		MenuItem.inMenu.add(opening.getName());
		Inventory inv = MenuItem.createMenu(Bukkit.createInventory(null, 9 * 6, ChatColor.BLUE+ "Punishing " + name), opening);
		if (Files.getDataFile().contains("Log." + uuid + ".ID1")){
			int location = 45;
			int id = Files.getDataFile().getInt("Log." + uuid + ".idutil");
			for (;;){
				List<String> lore1 = new ArrayList<String>();
				String type = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Type");
				String punisher = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Punisher");
				String date = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Date");
				int level = Files.getDataFile().getInt("Log." + uuid + ".ID" + id + ".Level");
				String length = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Length");
				String reason1 = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Reason");
				ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
				String display = "";
				if (type.equals("Warn")){
					item = new ItemStack(Material.PAPER);
					display = "§e§lWarning";
				}
				if (type.equals("Chat")){
					item = new ItemStack(Material.BOOK_AND_QUILL);
					display = "§e§lChat Violation";
				}
				if (type.equals("Gen")){
					item = new ItemStack(Material.IRON_SWORD);
					display = "§e§lGeneral Violation";
				}
				if (type.equals("Hack")){
					item = new ItemStack(Material.COMMAND);
					display = "§e§lHacking Violation";
				}
				ItemMeta itemm = item.getItemMeta();
				itemm.setDisplayName(display);
				if (!type.equals("Warn")){
				lore1.add("");
				lore1.add("§7Level: §e" + level);
				lore1.add("§7Length: §e" + length);
				}
				lore1.add("");
				lore1.add("§7Reason:");
				lore1.addAll(format(reason1));
				lore1.add("");
				lore1.add("§7Staff: §e" + punisher);
				lore1.add("§7Date: §e" + date);
				itemm.setLore(lore1);
				item.setItemMeta(itemm);
				inv.setItem(location, item);
				id--;
				if (id == 0){
					break;
				}
				location++;
				if (location == 54){
					break;
				}
			}
		}
		opening.openInventory(MenuItem.createMenu(inv, opening));
	}
	
	public static void openInventory(String uuid, String name, Player opening){
		Inventory inv = Bukkit.createInventory(null, 9 * 4, ChatColor.BLUE + "Punish History");
		List<String> lore = new ArrayList<>();
		ItemStack skull = new ItemStack(397, 1, (byte)3);
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(name);
		skullm.setDisplayName("§b§l" + name + "'s Info");
		lore.add("");
		if (PunishAPI.ifPlayerIsMuted(uuid)){
			lore.add("§7Currently Muted: §aYes");
		}else{
			lore.add("§7Currently Muted: §cNo");
		}
		lore.add("");
		if (PunishAPI.ifPlayerIsTempbanned(uuid)){
			lore.add("§7Currently Tempbanned: §aYes");
		}else{
			lore.add("§7Currently Tempbanned: §cNo");
		}
		lore.add("");
		if (PunishAPI.ifPlayerIsBanned(uuid)){
			lore.add("§7Currently Perm banned: §aYes");
		}else{
			lore.add("§7Currently Perm banned: §cNo");
		}
		skullm.setLore(lore);
		skull.setItemMeta(skullm);
		inv.setItem(4, skull);
		lore.clear();
		
		if (Files.getDataFile().contains("Log." + uuid + ".ID1")){
			int location = 18;
			int id = Files.getDataFile().getInt("Log." + uuid + ".idutil");
			for (;;){
				List<String> lore1 = new ArrayList<String>();
				String type = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Type");
				String punisher = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Punisher");
				String date = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Date");
				int level = Files.getDataFile().getInt("Log." + uuid + ".ID" + id + ".Level");
				String length = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Length");
				String reason = Files.getDataFile().getString("Log." + uuid + ".ID" + id + ".Reason");
				ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
				String display = "";
				if (type.equals("Warn")){
					item = new ItemStack(Material.PAPER);
					display = "§e§lWarning";
				}
				if (type.equals("Chat")){
					item = new ItemStack(Material.BOOK_AND_QUILL);
					display = "§e§lChat Violation";
				}
				if (type.equals("Gen")){
					item = new ItemStack(Material.IRON_SWORD);
					display = "§e§lGeneral Violation";
				}
				if (type.equals("Hack")){
					item = new ItemStack(Material.COMMAND);
					display = "§e§lHacking Violation";
				}
				ItemMeta itemm = item.getItemMeta();
				itemm.setDisplayName(display);
				if (!type.equals("Warn")){
				lore1.add("");
				lore1.add("§7Level: §e" + level);
				lore1.add("§7Length: §e" + length);
				}
				lore1.add("");
				lore1.add("§7Reason:");
				lore1.addAll(format(reason));
				lore1.add("");
				lore1.add("§7Staff: §e" + punisher);
				lore1.add("§7Date: §e" + date);
				itemm.setLore(lore1);
				item.setItemMeta(itemm);
				inv.setItem(location, item);
				id--;
				if (id == 0){
					break;
				}
				location++;
				if (location == 36){
					break;
				}
			}
		}
		opening.openInventory(inv);
	}
	
	public static List<String> format(String s){
		List<String> real = new ArrayList<>();
		String line1 = "§e";
		String line2 = "";
		String line3 = "";
		int wordcount = 0;
		int whichline = 1;
		for (String word : s.split(" ")){
			wordcount++;
			if (whichline == 1) line1 += "§e" + word + " ";
			if (whichline == 2) line2 += "§e" + word + " ";
			if (whichline == 3) line3 += "§e" + word + " ";	
			if (wordcount == 6){
				wordcount = 0;
				whichline++;
			}
		}
		real.add(line1);
		if (line2 != "") real.add(line2);
		if (line3 != "") real.add(line3);
		return real;
	}
	
}
