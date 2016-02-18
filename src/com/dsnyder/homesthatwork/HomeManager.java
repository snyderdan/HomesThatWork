package com.dsnyder.homesthatwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.dsnyder.homesthatwork.permissions.PermissionManager;

public class HomeManager {
	
	public static final String LAST_LOCATION		= "__last";
	
	public static final String PERMISSION_MAX_HOMES = "homesthatwork.homes.max.";
	public static final String PERMISSION_DIMENSIONS = "homesthatwork.otherdimensions";
	public static final int    DEFAULT_MAX_HOMES = 10;
	
	private YamlConfiguration config;
	private File playerconf;
	private Player player;
	private int curHomeCount;
	
	public HomeManager(OfflinePlayer pl) {
		// Create/open player config file
		playerconf = new File(WorkingHomes.getPlugin().getDataFolder() + File.separator + pl.getUniqueId() + ".yml");
		config = YamlConfiguration.loadConfiguration(playerconf);
		
		if (pl.isOnline()) player = pl.getPlayer();
		
		if (!checkVersionCompatibility()) {
			System.out.println("WARNING: This version of HomesThatWork may incompatible with previous versions.");
		}
		
		// get the number of homes this player has (1 key = 1 home)
		if (config.getKeys(false).contains(LAST_LOCATION))
			// subtract 2 -- 1 for version key and 1 for last location
			curHomeCount = config.getKeys(false).size() - 2;
		else
			// only subtract 1 for version key
			curHomeCount = config.getKeys(false).size() - 1;
	}
	
	public boolean homeExists(String home) {
		return config.contains(home.toLowerCase());
	}
	
	public void setHome(String home) {
		// ensure this player can create more homes
		if (curHomeCount >= getMaxHomes()) {
			// we check for greater than in case their max homes changed
			// we do not delete the homes however -- they can choose which homes go
			player.sendMessage(ChatColor.RED + "Maxmium number of homes set.");
			return;
		}
		
		if (homeExists(home)) {
			player.sendMessage(ChatColor.RED + "Home already exists");
			return;
		}
		// attempt to set the home and save the configuration file
		config.set(home.toLowerCase(), locationToString(player.getLocation()));
		
		if (!saveHomes(ChatColor.RED + "Unable to save home - contact an administrator")) return;
		// send message that it was successful
		player.sendMessage("Home successfully set");
		curHomeCount++;
	}
	
	public void goHome(String home) {
		// if they have too many homes, then their permissions have changed
		// let them choose which homes they lose
		if (curHomeCount > getMaxHomes()) {
			int hCount = curHomeCount - getMaxHomes();
			player.sendMessage(String.format(ChatColor.RED + 
					"Too many homes set. You must delete %d home%s", hCount, (hCount > 1) ? "s" : ""));
			return;
		}
		// 
		if (!homeExists(home)) {
			player.sendMessage(ChatColor.RED + "Home does not exist");
			return;
		}
		// parse values accordingly
		Location loc = parseLocation((String) config.get(home.toLowerCase()));
		// go to the specified location
		if (loc == null) {
			player.sendMessage(ChatColor.RED + "Home data corrupted - contact an admin");
			return;
		}
		
		if (!loc.getWorld().equals(player.getWorld())) {
			if (!PermissionManager.getManager().hasPermission(player, PERMISSION_DIMENSIONS)) {
				player.sendMessage(ChatColor.RED + "You do not have permission for interdimensional homes");
				return;
			}
		}
		config.set(LAST_LOCATION, locationToString(player.getLocation()));
		saveHomes(ChatColor.RED + "Unable to save last location - please contact an administrator");
		player.teleport(loc);
	}
	
	public void delHome(String home) {
		// only reason you can't delete a home is if it doesn't exist
		if (!homeExists(home.toLowerCase())) {
			player.sendMessage(ChatColor.RED + "Home does not exist");
			return;
		}
		
		try {
			// remove home - should work every time
			config.set(home, null);
			config.save(playerconf);
			player.sendMessage("Home successfully removed");
			curHomeCount--;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player.sendMessage(ChatColor.RED + "An internal error has occured");
		}
	}
	
	public void listHomes() {
		player.sendMessage(generateHomeList());
	}
	
	public void listHomes(OfflinePlayer other) {
		player.sendMessage(new HomeManager(other).generateHomeList());
	}
	
