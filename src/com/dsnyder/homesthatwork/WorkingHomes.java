package com.dsnyder.homesthatwork;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorkingHomes extends JavaPlugin {
	
	public static final String VERSION_KEY		= "HTWVersion";
	public static final String CURRENT_VERSION	= "2.0.1";
	
	static JavaPlugin main;
	
	@Override
	public void onEnable() {
		main = this;
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
    public boolean onCommand(CommandSender sender, 
    		Command command, String label, String[] args) {

		if (!(sender instanceof Player) && !(sender instanceof CommandSender))
            return false;
		// create a new home manager for this player - currently no caching/saving of them in memory
		HomeManager mngr = new HomeManager((Player) sender);
		// decode command
        if (command.getName().equalsIgnoreCase("sethome")) {
            if (args.length != 1) 
            	return false;
            mngr.setHome(args[0]);
            return true;
        } else if (command.getName().equalsIgnoreCase("home")) {
            if (args.length != 1) 
            	return false;
            mngr.goHome(args[0]);      
            return true;
        } else if (command.getName().equalsIgnoreCase("delhome")) {
            if (args.length != 1) 
            	return false;
            mngr.delHome(args[0]);   
            return true;
        } else if (command.getName().equalsIgnoreCase("listhomes")) {
        	if (args.length == 0) 
        		mngr.listHomes();
        	else if (args.length == 1) 
        		if (Bukkit.getPlayer(args[0]) != null)
        			mngr.listHomes(Bukkit.getPlayer(args[0]));
        		else {
        			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
        				if (p.getName().equalsIgnoreCase(args[0])) {
        					sender.sendMessage(ChatColor.YELLOW + "Offline: Last known player named " + args[0]);
        					mngr.listHomes(p);
        					return true;
        				}
        			}
        			
        			sender.sendMessage(ChatColor.RED + "Player does not exist");
        		}
        	else
        		return false;
        	return true;
        } else if (command.getName().equalsIgnoreCase("goback")) {
        	// don't do args check - just go back.
        	mngr.goBack();
        	return true;
        } else if (command.getName().equalsIgnoreCase("htwversion")) {
        	sender.sendMessage("HomesThatWork Version " + CURRENT_VERSION);
        	return true;
        } else if (command.getName().equalsIgnoreCase("homeinfo")) {
        	if (args.length == 1) 
        		mngr.homeInfo(args[0]);
        	else if (args.length == 2)
        		if (Bukkit.getPlayer(args[0]) != null)
        			mngr.homeInfo(Bukkit.getPlayer(args[0]), args[1]);
        		else {
        			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
        				if (p.getName().equalsIgnoreCase(args[0])) {
        					sender.sendMessage(ChatColor.YELLOW + "Offline: Last known player named " + args[0]);
        					mngr.homeInfo(p, args[1]);
        					return true;
        				}
        			}
        			
        			sender.sendMessage(ChatColor.RED + "Player does not exist");
        		}
        	else
        		return false;
        	return true;
        }
        
        return false;
    }
}
