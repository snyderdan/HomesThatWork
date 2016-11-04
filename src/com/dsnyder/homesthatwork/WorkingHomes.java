package com.dsnyder.homesthatwork;

import org.bukkit.plugin.java.JavaPlugin;

import com.dsnyder.homesthatwork.commands.*;
import com.dsnyder.homesthatwork.permissions.PermissionManager;

import java.io.File;

public class WorkingHomes extends JavaPlugin {
	
	public static final String VERSION_KEY		= "HTWVersion";
	public static final String CURRENT_VERSION	= "2.3.0";
	
	private static JavaPlugin main;
	
	@Override
	public void onEnable() {
		main = this;
		new FileManager();
		new MessageManager();
		new PermissionManager();
		new CommandManager();
	}


	public String getStringConfig()
	{
		return WorkingHomes.getPlugin().getDataFolder().getPath() + File.separator + "config.yml";
	}

	@Override
	public void onDisable() {
	}

	public static JavaPlugin getPlugin() {
		return main;
	}


}
