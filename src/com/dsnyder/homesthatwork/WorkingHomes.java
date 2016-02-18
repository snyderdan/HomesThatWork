package com.dsnyder.homesthatwork;

import org.bukkit.plugin.java.JavaPlugin;

import com.dsnyder.homesthatwork.commands.*;

public class WorkingHomes extends JavaPlugin {
	
	public static final String VERSION_KEY		= "HTWVersion";
	public static final String CURRENT_VERSION	= "2.1.0";
	
	private static JavaPlugin main;
	
	@Override
	public void onEnable() {
		main = this;
		new CommandManager();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static JavaPlugin getPlugin() {
		return main;
	}
}
