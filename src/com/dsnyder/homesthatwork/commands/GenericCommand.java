package com.dsnyder.homesthatwork.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.dsnyder.homesthatwork.HomeManager;
import com.dsnyder.homesthatwork.permissions.PermissionManager;

public abstract class GenericCommand implements TabExecutor {
	
	private String name;
	private String description;
	private String usage;
	private String permission;
	
	protected HomeManager homeManager;
	
	public GenericCommand(String name, String description, String usage, String permission) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.permission = permission;
		if (permission != null) {
			PermissionManager.getManager().registerPermission(permission);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public String getHelp() {
		return ChatColor.GOLD + "/" + getName() + ": " + ChatColor.WHITE + 
				getDescription() + "\n" + ChatColor.GOLD + "Usage: " + ChatColor.WHITE + getUsage();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) return false;
		
		homeManager = new HomeManager((OfflinePlayer) sender);
		
		if (permission == null || PermissionManager.getManager().hasPermission(sender, getPermission())) {
			return execute(sender, args);
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			return true;
		}
	}
	
	protected abstract boolean execute(CommandSender sender, String[] args);
}
