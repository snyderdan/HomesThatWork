package com.dsnyder.homesthatwork.commands;

import java.util.Collections;
import java.util.List;

import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.dsnyder.homesthatwork.HomeManager;
import com.dsnyder.homesthatwork.permissions.PermissionManager;

public class HomeInfoCommand extends GenericCommand {

	public HomeInfoCommand() {
		super("homeinfo", MessageManager.getMsg( "help-homeinfo" ), "/homeinfo [player] <home_name>", "homesthatwork.home.info");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		initializeHomeManager(arg0);
		if (homeManager == null) return null;
		
		List<String> homes = homeManager.getHomeList();
		
		Collections.sort(homes);
		
		if (arg3.length == 0) return homes;
		
		if (PermissionManager.getManager().hasPermission(arg0, "homesthatwork.others.info")) {
			// any access to other player homes?
			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				if (p.getName().equalsIgnoreCase(arg3[0])) {
					if (!p.isOnline())
						arg0.sendMessage(ChatColor.YELLOW + "Offline: Last known player named " + arg3[0]);
					
					HomeManager other = new HomeManager(p);
					
					homes = other.getHomeList();
					Collections.sort(homes);
					
					if (arg3.length == 2) {
						for (String home : other.getHomeList()) {
							if (!home.toLowerCase().startsWith(arg3[1].toLowerCase())) {
								homes.remove(home);
							}
						}
					}
					return homes;
				}
			}
		}

		if (arg3.length == 1) {
			for (String home : homeManager.getHomeList()) {
				if (!home.toLowerCase().startsWith(arg3[0].toLowerCase())) {
					homes.remove(home);
				}
			}
			
			if (homes.isEmpty()) return null;

		} else {
			return null;
		}
		
		return homes;
	}
	
	@Override
	protected boolean execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 1) {
			// viewing personal home info
			homeManager.homeInfo(args[0]);
			
		} else  if ((args.length == 2) && 
				PermissionManager.getManager().hasPermission(sender, "homesthatwork.others.info")) {
			// viewing another players home info w/permission
			if (Bukkit.getPlayer(args[0]) != null)
    			homeManager.homeInfo(Bukkit.getPlayer(args[0]), args[1]);
    		else {
    			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
    				if (p.getName().equalsIgnoreCase(args[0])) {
    					if (!p.isOnline())
    						sender.sendMessage(ChatColor.YELLOW + "Offline: Last known player named " + args[0]);
    					homeManager.homeInfo(p, args[1]);
    					return true;
    				}
    			}
    			
    			sender.sendMessage(ChatColor.RED + "Player does not exist");
    		}
		} else if (args.length == 2) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to view other players' information.");
		} else {
			// invalid number of args
			return false;
		}
		return true;
	}
}
