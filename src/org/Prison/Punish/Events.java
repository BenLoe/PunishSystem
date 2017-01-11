package org.Prison.Punish;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
			String reason = Files.getDataFile().getString("Currents." + uuid + ".tempreason");
			event.disallow(Result.KICK_BANNED, "§c§lYour access to this server has been suspended\n§7You may rejoin in §7§l" + Cooldown.getTimeLeftAlt(uuid, "Tempban") + "\n§f\n§7Reason: §f" + reason);
		}
		if (PunishAPI.ifPlayerIsBanned(uuid)){
			String reason = Files.getDataFile().getString("Currents." + uuid + ".banreason");
			event.disallow(Result.KICK_BANNED, "§c§lYour access to this server has been suspended permanently\n§f\n§7Reason: §f" + reason);
		}
	}
	
	@EventHandler
	public void login(final PlayerLoginEvent event){
		final String uuid = event.getPlayer().getUniqueId().toString();
		if (Files.getDataFile().contains("OfflineWarn." + uuid)){
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
				public void run(){
					event.getPlayer().sendMessage("");
					event.getPlayer().sendMessage("§4§l>> §cWhile offline a staff warned you for: §7" + Files.getDataFile().getString("OfflineWarn." + uuid));
					event.getPlayer().sendMessage("");
					Files.getDataFile().set("OfflineWarn." + uuid, null);
					Files.saveDataFile();
				}
			}, 40l);
		}
	}
	
	@EventHandler
	public void InventoryInteract(InventoryClickEvent event){
		if (event.getInventory().getName().contains("Punish History")){
			event.setCancelled(true);
		}
		if (MenuItem.inMenu.contains(event.getWhoClicked().getName())){
			event.setCancelled(true);
			if (MenuItem.wasAItem(event.getRawSlot())){
				MenuItem.getItemFromSlot(event.getRawSlot()).wasClicked((Player) event.getWhoClicked());
			}
		}
	}
	
	@EventHandler
	public void InventoryClose(InventoryCloseEvent event){
		if (MenuItem.inMenu.contains(event.getPlayer().getName())){
			MenuItem.inMenu.remove(event.getPlayer().getName());
		}
	}
}
