package com.dsnyder.homesthatwork;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.moveFile;
import static org.apache.commons.io.FileUtils.openInputStream;

/**
 *
 *  AdminMip/TheMIP3 created this file to add configuration management to HTW.
 *
 */
public class FileManager {

    private static File playerconf;

    private static YamlConfiguration stringconf;
    private static YamlConfiguration conf;

    private static Player player;

    public FileManager() {

        stringconf = loadStringConf();
        prepFiles();
    }

    // Get the player's specific config
    public static YamlConfiguration getHomeConf(OfflinePlayer pl) {
        // Test to see if the player's config is in the root folder of the plugin
        File testConf = new File(WorkingHomes.getPlugin().getDataFolder(), pl.getUniqueId() + ".yml");
        if (testConf.exists()) {
            try {

                // if it is, move it to the new subdirectory called "home_data" and delete the old one
                moveFile(testConf, new File(WorkingHomes.getPlugin().getDataFolder() + "/home_data/", pl.getUniqueId() + ".yml"));
                testConf.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        // Load the final version if we've moved it.  If not, just load the existing one
        playerconf = new File(WorkingHomes.getPlugin().getDataFolder() + "/home_data/", pl.getUniqueId() + ".yml");

        // Read file
        conf = YamlConfiguration.loadConfiguration(playerconf);

        return conf;

    }


    // Check if the home_data directory exists already.  If not, make it.
    private void checkFileStructure() {
        File dir = new File(WorkingHomes.getPlugin().getDataFolder(), "home_data");
        if (!(dir.exists())) {

            dir.mkdirs();
        }
    }

    private static YamlConfiguration loadStringConf() {

        File f = new File(WorkingHomes.getPlugin().getDataFolder(), "config.yml");

        if (!f.exists()) {
            WorkingHomes.getPlugin().saveDefaultConfig();
            f = new File(WorkingHomes.getPlugin().getDataFolder(), "config.yml");
        }
        // If the YAML is valid,
        try {

            conf = YamlConfiguration.loadConfiguration(f);
            System.out.println("[HomesThatWork] Attempting to load config.yml file...");

        } catch (YAMLException YamlError) {

            System.out.println("[HomesThatWork] There was an error parsing the config.yml file!");

            try {
                moveFile(f, new File(WorkingHomes.getPlugin().getDataFolder(), "config.yml.old"));
            } catch (IOException e) {

                System.out.println("[HomesThatWork] There was an error renaming the old file. Contact the developer!");
            }
            WorkingHomes.getPlugin().saveDefaultConfig();

            System.out.println("[HomesThatWork] Replacing old copy with a default copy.");
            System.out.println("[HomesThatWork] A backup was made at config.yml.old");

        }

        return conf;
    }

    // Usually works, save the home to the config.
    public static boolean saveHomes(String msg, Player pl) {
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
    public static YamlConfiguration getStringConf() {

        return stringconf;

    }

    private void prepFiles() {

        checkFileStructure();


    }

}
