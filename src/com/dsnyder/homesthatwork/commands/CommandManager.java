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
		registerCommand(new HomeCommand());
		registerCommand(new DelHomeCommand());
		registerCommand(new SetHomeCommand());
		registerCommand(new ListHomesCommand());
		registerCommand(new HomeInfoCommand());
		registerCommand(new GoBackCommand());
		registerCommand(new ReloadCommand());
		registerCommand(new HTWCommand());


	}
	
	public void registerCommand(GenericCommand cmd) {
		JavaPlugin plugin = WorkingHomes.getPlugin();
		plugin.getCommand(cmd.getName()).setExecutor(cmd);
		commands.add(cmd);
	}
	
	public List<GenericCommand> getCommands() {
		return commands;
	}
	
	public static CommandManager getManager() {
		return manager;
	}

}
