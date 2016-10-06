package com.dsnyder.homesthatwork.commands;

import java.util.ArrayList;
import java.util.List;

import com.dsnyder.homesthatwork.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.dsnyder.homesthatwork.WorkingHomes;

public class HTWCommand extends GenericCommand {

	public HTWCommand() {
		super("homesthatwork", MessageManager.getMsg( "help-htw" ), "/htw [version | help [command]]", "homesthatwork.command.main");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list.add("help");
		list.add("version");
		
		if (arg3.length == 0) return list;
		
		if (arg3.length == 1) {
			if (arg3[0].equalsIgnoreCase("help")) {
				list = new ArrayList<>();
				for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
					list.add(cmd.getName());
				}
			} else {
				for (String cmd : list) {
					if (!cmd.toLowerCase().startsWith(arg3[1].toLowerCase())) {
						list.remove(cmd);
					}
				}
			}
			
			return list;
		} else if (arg3.length == 2 && arg3[1].equalsIgnoreCase("help")) {
			list = new ArrayList<>();
			for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
				list.add(cmd.getName());
			}
			
			for (String cmd : list) {
				if (!cmd.toLowerCase().startsWith(arg3[2].toLowerCase())) {
					list.remove(cmd);
				}
			}
		}
			
		return null;
	}

	@Override
	protected boolean execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			sender.sendMessage(getHelp());
		} else if (args[0].equalsIgnoreCase("help")) {
			
			String help = "";
			
			if (args.length == 1) {
				for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
					help += cmd.getHelp() + "\n\n";
				}
			} else if (args.length == 2) {
				for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
					if (cmd.getName().equalsIgnoreCase(args[1])) {
						sender.sendMessage(cmd.getHelp());
						return true;
					}
				}
				
				sender.sendMessage(ChatColor.RED + "Not a HTW Command.");
			} else {
				return false;
			}
			
			sender.sendMessage(help);
		} else if (args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("HomesThatWork Version " + WorkingHomes.CURRENT_VERSION);
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid subcommand.");
		}
		
		return true;
	}

}
