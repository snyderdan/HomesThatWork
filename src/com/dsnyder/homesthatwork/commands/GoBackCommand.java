package com.dsnyder.homesthatwork.commands;

import java.util.List;

import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GoBackCommand extends GenericCommand {

	public GoBackCommand() {
		super("goback", MessageManager.getMsg( "help-goback" ), "/goback", "homesthatwork.goback");
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
		if (args.length != 0) return false;
		homeManager.goBack();
		return true;
	}

}
