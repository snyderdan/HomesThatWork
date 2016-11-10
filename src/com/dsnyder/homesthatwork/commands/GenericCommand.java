package com.dsnyder.homesthatwork.commands;

import com.dsnyder.homesthatwork.MessageManager;
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
		return ChatColor.YELLOW + "/" + getName() + ": " + ChatColor.GRAY +
				getDescription() + "\n" + ChatColor.YELLOW + "Usage: " + ChatColor.GRAY + getUsage();
	}
	
	public void initializeHomeManager(CommandSender sender) {
		
		homeManager = null;
		
		if (!(sender instanceof Player)) return;
		
		homeManager = new HomeManager((OfflinePlayer) sender);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		initializeHomeManager(sender);
		
		if (homeManager == null) return false;
		
		if (permission == null || PermissionManager.getManager().hasPermission(sender, getPermission())) {
			return execute(sender, args);
		} else {
			sender.sendMessage(MessageManager.getErrorMsg("no-command-perm"));
			return true;
		}
	}
	
	protected abstract boolean execute(CommandSender sender, String[] args);
}
