package com.dsnyder.homesthatwork;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class MessageManager {

    private static String PREFIX;

    private static YamlConfiguration conf;
    private static HashMap<String, String> configMsgs;

    public MessageManager()
    {

        loadStrings();

    }


    private static String getConfigMsg( String path )
    {

        return conf.getString( path );
    }

    public static String getMsg( String key )
    {

        return configMsgs.get( key );

    }
    public static String getErrorMsg( String key )
    {

        return getPfx() + ChatColor.RED + configMsgs.get( key );

    }

    public static String getSuccessMsg( String key )
    {

        return getPfx() + ChatColor.GREEN + configMsgs.get( key );

    }
    public static String getPfx()
    {

        return PREFIX + " ";

    }

    public static void loadStrings()
    {

        conf = FileManager.getStringConf();

        configMsgs = new HashMap<>();
        configMsgs.put( "prefix", getConfigMsg( "messages.prefix" ) );
        // player errors
        configMsgs.put( "max-homes-set", getConfigMsg( "messages.player-errors.max-homes-set" ) );
        configMsgs.put( "home-exists", getConfigMsg( "messages.player-errors.home-already-exists" ) );
        configMsgs.put( "too-many-homes", getConfigMsg( "messages.player-errors.too-many-homes" ) );
        configMsgs.put( "does-not-exist", getConfigMsg( "messages.player-errors.home-not-exist" ) );
        configMsgs.put( "no-homes-set", getConfigMsg( "messages.player-errors.no-homes-set" ) );
        configMsgs.put( "home-rem-error", getConfigMsg( "messages.player-errors.home-remove-fail" ) );
        configMsgs.put( "no-prev-loc", getConfigMsg( "messages.player-errors.no-previous-loc" ) );
        // General player messages
        configMsgs.put( "home-set", getConfigMsg( "messages.player-msgs.home-set-success" ) );
        configMsgs.put( "home-removed", getConfigMsg( "messages.player-msgs.home-remove-success" ) );
        // Permission errors
        configMsgs.put( "no-command-perm", getConfigMsg("messages.permission-msgs.no-command-perm" ));
        configMsgs.put( "no-inter-perm", getConfigMsg( "messages.permission-msgs.no-interdimensional-perm" ) );
        // File Errors
        configMsgs.put( "sethome-error", getConfigMsg( "messages.file-errors.home-set-error" ) );
        configMsgs.put( "corrupt-data", getConfigMsg( "messages.file-errors.corrupt-home-data") );
        configMsgs.put( "loc-save-error", getConfigMsg( "messages.file-errors.last-location-save-error" ) );
        configMsgs.put( "reload-fail", getConfigMsg( "messages.file-errors.reload-fail" ) );

        // Command Help Descriptions
        configMsgs.put( "help-delhome", getConfigMsg( "messages.command-msgs.delhome.description") );
        configMsgs.put( "help-goback", getConfigMsg( "messages.command-msgs.goback.description" ) );
        configMsgs.put( "help-home", getConfigMsg( "messages.command-msgs.home.description" ) );
        configMsgs.put( "help-sethome", getConfigMsg( "messages.command-msgs.sethome.description" ) );
        configMsgs.put( "help-listhomes", getConfigMsg( "messages.command-msgs.listhomes.description" ) );
        configMsgs.put( "help-homeinfo", getConfigMsg( "messages.command-msgs.homeinfo.description" ) );
        configMsgs.put( "help-htw", getConfigMsg( "messages.command-msgs.htw.description" ) );
        configMsgs.put( "help-reload", getConfigMsg( "messages.command-msgs.htw-reload.description"));
        configMsgs.put( "reloaded", getConfigMsg( "messages.command-msgs.htw-reload.success" ) );


        // Not a (sub)command errors
        configMsgs.put( "not-a-command", getConfigMsg( "messages.not-a-command-error" ) );
        configMsgs.put( "not-a-subcommand", getConfigMsg( "messages.invalid-subcommand" ) );

        // Admin errors
        configMsgs.put( "no-perm-others", getConfigMsg( "messages.admin-errors.noperm" ) );
        configMsgs.put( "offline-player", getConfigMsg( "messages.admin-errors.offline" ) );
        configMsgs.put( "invalid-player", getConfigMsg( "messages.admin-errors.invalid-player" ) );

        PREFIX = ChatColor.translateAlternateColorCodes( '&', configMsgs.get( "prefix" ) );

    }

    public static void reloadMsgs()
    {

        configMsgs.clear();

        conf = FileManager.getStringConf();

        configMsgs.put( "prefix", getConfigMsg( "messages.prefix" ) );

        // Player errors
        configMsgs.put( "max-homes-set", getConfigMsg( "messages.player-errors.max-homes-set" ) );
        configMsgs.put( "home-exists", getConfigMsg( "messages.player-errors.home-already-exists" ) );
        configMsgs.put( "too-many-homes", getConfigMsg( "messages.player-errors.too-many-homes" ) );
        configMsgs.put( "does-not-exist", getConfigMsg( "messages.player-errors.home-not-exist" ) );
        configMsgs.put( "no-homes-set", getConfigMsg( "messages.player-errors.no-homes-set" ) );
        configMsgs.put( "home-rem-error", getConfigMsg( "messages.player-errors.home-remove-fail" ) );
        configMsgs.put( "no-prev-loc", getConfigMsg( "messages.player-errors.no-previous-loc" ) );

        // General player messages
        configMsgs.put( "home-set", getConfigMsg( "messages.player-msgs.home-set-success" ) );
        configMsgs.put( "home-removed", getConfigMsg( "messages.player-msgs.home-remove-success" ) );

        // Permission errors
        configMsgs.put( "no-command-perm", getConfigMsg("messages.permission-msgs.no-command-perm" ));
        configMsgs.put( "no-inter-perm", getConfigMsg( "messages.permission-msgs.no-interdimensional-perm" ) );

        // File Errors
        configMsgs.put( "sethome-error", getConfigMsg( "messages.file-errors.home-set-error" ) );
        configMsgs.put( "corrupt-data", getConfigMsg( "messages.file-errors.corrupt-home-data") );
        configMsgs.put( "loc-save-error", getConfigMsg( "messages.file-errors.last-location-save-error" ) );
        configMsgs.put( "reload-fail", getConfigMsg( "messages.file-errors.reload-fail" ) );

        // Command Help Descriptions
        configMsgs.put( "help-delhome", getConfigMsg( "messages.command-msgs.delhome.description") );
        configMsgs.put( "help-goback", getConfigMsg( "messages.command-msgs.goback.description" ) );
        configMsgs.put( "help-home", getConfigMsg( "messages.command-msgs.home.description" ) );
        configMsgs.put( "help-sethome", getConfigMsg( "messages.command-msgs.sethome.description" ) );
        configMsgs.put( "help-listhomes", getConfigMsg( "messages.command-msgs.listhomes.description" ) );
        configMsgs.put( "help-homeinfo", getConfigMsg( "messages.command-msgs.homeinfo.description" ) );
        configMsgs.put( "help-htw", getConfigMsg( "messages.command-msgs.htw.description" ) );
        configMsgs.put( "help-reload", getConfigMsg( "messages.command-msgs.htw-reload.description"));
        configMsgs.put( "reloaded", getConfigMsg( "messages.command-msgs.htw-reload.success" ) );


        // Not a (sub)command errors
        configMsgs.put( "not-a-command", getConfigMsg( "messages.not-a-command-error" ) );
        configMsgs.put( "not-a-subcommand", getConfigMsg( "messages.invalid-subcommand" ) );

        // Admin errors
        configMsgs.put( "no-perm-others", getConfigMsg( "messages.admin-errors.noperm" ) );
        configMsgs.put( "offline-player", getConfigMsg( "messages.admin-errors.offline" ) );
        configMsgs.put( "invalid-player", getConfigMsg( "messages.admin-errors.invalid-player" ) );

        PREFIX = ChatColor.translateAlternateColorCodes( '&', configMsgs.get( "prefix" ) );



    }

}
