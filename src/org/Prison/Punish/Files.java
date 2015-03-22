package org.Prison.Punish;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Files {
	public static File f = new File("plugins/PrisonPunish/Data.yml");
	public static YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(f);
	public static File f1 = new File("plugins/PrisonPunish/Log.yml");
	public static YamlConfiguration logFile = YamlConfiguration.loadConfiguration(f1);
	public static Main plugin;
	public Files(Main instance){
		plugin = instance;
	}
	
	public static FileConfiguration config(){
		return plugin.getConfig();
	}
	
	public static void saveConfig(){
		plugin.saveConfig();
	}
	
	public static void reloadConfig(){
		plugin.reloadConfig();
	}
	
	public static YamlConfiguration getDataFile(){
		return dataFile;
	}
	public static YamlConfiguration getLogFile(){
		return logFile;
	}
	
	public static void saveDataFile(){
		try {
			getDataFile().save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveLogFile(){
		try {
			getLogFile().save(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
