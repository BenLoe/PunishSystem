package org.Prison.Punish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;

public class Cooldown {

		public static boolean hasCooldown(String p, String type){
			if(Files.getDataFile().contains("Currents." + p + "." + type)){
				if (!Files.getDataFile().getString("Currents." + p + "." + type).equalsIgnoreCase("None")){
					return true;
				}
				return false;
			}else{
				return false;
			}
		}
		public static boolean hasCooldown(Player p, String type){
			if(Files.getDataFile().contains("Currents." + p.getUniqueId().toString() + "." + type)){
				if (!Files.getDataFile().getString("Currents." + p.getUniqueId().toString() + "." + type).equalsIgnoreCase("None")){
					return true;
				}
				return false;
			}else{
				return false;
			}
		}
		
		public static void setCooldown(String p, int minutes, String type){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = new Date();
			c.setTime(d1);
			c.add(Calendar.MINUTE, minutes);
			c.add(Calendar.SECOND, 1);
			String newtime = df.format(c.getTime().getTime());
			Files.getDataFile().set("Currents." + p + "." + type, newtime);
			Files.saveDataFile();
		}
		
		public static void setCooldown1(String p, int minutes, String type){
			Calendar c = Calendar.getInstance();
			int currentday = c.get(Calendar.DAY_OF_YEAR);
			int currenthours = c.get(Calendar.HOUR_OF_DAY);
			int currentminutes = c.get(Calendar.MINUTE);
			
			int newday = 0 + currentday;
			int newhours = 0 + currenthours;
			int newminutes = minutes + currentminutes;
			
			for (int i = 1; i < 100; i++){
				if (newminutes >= 60){
					newminutes = newminutes - 60;
					newhours = newhours + 1;
				}else{
					break;
				}
			}
			for (int i = 1; i < 100; i++){
				if (newhours >= 24){
					newhours = newhours - 24;
					newday = newday + 1;
				}else{
					break;
				}
			}
			for (int i = 1; i < 100; i++){
				if (newday >= 364){
					newday = newday - 364;
				}else{
					break;
				}
			}
			Files.getDataFile().set("Players." + p + ".Cooldowns." + type + ".Day", newday);
			Files.getDataFile().set("Players." + p + ".Cooldowns." + type + ".Hour", newhours);
			Files.getDataFile().set("Players." + p + ".Cooldowns." + type + ".Minute", newminutes);
			Files.getDataFile().set("Players." + p + ".Cooldowns." + type + ".Second", c.get(Calendar.SECOND));
			Files.saveDataFile();
		}
		
		public static String getTimeLeft(String p, String type){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date current = new Date();
			String timestamp = Files.getDataFile().getString("Currents." + p + "." + type);
			try {
				Date punishend = df.parse(timestamp);
				long diff = punishend.getTime() - current.getTime();
	
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				return "§c " + diffDays + "D " + diffHours + "H " + diffMinutes + "M " + diffSeconds + "S";
			} catch (ParseException e) {
				return "Error";
			}
		}
		
