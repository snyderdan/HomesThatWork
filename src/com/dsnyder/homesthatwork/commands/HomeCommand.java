package com.dsnyder.homesthatwork.commands;

import java.util.Collections;
import java.util.List;

import com.dsnyder.homesthatwork.HomeManager;
import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HomeCommand extends GenericCommand {

	public HomeCommand() {
		super("home", MessageManager.getMsg( "help-home" ), "/home [home_name]", "homesthatwork.home.use");
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		initializeHomeManager(arg0);
		if (homeManager == null) return null;
		
		List<String> homes = homeManager.getHomeList();
		
		Collections.sort(homes);
		
		if (arg3.length == 0) return homes;

		if (arg3.length == 1) {
			for (String home : homeManager.getHomeList()) {
				if (!home.toLowerCase().startsWith(arg3[0].toLowerCase())) {
					homes.remove(home);
				}
			}
			
			return homes;
		} else {
			return null;
		}
	}
	
	@Override
	protected boolean execute(CommandSender sender, String[] args) {
		if (args.length == 0) args = new String[] {HomeManager.DEFAULT_LOCATION};
		if (args.length != 1) return false;
		homeManager.goHome(args[0]);
		return true;
	}

}
