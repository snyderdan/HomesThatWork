package com.dsnyder.homesthatwork.commands;

import java.util.List;

import com.dsnyder.homesthatwork.HomeManager;
import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetHomeCommand extends GenericCommand {

	public SetHomeCommand() {
		super("sethome", MessageManager.getMsg( "help-sethome" ), "/sethome [home_name]", "homesthatwork.home.set");
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
		if (args.length == 0) args = new String[] {HomeManager.DEFAULT_LOCATION};
		if (args.length != 1) return false;
		homeManager.setHome(args[0]);
		return true;
	}

}