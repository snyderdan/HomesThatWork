package com.dsnyder.homesthatwork;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.moveFile;

/**
 *
 * AdminMip/TheMIP3 created this file to add configuration management to HTW.
 */
public class FileManager
{

    private static File playerconf;

    private static YamlConfiguration stringconf;
    private static YamlConfiguration conf;

    private static Player player;

    public FileManager()
    {

        stringconf = loadStringConf();
        prepFiles();
    }

    // Get the player's specific config
    public static YamlConfiguration getHomeConf( OfflinePlayer pl )
    {
        // Test to see if the player's config is in the root folder of the plugin
        File testConf = new File( WorkingHomes.getPlugin().getDataFolder(), pl.getUniqueId() + ".yml" );
        if( testConf.exists() )
        {
            try {

         // if it is, move it to the new subdirectory called "home_data" and delete the old one
                moveFile( testConf, new File( WorkingHomes.getPlugin().getDataFolder() + "/home_data/", pl.getUniqueId() + ".yml" ));
                testConf.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        // Load the final version if we've moved it.  If not, just load the existing one
        playerconf = new File( WorkingHomes.getPlugin().getDataFolder() + "/home_data/",  pl.getUniqueId() + ".yml");

        // Read file
        conf = YamlConfiguration.loadConfiguration(playerconf);

        return conf;

    }


    // Check if the home_data directory exists already.  If not, make it.
    private void checkFileStructure()
    {
        File dir = new File( WorkingHomes.getPlugin().getDataFolder(), "home_data");
        if( !(dir.exists()) ) {

            dir.mkdirs();
        }
    }

    private static YamlConfiguration loadStringConf()
    {

        File f = new File( WorkingHomes.getPlugin().getDataFolder(), "strings.yml" );

        if( !f.exists() )
        {

                WorkingHomes.getPlugin().saveResource( "strings.yml", true);
            f = new File( WorkingHomes.getPlugin().getDataFolder(), "strings.yml" );
        }

        conf = YamlConfiguration.loadConfiguration( f );
        System.out.println("]HomesThatWork] Loading strings.yml file...");
        return conf;
    }

    // Usually works, save the home to the config.
    public static boolean saveHomes(String msg, Player pl ) {
        try {
            conf.save(playerconf);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pl.sendMessage(msg);
            return false;
        }
    }

    // Return the string config.  Used in MessageManager.java
    public static YamlConfiguration getStringConf()
    {

        return stringconf;

    }

    private void prepFiles()
    {

        checkFileStructure();


    }
}