	public void homeInfo(String home) {
		player.sendMessage(generateHomeInfo(home));
	}
	
	public void homeInfo(OfflinePlayer other, String home) {
		player.sendMessage(new HomeManager(other).generateHomeInfo(home));
	}
	
	public void goBack() {
		
		if (!homeExists(LAST_LOCATION)) {
			player.sendMessage(ChatColor.RED + "No previous location recorded!");
			return;
		}
		
		goHome(LAST_LOCATION);
	}
	
	public List<String> getHomeList() {
		List<String> homes = new ArrayList<String>();
		homes.addAll(config.getKeys(false));
		homes.remove(WorkingHomes.VERSION_KEY);
		homes.remove(LAST_LOCATION);
		return homes;
	}
	
	@SuppressWarnings("unused")
	private boolean checkVersionCompatibility() {
		// If the they are up to date, then don't do anything
		if (config.contains(WorkingHomes.VERSION_KEY) && config.get(WorkingHomes.VERSION_KEY).equals(WorkingHomes.CURRENT_VERSION)) 
			return true;
		
		if (!config.contains(WorkingHomes.VERSION_KEY)) {
			// Version 1.0.0 to Version 1.0.1 - fixing case sensitivity
			for (String key : config.getKeys(false)) {
				String value = (String) config.get(key);
				config.set(key, null); // should remove the key from the config
				config.set(key.toLowerCase(), value); // add the value with all lowercase key
			}
		} else if (false) {
			// Version 1.0.1 to Version X.X.X
		}
		
		config.set(WorkingHomes.VERSION_KEY, WorkingHomes.CURRENT_VERSION);
		return saveHomes(ChatColor.RED + "WARNING: An error occurred during version conversion. Please contact an administrator.");
	}
	
	public int comparePlayer(Player pl) {
		// method for binary search through cached HomeManagers
		return pl.getUniqueId().compareTo(player.getUniqueId());
	}
	
	private String generateHomeInfo(String home) {
		
		if (!homeExists(home)) return ChatColor.RED + "Home does not exist";
		
		Location loc = parseLocation((String) config.get(home.toLowerCase()));
		
		return String.format(
				"%s:\n  World: %s\n  X: %.2f Y: %.2f Z: %.2f",
				home, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
	}
	
	private String generateHomeList() {
		
		Set<String> homes = config.getKeys(false);
		homes.remove(LAST_LOCATION);
		homes.remove(WorkingHomes.VERSION_KEY);
		
		if (homes.isEmpty()) return "No homes set";
		
		String homestr = "Homes: ";
		for (String home : homes) homestr += home + ", ";
		// substring is to remove the last comma in the list
		return homestr.substring(0, homestr.length()-2);
	}
	
	private boolean saveHomes(String msg) {
		try {
			config.save(playerconf);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player.sendMessage(msg);
			return false;
		}
	}
	
	private int getMaxHomes() {
		
		int maxHomes = -1;
		
		if (player.isOp()) return Integer.MAX_VALUE;
		
		for (PermissionAttachmentInfo p : player.getEffectivePermissions()) {
			
			String name = p.getPermission().toLowerCase();
			
			if (name.startsWith(PERMISSION_MAX_HOMES)) {
				// if we get a match, then set maxNumHomes to whatever is specified
				maxHomes = Math.max(maxHomes, Integer.parseInt(name.substring(PERMISSION_MAX_HOMES.length())));
				if (maxHomes < 0) {
					System.out.println(String.format(ChatColor.RED + 
							"%s has bad max home value. Defaulting to %d", player.getName(), DEFAULT_MAX_HOMES));
					maxHomes = DEFAULT_MAX_HOMES;
					break;
				}
			}
		}
		
		if (maxHomes == -1) maxHomes = DEFAULT_MAX_HOMES;
		
		return maxHomes;
	}
	
	private static String locationToString(Location pl) {
		return String.format("%s %f %f %f %f %f", pl.getWorld().getName(), pl.getX(), 
				pl.getY(), pl.getZ(), pl.getYaw(), pl.getPitch());
	}
	
	private static Location parseLocation(String st) {
		// get line and split it by spaces. Lazy, yes. But cheap and easy.
		String[] data = st.split(" ");
		try {
			// parse values accordingly
			Location loc = new Location(Bukkit.getWorld(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]),
					Float.parseFloat(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
			return loc;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
	}
}