		public static String getTimeLeft1(String p, String type){
			Calendar c = Calendar.getInstance();
			String uuid = p;
			int day = Files.getDataFile().getInt("Players." + uuid + ".Cooldowns." + type + ".Day");
			int hours = Files.getDataFile().getInt("Players." + uuid + ".Cooldowns." + type + ".Hour");
			int minutes = Files.getDataFile().getInt("Players." + uuid + ".Cooldowns." + type + ".Minute");
			int seconds = Files.getDataFile().getInt("Players." + uuid + ".Cooldowns." + type + ".Second");
			String dateStart = c.get(Calendar.DAY_OF_YEAR) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			String dateStop = day + "/" + c.get(Calendar.YEAR) + " " + hours + ":" + minutes + ":" + seconds;
	 
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("dd/yyyy HH:mm:ss");
	 
			Date d1 = null;
			Date d2 = null;
	 
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);
	 
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				
				
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				return "§c " + diffDays + "D " + diffHours + "H " + diffMinutes + "M " + diffSeconds + "S";
	 
			} catch (Exception e) {
				e.printStackTrace();
				return "BROKEN";
			}
		}
		
		public static boolean checkCooldown(String p, String type){
			if (hasCooldown(p, type)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date current = new Date();
			String timestamp = Files.getDataFile().getString("Currents." + p + "." + type);
			try {
				Date punishend = df.parse(timestamp);
				long diff = punishend.getTime() - current.getTime();
				
				if (diff <= 0){
					Files.getDataFile().set("Currents." + p + "." + type, "None");
					Files.saveDataFile();
					return true;
				}else{
					return false;
				}
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
			return false;
		}
		
		public static boolean checkCooldown(Player p, String type){
			if (hasCooldown(p, type)){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date current = new Date();
			String timestamp = Files.getDataFile().getString("Currents." + p.getUniqueId().toString() + "." + type);
			try {
				Date punishend = df.parse(timestamp);
				long diff = punishend.getTime() - current.getTime();
	
				if (diff <= 0){
					Files.getDataFile().set("Currents." + p.getUniqueId().toString() + "." + type, "None");
					String uuid = p.getUniqueId().toString();
					if (!PunishAPI.ifPlayerIsBanned(uuid) && !PunishAPI.ifPlayerIsMuted(p) && !PunishAPI.ifPlayerIsTempbanned(uuid)){
						Files.getDataFile().set("Currents." + uuid , null);
					}
					Files.saveDataFile();
					return true;
				}else{
					return false;
				}
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
			return false;
		}
		
		public static int getMinutes(String type, String p){
			if (hasCooldown(p, type)){
				Calendar c = Calendar.getInstance();
				int day = Files.getDataFile().getInt("Players." + p + ".Cooldowns." + type + ".Day");
				int hours = Files.getDataFile().getInt("Players." + p + ".Cooldowns." + type + ".Hour");
				int minutes = Files.getDataFile().getInt("Players." + p + ".Cooldowns." + type + ".Minute");
				int seconds = Files.getDataFile().getInt("Players." + p + ".Cooldowns." + type + ".Second");
				String dateStart = c.get(Calendar.DAY_OF_YEAR) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
				String dateStop = day + "/" + c.get(Calendar.YEAR) + " " + hours + ":" + minutes + ":" + seconds;
		 
				//HH converts hour in 24 hours format (0-23), day calculation
				SimpleDateFormat format = new SimpleDateFormat("dd/yyyy HH:mm:ss");
		 
				Date d1 = null;
				Date d2 = null;
		 
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
		 
					//in milliseconds
					long diff = d2.getTime() - d1.getTime();
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					return (int) ((int) (diffMinutes * 60) + diffSeconds);
				}catch(Exception e){
					return 0;
				}
			}
			return 0;
		}
		
		public static void resetCooldown(String type, String p){
			Files.getDataFile().set("Currents." + p + "." + type, "None");
			if (!PunishAPI.ifPlayerIsBanned(p) && !PunishAPI.ifPlayerIsMuted(p) && !PunishAPI.ifPlayerIsTempbanned(p)){
				Files.getDataFile().set("Currents." + p , null);
			}
			Files.saveDataFile();
		}
		
		public static String getTimeLeftAlt(String p, String type){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date current = new Date();
			String timestamp = Files.getDataFile().getString("Currents." + p + "." + type);
			try {
				Date punishend = df.parse(timestamp);
				long diff = punishend.getTime() - current.getTime();
	
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				int over0 = 0;
				if (diffSeconds > 0) over0++;
				if (diffMinutes > 0) over0++;
				if (diffHours > 0) over0++;
				if (diffDays > 0) over0++;
				
				if (over0 == 4) return diffDays + "D " + diffHours + "H " + diffMinutes + "M " + diffSeconds + "S";
				if (over0 == 3){
					String returns = "";
					if (diffDays > 0) returns += diffDays + "D ";
					if (diffHours > 0) returns += diffHours + "H ";
					if (diffMinutes > 0) returns += diffMinutes + "M ";
					if (diffSeconds > 0) returns += diffSeconds + "S ";
					returns.substring(0, returns.length() - 1);
					return returns;
				}
				if (over0 == 2){
					String returns = "";
					if (diffDays > 0) returns += diffDays + " Days ";
					if (diffHours > 0) returns += diffHours + " Hours ";
					if (diffMinutes > 0) returns += diffMinutes + " Mins ";
					if (diffSeconds > 0) returns += diffSeconds + " Secs ";
					returns.substring(0, returns.length() - 1);
					return returns;
				}
				if (over0 == 1){
					String returns = "";
					if (diffDays > 0) returns += diffDays + " Days";
					if (diffHours > 0) returns += diffHours + " Hours";
					if (diffMinutes > 0) returns += diffMinutes + " Mins";
					if (diffSeconds > 0) returns += diffSeconds + " Secs";
					returns.substring(0, returns.length() - 1);
					return returns;
				}
				return "BROKEN";
			} catch (ParseException e) {
				return "Error";
			}
		}
}
