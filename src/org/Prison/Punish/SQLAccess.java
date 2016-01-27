package org.Prison.Punish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class SQLAccess {

	public static Connection connection;
	
	public void onDisable(){
		try {
			if (connection != null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
		}
	}
	
	public synchronized static void openConnection(){
		try{
			connection = DriverManager.getConnection("jdbc:mysql://****/****", "***", "****");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized static void closeConnection(){
		try{
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public synchronized static void addToLog(String name, String uuid, Player punisher, String type, String time, boolean isPerm, String reason){
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("INSERT INTO `PunishLog` values(NULL,?,?,?,?,?,NULL,?,?);");
			sql.setString(1, name);
			sql.setString(2, uuid);
			sql.setString(3, type);
			sql.setString(4, punisher.getName());
			sql.setString(5, time);
			if (isPerm){
			sql.setString(6, "True");
			}else{
			sql.setString(6, "False");
			}
			sql.setString(7, reason);
			sql.executeUpdate();
			sql.close();
			closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	
}
