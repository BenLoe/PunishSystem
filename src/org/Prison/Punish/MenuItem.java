package org.Prison.Punish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.Prison.Main.Items.ItemAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
public enum MenuItem {
		
	PLAYER_SKULL(4),
	
	CHAT_HEADER(11),
	
	GEN_HEADER(13),
	
	HACK_HEADER(15),
	
	WARN(17),
	
	CHAT_LEVEL1(20),
	
	CHAT_LEVEL2(29),
	
	CHAT_LEVEL3(38),
	
	GEN_LEVEL1(22),
	
	GEN_LEVEL2(31),
	
	HACK_LEVEL1(24),
	
	HACK_LEVEL2(33);
	
	
	
	private final int i;
	public static HashMap<String,String> whoBan = new HashMap<String,String>();
	public static HashMap<String,UUID> whoBanUUID = new HashMap<String,UUID>();
	public static HashMap<String,String> reason = new HashMap<String,String>();
	public static List<String> inMenu = new ArrayList<String>();
	
	private MenuItem(int i){
		this.i = i;
	}
	
	private MenuItem getItem(){
		for (MenuItem m : values()){
			if (m.i == i){
				return m;
			}
		}
		return PLAYER_SKULL;
	}
	
	public void wasClicked(Player p){
		switch(getItem()){
		case WARN:{
			PunishAPI.attemptWarn(whoBan.get(p.getName()), p, reason.get(p.getName()));
			p.closeInventory();
		}
		break;
		case CHAT_HEADER:{
			break;
		}
		case CHAT_LEVEL1:{
		 PunishAPI.attemptMuteLevel(whoBan.get(p.getName()), p, 1, reason.get(p.getName()));
		 p.closeInventory();
		}
		break;
		case CHAT_LEVEL2:{
			if (p.hasPermission("Punish.Level2")){
				PunishAPI.attemptMuteLevel(whoBan.get(p.getName()), p, 2, reason.get(p.getName()));
				p.closeInventory();
			}
		}
		break;
		case CHAT_LEVEL3:{
			if (p.hasPermission("Punish.Level2")){
				PunishAPI.attemptMuteLevel(whoBan.get(p.getName()), p, 3, reason.get(p.getName()));
				p.closeInventory();
			}
		}
		break;
		case GEN_HEADER:{
			break;
		}
		case GEN_LEVEL1:{
			PunishAPI.attemptGenBan(whoBan.get(p.getName()), p, 1, reason.get(p.getName()));
			p.closeInventory();
		}
		break;
		case GEN_LEVEL2:{
			if (p.hasPermission("Punish.Level2")){
				PunishAPI.attemptGenBan(whoBan.get(p.getName()), p, 2, reason.get(p.getName()));
				p.closeInventory();
			}
		}
		break;
		case HACK_HEADER:{
			break;
		}
		case HACK_LEVEL1:{
			PunishAPI.attemptHackBan(whoBan.get(p.getName()), p, 1, reason.get(p.getName()));
			p.closeInventory();
		}
		break;
		case HACK_LEVEL2:{
			if (p.hasPermission("Punish.Level2")){
				PunishAPI.attemptHackBan(whoBan.get(p.getName()), p, 2, reason.get(p.getName()));
				p.closeInventory();
			}
		}
		break;
		case PLAYER_SKULL:{
			break;
		}
		default:
			break;
		}
	}
	
