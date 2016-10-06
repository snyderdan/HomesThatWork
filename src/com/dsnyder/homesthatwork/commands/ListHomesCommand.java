package com.dsnyder.homesthatwork.commands;

import java.util.List;

import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.dsnyder.homesthatwork.permissions.PermissionManager;

public class ListHomesCommand extends GenericCommand {

	public ListHomesCommand() {
		super("listhomes", MessageManager.getMsg( "help-listhomes" ), "/listhomes [player]", "homesthatwork.home.list");
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
		if (args.length == 1 && PermissionManager.getManager().hasPermission(sender, "homesthatwork.others.list")) {
			if (Bukkit.getPlayer(args[0]) != null)
				// player is online
    			homeManager.listHomes(Bukkit.getPlayer(args[0]));
    		else {
    			// sort through offline players
    			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
    				if (p.getName().equalsIgnoreCase(args[0])) {
    					sender.sendMessage(ChatColor.YELLOW + "Offline: Last known player named " + args[0]);
    					homeManager.listHomes(p);
    					return true;
    				}
    			}
    			
    			sender.sendMessage(ChatColor.RED + "Player does not exist");
    		}
		} else if (args.length == 1) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to view other players' information.");
		} else if (args.length == 0) {
			homeManager.listHomes();
		} else {
			return false;
		} 
		
		return true;
	}
}
