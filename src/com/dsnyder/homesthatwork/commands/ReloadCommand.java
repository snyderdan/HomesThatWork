package com.dsnyder.homesthatwork.commands;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import com.dsnyder.homesthatwork.MessageManager;
import com.dsnyder.homesthatwork.WorkingHomes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.List;

/**
 * Created by TheMIP3 on 11/3/16.
 */
public class ReloadCommand extends GenericCommand {

    public ReloadCommand()
    {

        super( "htw-reload", MessageManager.getMsg( "help-reload" ), "/htw-reload", "homesthatwork.command.reload");

    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {

if( !(sender instanceof Player) ) {

    WorkingHomes.getPlugin().reloadConfig();
    MessageManager.reloadMsgs();
    sender.sendMessage( "[HomesThatWork] All HomesThatWork messages reloaded!" );

} else {

        WorkingHomes.getPlugin().reloadConfig();
        MessageManager.reloadMsgs();
        sender.sendMessage(MessageManager.getSuccessMsg( "reloaded" ));
            }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
