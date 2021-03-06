package org.Prison.Punish;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Files files = new Files(this);
	Events events = new Events(this);
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(events, this);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String Label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			
			if(Label.equalsIgnoreCase("punish")){
				if (p.hasPermission("Punish.Punish")){
					if (args.length < 2){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/punish (player) (reason)");
					}else{
						String uuid = "";
						String name = args[0];
						if (Bukkit.getPlayer(name) != null){
							uuid = Bukkit.getPlayer(name).getUniqueId().toString();
						}else{
							if (PunishAPI.hasJoined(name)){
								try{
									uuid = UUIDFetcher.getUUIDOf(name).toString();
								}catch(Exception e){
									p.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
									return false;
								}
								String reason = "";
								for (int i = 1; i < args.length; i++){
									if (i == args.length - 1){
										reason += args[i];	
									}else{
									reason += (args[i] + " ");
									}
								}
								Menu.openInventory1(uuid, name, p, reason);
							}else{
								p.sendMessage(ChatColor.RED + "Player has never joined.");
							}
						}
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}			
			if(Label.equalsIgnoreCase("historyp")){
				if (p.hasPermission("Punish.history")){
					if (args.length < 1 || args.length > 1){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/historyp (player)");
					}else{
						String uuid = "";
						String name = args[0];
						if (Bukkit.getPlayer(name) != null){
							uuid = Bukkit.getPlayer(name).getUniqueId().toString();
						}else{
							if (PunishAPI.hasJoined(name)){
								try{
									uuid = UUIDFetcher.getUUIDOf(name).toString();
								}catch(Exception e){
									p.sendMessage(ChatColor.RED + "The username you entered is not a minecraft account.");
									return false;
								}
								Menu.openInventory(uuid, name, p);
							}else{
								p.sendMessage(ChatColor.RED + "Player has never joined.");
							}
						}
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			if (Label.equalsIgnoreCase("mute")){
				if (p.hasPermission("Punish.Mute")){
					if (args.length < 3){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/mute (player) (minutes) (reason)");
					}else{
						int minutes = 0;
						try{
							minutes = Integer.parseInt(args[1]);
						}catch (Exception e){
							p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/mute (player) (minutes) (reason)");
							return true;
						}
						String reason = "";
						for (int i = 2; i < args.length; i++){
							if (i == args.length - 1){
								reason += args[i];	
							}else{
							reason += (args[i] + " ");
							}
						}
						PunishAPI.attemptMute(args[0], p, minutes, reason);
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			
			if (Label.equalsIgnoreCase("chatp")){
				if (p.hasPermission("Punish.chat")){
					if (args.length < 3){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/chatp (player) (level) (reason)");
					}else{
						int level = 0;
						try{
							level = Integer.parseInt(args[1]);
						}catch (Exception e){
							p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/chatp (player) (level) (reason)");
							return true;
						}
						String reason = "";
						for (int i = 2; i < args.length; i++){
							if (i == args.length - 1){
								reason += args[i];	
							}else{
							reason += (args[i] + " ");
							}
						}
						PunishAPI.attemptMuteLevel(args[0], p, level, reason);
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			
			if (Label.equalsIgnoreCase("genp")){
				if (p.hasPermission("Punish.gen")){
					if (args.length < 3){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/genp (player) (level) (reason)");
					}else{
						int level = 0;
						try{
							level = Integer.parseInt(args[1]);
						}catch (Exception e){
							p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/genp (player) (level) (reason)");
							return true;
						}
						String reason = "";
						for (int i = 2; i < args.length; i++){
							if (i == args.length - 1){
								reason += args[i];	
							}else{
							reason += (args[i] + " ");
							}
						}
						PunishAPI.attemptGenBan(args[0], p, level, reason);
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			
			if (Label.equalsIgnoreCase("hackp")){
				if (p.hasPermission("Punish.hack")){
					if (args.length < 3){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/hackp (player) (level) (reason)");
					}else{
						int level = 0;
						try{
							level = Integer.parseInt(args[1]);
						}catch (Exception e){
							p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/hackp (player) (level) (reason)");
							return true;
						}
						String reason = "";
						for (int i = 2; i < args.length; i++){
							if (i == args.length - 1){
								reason += args[i];	
							}else{
							reason += (args[i] + " ");
							}
						}
						PunishAPI.attemptHackBan(args[0], p, level, reason);
					}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			
			if (Label.equalsIgnoreCase("tempban")){
				if (p.hasPermission("Punish.Tempban")){
				if (args.length > 2){
					int minutes = 0;
					try{
						minutes = Integer.parseInt(args[1]);
					}catch (Exception e){
						p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/tempban (player) (minutes) (reason)");
						return true;
					}
					String reason = "";
					for (int i = 2; i < args.length; i++){
						if (i == args.length - 1){
							reason += args[i];	
						}else{
						reason += (args[i] + " ");
						}
					}
					PunishAPI.attemptTempBan(args[0], p, minutes, reason);
				}else{
					p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/tempban (player) (minutes) (reason)");
				}
				}else{
					p.sendMessage(ChatColor.RED + "No permission.");
				}
			}
			
		if (Label.equalsIgnoreCase("ban")){
			if (p.hasPermission("Punish.Ban")){
			if (args.length <= 1){
				p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/ban (player) (reason)");
			}else{
				String reason = "";
				for (int i = 1; i < args.length; i++){
					if (i == args.length - 1){
						reason += args[i];	
					}else{
					reason += (args[i] + " ");
					}
				}
				PunishAPI.attemptBan(args[0], p, reason);
			}
			}else{
				p.sendMessage(ChatColor.RED + "No permission.");
			}
		}
		
		if (Label.equalsIgnoreCase("kick")){
			if (p.hasPermission("Punish.Kick")){
			if (args.length <= 1){
				p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/kick (player) (reason)");
			}else{
				String reason = "";
				for (int i = 1; i < args.length; i++){
					if (i == args.length - 1){
						reason += args[i];	
					}else{
					reason += (args[i] + " ");
					}
				}
				PunishAPI.attemptKick(args[0], p, reason);
			}
			}else{
				p.sendMessage(ChatColor.RED + "No permission.");
			}
		}
		
		if (Label.equalsIgnoreCase("unban")){
			if (p.hasPermission("Punish.Unban")){
				if (args.length == 1){
					PunishAPI.attemptUnBan(args[0], p);
				}else{
					p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/unban (player)");
				}
			}else{
				p.sendMessage(ChatColor.RED + "No permission.");
			}
		}
		
		if (Label.equalsIgnoreCase("unmute")){
			if (p.hasPermission("Punish.Unmute")){
				if (args.length == 1){
					PunishAPI.attemptunMute(args[0], p);
				}else{
					p.sendMessage(ChatColor.RED + "Invalid Arguments: " + ChatColor.AQUA + "/unmnute (player)");
				}
			}else{
				p.sendMessage(ChatColor.RED + "No permission.");
			}
		}
		
		}
		return true;
	}
	
}
