package org.Prison.Punish;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;


@SuppressWarnings("deprecation")
public class Events implements Listener {
	public static Main plugin;
	public Events(Main instance){
		plugin = instance;
	}
	
	@EventHandler
	public void preLoging(PlayerPreLoginEvent event){
		String name = event.getName();
		if (PunishAPI.ifPlayerIsTempbanned(name)){
			event.disallow(Result.KICK_BANNED, "§c§lTempBan: §f" + PunishAPI.getReason(name, "Tempban") + " " + Cooldown.getTimeLeft(name, "Tempban"));
		}
		if (PunishAPI.ifPlayerIsBanned(name)){
			event.disallow(Result.KICK_BANNED, "§c§lBanned: §f" + PunishAPI.getReason(name, "Ban"));
		}
	}
}
