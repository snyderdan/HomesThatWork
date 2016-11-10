package com.dsnyder.homesthatwork.commands;

import java.util.ArrayList;
import java.util.List;

import com.dsnyder.homesthatwork.MessageManager;
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

		if (arg3.length == 0) return null; // shouldn't happen?
		else if (arg3.length == 1) return list;
		else if (arg3.length == 2) {
			if (arg3[0].equalsIgnoreCase("help")) {
				// if we have the full help command
				list = new ArrayList<>();
				for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
					list.add(cmd.getName());
				}
				return list;
			}
			// if not, then try to complete if possible
			for (String n : list) {
				if (!n.toLowerCase().startsWith(arg3[1])) {
					list.remove(n);
				}
			}
			return list;
		} else if (arg3.length == 3 && arg3[0].equalsIgnoreCase("help")) {
			// if we are in the help command, try to complete
			list = new ArrayList<>();
			for (GenericCommand cmd : CommandManager.getManager().getCommands()) {
				list.add(cmd.getName());
			}
			for (String n : list) {
				if (!n.toLowerCase().startsWith(arg3[1])) {
					list.remove(n);
				}
			}
			return list;
		}
			
		return null;
	}

	@Override
	protected boolean execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			sender.sendMessage(getHelp());
		} else if (args[0].equalsIgnoreCase("help")) {
			
			String help = "===== HomesThatWork Help =====\n\n";
			
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
				
				sender.sendMessage( MessageManager.getErrorMsg( "not-a-command" ));
			} else {
				return false;
			}
			
			sender.sendMessage(help);
		} else if (args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("HomesThatWork Version " + WorkingHomes.CURRENT_VERSION);
		} else {
			sender.sendMessage( MessageManager.getErrorMsg( "not-a-subcommand" ) );
		}
		
		return true;
	}

}
