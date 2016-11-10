package com.dsnyder.homesthatwork.commands;

import com.dsnyder.homesthatwork.MessageManager;
import com.dsnyder.homesthatwork.WorkingHomes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by TheMIP3 on 11/3/16.
 *
 */
public class ReloadCommand extends GenericCommand {

    public ReloadCommand() {
        super( "htw-reload", MessageManager.getMsg( "help-reload" ), "/htw-reload", "homesthatwork.command.reload");
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        WorkingHomes.getPlugin().reloadConfig();
        MessageManager.reloadMsgs();
        sender.sendMessage(MessageManager.getSuccessMsg("reloaded"));
        System.out.println(MessageManager.getSuccessMsg("reloaded"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
