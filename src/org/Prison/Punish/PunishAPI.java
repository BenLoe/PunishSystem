package org.Prison.Punish;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishAPI {
	
	public static Calendar c = Calendar.getInstance();
	
	public static void attemptWarn(String p, Player warner, String reason){
		if (!canBePunished(p)){
			warner.sendMessage(ChatColor.RED + "Player cannot be punished.");
			return;
		}
		if (p.equals(warner.getName())){
			warner.sendMessage(ChatColor.RED + "You wanna warn yourself? Nice try.");
			
		}
		String uuid = "";
		boolean hasntJoined = false;
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				warner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		sendRightPlayers("§c§l" + p + "§f warned by §c§l" + warner.getName(), warner);
		if (onlinePlayer(p)){
			Player warned = Bukkit.getPlayer(p);
			warned.sendMessage("");
			warned.sendMessage("§4§l>> §cA staff member has warned you for: §7" + reason);
			warned.sendMessage("");
			warner.sendMessage("§c§l" + p + "§f was warned");
		}else{
			warner.sendMessage("§c§l" + p + "§f isn't online, so they will be warned when they return.");
			Files.getDataFile().set("OfflineWarn." + uuid, reason);
			Files.saveDataFile();
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		c.setTime(d1);
		String date = df.format(c.getTime().getTime());
		addBetterLog(uuid, "Warn", warner.getName(), date, 0, "-", reason);
		if (hasntJoined){
			warner.sendMessage(ChatColor.RED + "WARNING: Warned player has never joined the server.");
		}
	}
	
	public static void attemptMuteLevel(String p, Player muter, int level, String reason){
		if (!canBePunished(p)){
			muter.sendMessage(ChatColor.RED + "Player cannot be punished.");
			return;
		}
		if (p.equals(muter.getName())){
			muter.sendMessage(ChatColor.RED + "You wanna mute yourself? Nice try.");
			
		}
		String uuid = "";
		boolean hasntJoined = false;
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				muter.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		if (level > 3 || level < 1){
			muter.sendMessage(ChatColor.RED + "Level can only be 1, 2 or 3.");
			return;
		}
		int minutes = MinuteCal.calMuteTime(uuid, level);
		boolean wasMuted = ifPlayerIsMuted(uuid);
		Cooldown.setCooldown(uuid, minutes, "Mute");
		String time = Cooldown.getTimeLeftAlt(uuid, "Mute");
		sendRightPlayers("§c§l" + p + "§f muted by §c§l" + muter.getName() + "§f for §c§l" + time + "§f.", muter);
		if (onlinePlayer(p)){
			Player muted = Bukkit.getPlayer(p);
			muted.sendMessage("§8§l===============================================");
			muted.sendMessage("§cYou have been muted for §4" + time + " §c.");
			muted.sendMessage("§cReason: §4" + reason);
			muted.sendMessage("§8§l===============================================");
		}
		if (wasMuted){
			muter.sendMessage("§c§l" + p + "§f was already muted, so their mute time was reset to §c§l" + time + "§f.");
		}else{
			muter.sendMessage("§c§l" + p + "§f has been muted for §c§l" + time + "§f.");
		}
		Files.getDataFile().set("Currents." + uuid + ".mutereason", reason);
		Files.saveDataFile();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		c.setTime(d1);
		String date = df.format(c.getTime().getTime());
		addBetterLog(uuid, "Chat", muter.getName(), date, level, time, reason);
		if (hasntJoined){
			muter.sendMessage(ChatColor.RED + "WARNING: Muted player has never joined the server.");
		}
	}
	
	public static void attemptGenBan(String p, Player banner, int level, String reason){
		if (!canBePunished(p)){
			banner.sendMessage(ChatColor.RED + "Player cannot be punished.");
			return;
		}
		if (p.equals(banner.getName())){
			banner.sendMessage(ChatColor.RED + "You wanna punish yourself? Nice try.");
			
		}
		boolean hasntJoined = false;
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				banner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		
		if (level != 1 && level != 2){
			banner.sendMessage(ChatColor.RED + "Level can only be 1 or 2.");
			return;
		}
		int minutes = MinuteCal.calGenTime(uuid, level);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		c.setTime(d1);
		String date = df.format(c.getTime().getTime());
		if (minutes > 0){
			boolean wasBanned = ifPlayerIsTempbanned(uuid);
			Cooldown.setCooldown(uuid, minutes, "Tempban");
			String time = Cooldown.getTimeLeftAlt(uuid, "Tempban");
			sendRightPlayers("§c§l" + p + "§f tempbanned by §c§l" + banner.getName() + "§f for §c§l" + time + "§f.", banner);
			Files.getDataFile().set("Currents." + uuid + ".tempreason", reason);
			Files.saveDataFile();
			addBetterLog(uuid, "Gen", banner.getName(), date, level, time, reason);
			if (wasBanned){
				banner.sendMessage("§c§l" + p + "§f was already temp banned, so their temp ban time was reset to §c§l" + time + "§f.");
			}else{
				banner.sendMessage("§c§l" + p + "§f has been temp banned for §c§l" + time + "§f.");
			}
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lYour access to this server has been suspended\n§7You may rejoin in §7§l" + Cooldown.getTimeLeftAlt(uuid, "Tempban") + "\n§f\n§7Reason: §f" + reason);
			}
		}else{
			sendRightPlayers("§c§l" + p + "§f banned permanently by §c§l" + banner.getName() + "§f.", banner);
			banner.sendMessage("§c§l" + p + "§f is now banned permanently.");
			List<String> banned = Files.getDataFile().getStringList("Banned");
			banned.add(uuid);
			Files.getDataFile().set("Banned", banned);
			Files.getDataFile().set("Currents." + uuid + ".banreason", reason);
			Files.saveDataFile();
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lYour access to this server has been suspended §c§lPermanently\n§f\n§7Reason: §f" + reason);
			}
			addBetterLog(uuid, "Gen", banner.getName(), date, level, "Permanently", reason);
		}	
		
		if (hasntJoined){
			banner.sendMessage(ChatColor.RED + "WARNING: Punished player has never joined the server.");
		}
	}
	
	public static void attemptHackBan(String p, Player banner, int level, String reason){
		if (!canBePunished(p)){
			banner.sendMessage(ChatColor.RED + "Player cannot be punished.");
			return;
		}
		if (p.equals(banner.getName())){
			banner.sendMessage(ChatColor.RED + "You wanna punish yourself? Nice try.");
			
		}
		boolean hasntJoined = false;
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				banner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		
		if (level != 1 && level != 2){
			banner.sendMessage(ChatColor.RED + "Level can only be 1 or 2.");
			return;
		}
		int minutes = MinuteCal.calHackTime(uuid, level);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		c.setTime(d1);
		String date = df.format(c.getTime().getTime());
		
		if (minutes > 0){
			boolean wasBanned = ifPlayerIsTempbanned(uuid);
			Cooldown.setCooldown(uuid, minutes, "Tempban");
			String time = Cooldown.getTimeLeftAlt(uuid, "Tempban");
			sendRightPlayers("§c§l" + p + "§f tempbanned by §c§l" + banner.getName() + "§f for §c§l" + time + "§f.", banner);
			Files.getDataFile().set("Currents." + uuid + ".tempreason", reason);
			Files.saveDataFile();
			addBetterLog(uuid, "Hack", banner.getName(), date, level, time, reason);
			if (wasBanned){
				banner.sendMessage("§c§l" + p + "§f was already temp banned, so their temp ban time was reset to §c§l" + time + "§f.");
			}else{
				banner.sendMessage("§c§l" + p + "§f has been temp banned for §c§l" + time + "§f.");
			}
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lYour access to this server has been suspended\n§7You may rejoin in §7§l" + Cooldown.getTimeLeftAlt(uuid, "Tempban") + "\n§f\n§7Reason: §f" + reason);
			}
		}else{
			sendRightPlayers("§c§l" + p + "§f banned permanently by §c§l" + banner.getName() + "§f.", banner);
			banner.sendMessage("§c§l" + p + "§f is now banned permanently.");
			List<String> banned = Files.getDataFile().getStringList("Banned");
			banned.add(uuid);
			Files.getDataFile().set("Banned", banned);
			Files.getDataFile().set("Currents." + uuid + ".banreason", reason);
			Files.saveDataFile();
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lYour access to this server has been suspended §c§lPermanently\n§f\n§7Reason: §f" + reason);
			}
			addBetterLog(uuid, "Hack", banner.getName(), date, level, "Permanently", reason);
		}	
		
		if (hasntJoined){
			banner.sendMessage(ChatColor.RED + "WARNING: Punished player has never joined the server.");
		}
	}
	
	public static void attemptMute(String p, Player muter, int minutes, String reason){
		if (!canBePunished(p)){
			muter.sendMessage(ChatColor.RED + "Player cannot be muted.");
			return;
		}
		if (p.equals(muter.getName())){
			muter.sendMessage(ChatColor.RED + "You wanna mute yourself? Nice try.");
			
		}
		String uuid = "";
		boolean hasntJoined = false;
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				muter.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		sendRightPlayers("§c§l" + p + "§f muted by §c§l" + muter.getName() + "§f for §c§l" + minutes + "§f minutes. Reason: §c§l " + reason, muter);
		if (onlinePlayer(p)){
			Player muted = Bukkit.getPlayer(p);
			muted.sendMessage("§8§l===============================================");
			muted.sendMessage("§cYou have been muted by §4" + muter.getName() + " §cfor §4" + minutes + " §cminutes.");
			muted.sendMessage("§cReason: §4" + reason);
			muted.sendMessage("§8§l===============================================");
		}
		if (ifPlayerIsMuted(uuid)){
			muter.sendMessage("§c§l" + p + "§f was already muted, so their mute time was reset to §c§l" + minutes + "§f minutes.");
		}else{
			muter.sendMessage("§c§l" + p + "§f has been muted for §c§l" + minutes + "§f minutes.");
		}
		if (hasntJoined){
			muter.sendMessage(ChatColor.RED + "WARNING: Muted player has never joined the server.");
		}
	}
	
	public static void attemptTempBan(String p, Player banner, int minutes, String reason){
		if (!canBePunished(p)){
			banner.sendMessage(ChatColor.RED + "Player cannot be temp banned.");
			return;
		}
		if (p.equals(banner.getName())){
			banner.sendMessage(ChatColor.RED + "You wanna temp ban yourself? Nice try.");
			
		}
		boolean hasntJoined = false;
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				banner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		sendRightPlayers("§c§l" + p + "§f tempbanned by §c§l" + banner.getName() + "§f for §c§l" + minutes + "§f minutes. Reason: §c§l" + reason, banner);
		Files.getDataFile().set("Currents." + uuid + ".tempreason", reason);
		Files.saveDataFile();
		addToLog("Tempban: " + p + ", " + reason + ", Time:" + minutes + ", Issued By: " + banner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
		if (ifPlayerIsTempbanned(uuid)){
			banner.sendMessage("§c§l" + p + "§f was already temp banned, so their temp ban time was reset to §c§l" + minutes + "§f minutes.");
		}else{
			banner.sendMessage("§c§l" + p + "§f has been temp banned for §c§l" + minutes + "§f minutes.");
		}
		if (hasntJoined){
			banner.sendMessage(ChatColor.RED + "WARNING: Tempbanned player has never joined the server.");
		}
		if (onlinePlayer(p)){
			Bukkit.getPlayer(p).kickPlayer("§c§lBanned for" + Cooldown.getTimeLeft(uuid, "Tempban") + "\n§f" + reason);
		}
	}

	public static void attemptunMute(String p, Player unmuter){
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				unmuter.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		if (ifPlayerIsMuted(uuid)){
			sendRightPlayers("§c§l" + p + "§f unmuted by §c§l" + unmuter.getName(), unmuter);
			unmuter.sendMessage("§c§l" + p + "§f was unmuted.");
			addToLog("Unmute: " + p + ", No reason, Time:Immediately, Issued By: " + unmuter.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			Cooldown.resetCooldown("Mute", uuid);
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).sendMessage("§a§lYou have been unmuted.");
			}
		}else{
			unmuter.sendMessage("§cPlayer not muted.");
		}
	}
	
	public static void attemptBan(String p, Player banner, String reason){
		if (!canBePunished(p)){
			banner.sendMessage(ChatColor.RED + "Player cannot be banned.");
			return;
		}
		if (p.equals(banner.getName())){
			banner.sendMessage(ChatColor.RED + "You wanna ban yourself? Nice try.");
			
		}
		boolean hasntJoined = false;
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				banner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
			if (!hasJoined(p)) hasntJoined = true;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		if (ifPlayerIsBanned(uuid)){
			banner.sendMessage("§cAlready banned.");
		}else{
			sendRightPlayers("§c§l" + p + "§f banned by §c§l" + banner.getName() + "§f for §c§l" + reason, banner);
			banner.sendMessage("§c§l" + p + "§f is now banned.");
			List<String> banned = Files.getDataFile().getStringList("Banned");
			banned.add(uuid);
			addToLog("Ban: " + p + ", " + reason + ", Time:Permanent, Issued By: " + banner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			Files.getDataFile().set("Banned", banned);
			Files.getDataFile().set("Currents." + uuid + ".banreason", reason);
			Files.saveDataFile();
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lBanned permanently:\n§f" + reason);
			}
		}
		if (hasntJoined){
			banner.sendMessage(ChatColor.RED + "WARNING: Banned player has never joined the server.");
		}
	}
	
	public static void attemptUnBan(String p, Player unbanner){
		String uuid = "";
		if (!onlinePlayer(p)){
			try{
				uuid = UUIDFetcher.getUUIDOf(p).toString();
			}catch(Exception e){
				unbanner.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
				return;
			}
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
		if (ifPlayerIsBanned(uuid) || ifPlayerIsTempbanned(uuid)){
			unbanner.sendMessage("§c§l" + p + "§f has been unbanned.");
			sendRightPlayers("§c§l" + p + "§f unbanned by §c§l" + unbanner.getName(), unbanner);
			if (ifPlayerIsBanned(uuid)){
				addToLog("Unban: " + p + ", No reason, Time:Immediately, Issued By: " + unbanner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
				List<String> banned = Files.getDataFile().getStringList("Banned");
				banned.remove(uuid);
				Files.getDataFile().set("Banned", banned);
				Files.getDataFile().set("Players." + uuid + ".BanReason", null);
				Files.saveDataFile();
			}else{
				addToLog("Untempban: " + p + ", No reason, Time:Immediately, Issued By: " + unbanner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
				Files.getDataFile().set("Players." + uuid + ".TempBan", null);
				Cooldown.resetCooldown("Tempban", uuid);
			}
		}else{
			unbanner.sendMessage("§cPlayer is not banned.");
		}
	}
	
	public static void attemptKick(String p, Player kicker, String reason){
		if (!canBePunished(p)){
			kicker.sendMessage(ChatColor.RED + "Player cannot be kicked.");
			return;
		}
		if (p == kicker.getName()){
			kicker.sendMessage(ChatColor.RED + "You wanna kick yourself? Nice try.");
	
		}
		String uuid = "";
		if (!onlinePlayer(p)){
			kicker.sendMessage("§cPlayer not online");
			return;
		}else{
			uuid = Bukkit.getPlayer(p).getUniqueId().toString();
		}
			kicker.sendMessage("§c§l" + p + "§f has been kicked.");
			Bukkit.getPlayer(p).kickPlayer("§c§lKicked\n§f" + reason);
			addToLog("Kick: " + p + ", " + reason + ", Time:Immediately, Issued By: " + kicker.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			sendRightPlayers("§c§l" + p + "§f kicked by §c§l" + kicker.getName() + "§f for §c§l" + reason, kicker);
	}
	
	public static boolean ifPlayerIsMuted(String p){
		Cooldown.checkCooldown(p, "Mute");
		if (Cooldown.hasCooldown(p, "Mute")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean ifPlayerIsMuted(Player p){
		Cooldown.checkCooldown(p, "Mute");
		if (Cooldown.hasCooldown(p, "Mute")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean ifPlayerIsTempbanned(String p){
		Cooldown.checkCooldown(p, "Tempban");
		if (Cooldown.hasCooldown(p, "Tempban")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean ifPlayerIsBanned(String p){
		if (Files.getDataFile().getStringList("Banned").contains(p)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean onlinePlayer(String name){
		boolean returntype = false;
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getName().equalsIgnoreCase(name)){
				returntype = true;
				break;
			}
		}
		return returntype;
	}
	
	public static void addToLog(String log){
		List<String> loglist = Files.getLogFile().getStringList("Log");
		loglist.add(0, log);
		Files.getLogFile().set("Log", loglist);
		Files.saveLogFile();
	}
	
	public static void addBetterLog(String uuidp, String type, String punisher, String date, int Level, String time, String reason){
		int id = 1;
		if (Files.getDataFile().contains("Log." + uuidp + ".idutil")){
			id = Files.getDataFile().getInt("Log." + uuidp + ".idutil") + 1; 
		}
		Files.getDataFile().set("Log." + uuidp + ".idutil", id);
		if (Level > 0){
			int previous = 0;
			if (Files.getDataFile().contains("Log." + uuidp + "." + type + ".level" + Level)){
				previous = Files.getDataFile().getInt("Log." + uuidp + "." + type + ".level" + Level);
			}
			Files.getDataFile().set("Log." + uuidp + "." + type + ".level" + Level, previous + 1);
		}
		Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Type", type);
		Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Punisher", punisher);
		Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Date", date);
		if (!type.equals("Warn")){
			Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Level", Level);
			Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Length", time);
		}
		Files.getDataFile().set("Log." + uuidp + ".ID" + id + ".Reason", reason);
		Files.saveDataFile();
	}
	
	public static boolean hasJoined(String p){
		Set<String> PrisonMain = org.Prison.Main.Files.getDataFile().getConfigurationSection("Players").getKeys(false);
		for (String s : PrisonMain){
			if (s.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")){
				if (org.Prison.Main.Files.getDataFile().contains("Players." + s + ".Name")){
					if (org.Prison.Main.Files.getDataFile().getString("Players." + s + ".Name").equals(p)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void sendRightPlayers(String message, Player non){
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getName() != non.getName() && p.hasPermission("Punish.Notify")){
				p.sendMessage("§c§lPunish §7§l> " + message);
			}
		}
	}
	
	public static boolean canBePunished(String p){
		List<String> players = new ArrayList<String>();
		if (players.contains(p)){
			return false;
		}else{
			return true;
		}
	}
}
