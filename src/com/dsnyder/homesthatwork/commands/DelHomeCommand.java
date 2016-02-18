package com.dsnyder.homesthatwork.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DelHomeCommand extends GenericCommand {

	public DelHomeCommand() {
		super("delhome", "Deletes an existing home from record", "/delhome <home_name>", "homesthatwork.home.del");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		List<String> homes = homeManager.getHomeList();
		
		Collections.sort(homes);
		
		if (arg3.length == 0) return homes;

		if (arg3.length == 1) {
			for (String home : homes) {
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
		// TODO Auto-generated method stub
		if (args.length != 1) return false;
		homeManager.delHome(args[0]);
		return true;
	}

}
