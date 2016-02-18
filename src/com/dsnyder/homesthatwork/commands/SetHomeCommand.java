package com.dsnyder.homesthatwork.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetHomeCommand extends GenericCommand {

	public SetHomeCommand() {
		super("sethome", "Records current location as a home", "/sethome <home_name>", "homesthatwork.home.set");
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
		return false;
	}

}