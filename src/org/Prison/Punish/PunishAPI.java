package org.Prison.Punish;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishAPI {
	
	public static Calendar c = Calendar.getInstance();
	public static void attemptMute(String p, Player muter, int minutes){
		if (!canBePunished(p)){
			muter.sendMessage(ChatColor.RED + "Player cannot be muted.");
			return;
		}
		if (p == muter.getName()){
			muter.sendMessage(ChatColor.RED + "You wanna mute yourself? Nice try.");
			return;
		}
		if (ifPlayerIsMuted(p)){
			sendRightPlayers("§c§l" + p + "§f muted by §c§l" + muter.getName() + "§f for §c§l" + minutes + "§f minutes.", muter);
			muter.sendMessage("§c§l" + p + "§f was already muted, so his mute time was reset to §c§l" + minutes + "§f minutes.");
			Cooldown.setCooldown(p, minutes, "Mute");
			addToLog("Mute: " + p + ", No reason, Time:" + minutes + ", Issued By: " + muter.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).sendMessage("§cYou have been muted by §4" + muter.getName() + " §cfor §4" + minutes + " §cminutes.");
			}
		}else{
			Stats.addMute(p);
			sendRightPlayers("§c§l" + p + "§f muted by §c§l" + muter.getName() + "§f for §c§l" + minutes + "§f minutes.", muter);
			muter.sendMessage("§c§l" + p + "§f has been muted for §c§l" + minutes + "§f minutes.");
			Cooldown.setCooldown(p, minutes, "Mute");
			addToLog("Mute: " + p + ", No reason, Time:" + minutes + ", Issued By: " + muter.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).sendMessage("§cYou have been muted by §4" + muter.getName() + " §cfor §4" + minutes + " §cminutes.");
			}
		}
	}
	
	public static void attemptTempBan(String p, Player banner, int minutes, String reason){
		if (!canBePunished(p)){
			banner.sendMessage(ChatColor.RED + "Player cannot be temp banned.");
			return;
		}
		if (p == banner.getName()){
			banner.sendMessage(ChatColor.RED + "You wanna temp ban yourself? Nice try.");
			return;
		}
		if (ifPlayerIsTempbanned(p)){
			sendRightPlayers("§c§l" + p + "§f tempbanned by §c§l" + banner.getName() + "§f for §c§l" + minutes + "§f minutes, reason: §c§l" + reason, banner);
			banner.sendMessage("§c§l" + p + "§f was already temp banned, so his temp ban time was reset to §c§l" + minutes + "§f minutes.");
			Cooldown.setCooldown(p, minutes, "Tempban");
			Files.getDataFile().set("Reasons." + p + ".Tempban", reason);
			Files.saveDataFile();
			addToLog("Tempban: " + p + ", " + reason + ", Time:" + minutes + ", Issued By: " + banner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
		}else{
			Stats.addTemp(p);
			sendRightPlayers("§c§l" + p + "§f tempbanned by §c§l" + banner.getName() + "§f for §c§l" + minutes + "§f minutes, reason: §c§l" + reason, banner);
			banner.sendMessage("§c§l" + p + "§f has been temp banned for §c§l" + minutes + "§f minutes.");
			Cooldown.setCooldown(p, minutes, "Tempban");
			Files.getDataFile().set("Reasons." + p + ".Tempban", reason);
			Files.saveDataFile();
			addToLog("Tempban: " + p + ", " + reason + ", Time:" + minutes + ", Issued By: " + banner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lTempBan: §f" + reason + " " + Cooldown.getTimeLeft(p, "Tempban"));
			}
		}
	}
	
	public static void attemptunMute(String p, Player unmuter){
		if (ifPlayerIsMuted(p)){
			sendRightPlayers("§c§l" + p + "§f unmuted by §c§l" + unmuter.getName(), unmuter);
			unmuter.sendMessage("§c§l" + p + "§f was unmuted.");
			addToLog("Unmute: " + p + ", No reason, Time:Immediately, Issued By: " + unmuter.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			Cooldown.resetCooldown("Mute", p);
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).sendMessage("§aYou have been unmuted.");
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
		if (p == banner.getName()){
			banner.sendMessage(ChatColor.RED + "You wanna ban yourself? Nice try.");
			return;
		}
		if (ifPlayerIsBanned(p)){
			banner.sendMessage("§cAlready banned.");
		}else{
			Stats.addBan(p);
			sendRightPlayers("§c§l" + p + "§f banned by §c§l" + banner.getName() + "§f for §c§l" + reason, banner);
			banner.sendMessage("§c§l" + p + "§f is now banned.");
			List<String> banned = Files.getDataFile().getStringList("Banned");
			banned.add(p);
			addToLog("Ban: " + p + ", " + reason + ", Time:Permanent, Issued By: " + banner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			Files.getDataFile().set("Banned", banned);
			Files.getDataFile().set("Reasons." + p + ".Ban", reason);
			Files.saveDataFile();
			if (onlinePlayer(p)){
				Bukkit.getPlayer(p).kickPlayer("§c§lBanned: " + reason);
			}
		}
	}
	
	public static void attemptUnBan(String p, Player unbanner){
		if (ifPlayerIsBanned(p) || ifPlayerIsTempbanned(p)){
			unbanner.sendMessage("§c§l" + p + "§f has been unbanned.");
			sendRightPlayers("§c§l" + p + "§f unbanned by §c§l" + unbanner.getName(), unbanner);
			if (ifPlayerIsBanned(p)){
				addToLog("Unban: " + p + ", No reason, Time:Immediately, Issued By: " + unbanner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
				List<String> banned = Files.getDataFile().getStringList("Banned");
				banned.remove(p);
				Files.getDataFile().set("Banned", banned);
				Files.saveDataFile();
			}else{
				addToLog("Untempban: " + p + ", No reason, Time:Immediately, Issued By: " + unbanner.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
				Cooldown.resetCooldown("Tempban", p);
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
			return;
		}
		if (onlinePlayer(p)){
			Stats.addKick(p);
			kicker.sendMessage("§c§l" + p + "§f has been kicked.");
			Bukkit.getPlayer(p).kickPlayer("§c§lKick: " + reason);
			addToLog("Kick: " + p + ", " + reason + ", Time:Immediately, Issued By: " + kicker.getName() + ", " + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
			sendRightPlayers("§c§l" + p + "§f muted by §c§l" + kicker.getName() + "§f for §c§l" + reason, kicker);
		}else{
			kicker.sendMessage("§cPlayer not online");
		}
	}
	
	public static boolean ifPlayerIsMuted(String p){
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
	
	public static String getReason(String p, String type){
		String reason = Files.getDataFile().getString("Reasons." + p + "." + type);
		return reason;
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
	
	public static void sendRightPlayers(String message, Player non){
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getName() != non.getName() && p.hasPermission("Punish.Notify")){
				p.sendMessage("§c§lPunish §7§l> " + message);
			}
		}
	}
	
	public static boolean canBePunished(String p){
		List<String> players = new ArrayList<String>();
		players.add("Ben_Loe");
		players.add("Nicor");
		players.add("Joshur");
		if (players.contains(p)){
			return false;
		}else{
			return true;
		}
	}
}
