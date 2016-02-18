package com.dsnyder.homesthatwork.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.dsnyder.homesthatwork.permissions.PermissionManager;

public class HomeInfoCommand extends GenericCommand {

	public HomeInfoCommand() {
		super("homeinfo", "Lists information about a specified home", "/homeinfo [player] <home_name>", "homesthatwork.home.info");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
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
