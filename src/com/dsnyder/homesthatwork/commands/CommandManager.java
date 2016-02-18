package com.dsnyder.homesthatwork.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.dsnyder.homesthatwork.WorkingHomes;

public class CommandManager {
	
	private List<GenericCommand> commands;
	private static CommandManager manager;

	public CommandManager() {
		// TODO Auto-generated constructor stub
		commands = new ArrayList<>();
		manager = this;
		JavaPlugin plugin = WorkingHomes.getPlugin();
		plugin.getCommand("home").setExecutor(new HomeCommand());
		plugin.getCommand("delhome").setExecutor(new DelHomeCommand());
		plugin.getCommand("sethome").setExecutor(new SetHomeCommand());
		plugin.getCommand("listhome").setExecutor(new ListHomesCommand());
		plugin.getCommand("homeinfo").setExecutor(new HomeInfoCommand());
		plugin.getCommand("goback").setExecutor(new GoBackCommand());
		plugin.getCommand("homesthatwork").setExecutor(new HTWCommand());
	}
	
	public void registerCommand(GenericCommand cmd) {
		commands.add(cmd);
	}
	
	public List<GenericCommand> getCommands() {
		return commands;
	}
	
	public static CommandManager getManager() {
		return manager;
	}

}