	public ItemStack getItemFor(Player p){
		String punished = whoBan.get(p.getName());
		UUID uuid = whoBanUUID.get(p.getName());
		switch(getItem()){
		case WARN:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bWarning");
			lore.add("");
			lore.add("§7Click to warn player");
			lore.add("");
			lore.add("§7In most cases, a level 1 violation warrants a");
			lore.add("§7warning before punishing. If the player continues");
			lore.add("§7or has a warning for the same offense within the");
			lore.add("§7last 2 weeks, apply the punishment.");
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case CHAT_HEADER:{
			List<String> lore = new ArrayList<>();
			lore.add("§7Any violation made in the chat.");
			return ItemAPI.getItem(Material.BOOK_AND_QUILL, "§bChat Violations", lore);
		}
		case CHAT_LEVEL1:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)5);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 1 - Chat Violation");
			lore.add("");
			lore.add("§7Click to punish for: §e" + formatMinute(MinuteCal.calMuteTime(uuid.toString(), 1)));
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Chat.level1")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Chat.level1");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Spam");
			lore.add("§7● Excessive caps");
			lore.add("§7● Rudeness");
			lore.add("§7● Excessive use of fould swear words");
			lore.add("§7● Light advertising");
			lore.add("§7● Being inappropriate or excessive arguing");
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case CHAT_LEVEL2:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)4);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 2 - Chat Violation");
			lore.add("");
			lore.add("§7Click to punish for: §e" + formatMinute(MinuteCal.calMuteTime(uuid.toString(), 2)));
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Chat.level2")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Chat.level2");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Extreme rudeness");
			lore.add("§7● Discrimination");
			lore.add("§7● Using swear words in extreme contexts");
			lore.add("§7● Heavy advertising");
			lore.add("§7● Death threats");
			lore.add("§7● Revealing others personal information");
			lore.add("§7● Scamming players or making real life transactions");
			lore.add("§7● Linking inappropriate websites (porn, etc.)");
			if (!p.hasPermission("Punish.Level2")){
				lore.add("");
				lore.add("§7Must be §4§lMOD §7to do level 2+");
			}
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case CHAT_LEVEL3:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)14);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 3 - Chat Violation");
			lore.add("");
			lore.add("§7Click to punish for: §e" + formatMinute(MinuteCal.calMuteTime(uuid.toString(), 3)));
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Chat.level3")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Chat.level3");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Joining server and immediately heavy advertising");
			lore.add("§7● Can be given after excessive chat violations");
			lore.add("§7● Extreme discrimination");
			if (!p.hasPermission("Punish.Level2")){
				lore.add("");
				lore.add("§7Must be §4§lMOD §7to do level 2+");
			}
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case GEN_HEADER:{
			List<String> lore = new ArrayList<>();
			lore.add("§7General ingame violations not");
			lore.add("§7involving hacking.");
			return ItemAPI.getItem(Material.IRON_SWORD, "§bGeneral Violations", lore);
		}
		case GEN_LEVEL1:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)5);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 1 - General Violation");
			lore.add("");
			lore.add("§7Click to punish for: §e" + formatMinute(MinuteCal.calGenTime(uuid.toString(), 1)));
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Gen.level1")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Gen.level1");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Abusing known glitches on purpose");
			lore.add("§7● Using alts to have an advantage");
			lore.add("§7● Co operating with another player to be given an advantage");
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case GEN_LEVEL2:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)4);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 2 - General Violation");
			lore.add("");
			lore.add("§7Click to punish for: §ePermanent");
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Gen.level2")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Gen.level2");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Offensive/Inappropriate cape, skin or name");
			if (!p.hasPermission("Punish.Level2")){
				lore.add("");
				lore.add("§7Must be §4§lMOD §7to do level 2+");
			}
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case HACK_HEADER:{
			List<String> lore = new ArrayList<>();
			lore.add("§7Any violation involving the use of.");
			lore.add("§7a modified client.");
			return ItemAPI.getItem(Material.COMMAND, "§bHacking Violations", lore);
		}
		case HACK_LEVEL1:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)5);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 1 - Hacking Violation");
			lore.add("");
			lore.add("§7Click to punish for: §e" + formatMinute(MinuteCal.calHackTime(uuid.toString(), 1)));
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Hack.level1")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Hack.level1");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Unapproved mods that are not hacks");
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case HACK_LEVEL2:{
			List<String> lore = new ArrayList<>();
			ItemStack item = new ItemStack(159, 1, (byte)4);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("§bLevel 2 - Hacking Violation");
			lore.add("");
			String util = "Permanent";
			if (MinuteCal.calHackTime(uuid.toString(), 2) > 0){
				util = formatMinute(MinuteCal.calHackTime(uuid.toString(), 2));
			}
			lore.add("§7Click to punish for: §e" + util);
			int priors = 0;
			if (Files.getDataFile().contains("Log." + uuid + ".Hack.level2")){
				priors = Files.getDataFile().getInt("Log." + uuid + ".Hack.level2");
			}
			lore.add("§7Previous punishments: §e" + priors);
			lore.add("");
			lore.add("§7● Hacking");
			if (!p.hasPermission("Punish.Level2")){
				lore.add("");
				lore.add("§7Must be §4§lMOD §7to do level 2+");
			}
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			return item;
		}
		case PLAYER_SKULL:{
			List<String> lore = new ArrayList<>();
			ItemStack skull = new ItemStack(397, 1, (byte)3);
			SkullMeta skullm = (SkullMeta) skull.getItemMeta();
			skullm.setOwner(punished);
			skullm.setDisplayName("§b§l" + punished + "'s Info");
			lore.add("");
			lore.add("§7Punishing for:");
			lore.addAll(Menu.format(reason.get(p.getName())));
			lore.add("");
			if (PunishAPI.ifPlayerIsMuted(uuid.toString())){
				lore.add("§7Currently Muted: §aYes");
			}else{
				lore.add("§7Currently Muted: §cNo");
			}
			if (PunishAPI.ifPlayerIsTempbanned(uuid.toString())){
				lore.add("§7Currently Tempbanned: §aYes");
			}else{
				lore.add("§7Currently Tempbanned: §cNo");
			}
			if (PunishAPI.ifPlayerIsBanned(uuid.toString())){
				lore.add("§7Currently Perm banned: §aYes");
			}else{
				lore.add("§7Currently Perm banned: §cNo");
			}
			skullm.setLore(lore);
			skull.setItemMeta(skullm);
			return skull;
		}
		default:
			break;
		}
		
		return new ItemStack(Material.AIR);
	}
	
	public int getSlot(){
		return this.i;
	}
	public static MenuItem getItemFromSlot(int slot){
		for (MenuItem m : values()){
				if (slot == m.i){
					return m;
				}
		}
		return MenuItem.PLAYER_SKULL;
	}
	public static boolean wasAItem(int slot){
		for (MenuItem m : values()){
				if (slot == m.i){
					return true;
			}
		}
		return false;
	}
	
	public static Inventory createMenu(Inventory inv, Player p){
		for (MenuItem m : values()){
				inv.setItem(m.getSlot(), m.getItemFor(p));
		}
		return inv;
	}
	
	public static String formatMinute(int minutes){
		if (minutes < 60){
			return minutes + " minutes";
		}
		if (minutes >= 60 && minutes < 1440){
			return (minutes / 60) + " hours";
		}
		if (minutes >= 1440){
			return (minutes / 1440) + " days";
		}
		return "Broken";
	}
	

}
