package org.Prison.Punish;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;


@SuppressWarnings("deprecation")
public class Events implements Listener {
	public static Main plugin;
	public static HashMap<String,String> cached = new HashMap<>();
	public Events(Main instance){
		plugin = instance;
	}
	
	@EventHandler
	public void preLoging(PlayerPreLoginEvent event){
		String name = event.getName();
		String uuid = "";
		if (cached.containsKey(name)){
			uuid = cached.get(name);
		}else{
			uuid = UUIDFetcher.getUUIDOf(name).toString();
			cached.put(name, uuid);
		}
		if (PunishAPI.ifPlayerIsTempbanned(uuid)){
			String reason = Files.getDataFile().getString("Players." + uuid + ".Tempban");
			event.disallow(Result.KICK_BANNED, "§c§lBanned for" + Cooldown.getTimeLeft(uuid, "Tempban") + "\n§f" + reason);
		}
		if (PunishAPI.ifPlayerIsBanned(uuid)){
			String reason = Files.getDataFile().getString("Players." + uuid + ".BanReason");
			event.disallow(Result.KICK_BANNED, "§c§lBanned permanently:\n§f" + reason);
		}
	}
}
