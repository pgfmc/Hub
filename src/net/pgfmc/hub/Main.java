package net.pgfmc.hub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.hub.commands.Goto;

public class Main extends JavaPlugin {
	
	public static Main plugin; // Used for constructor
	
	File file = new File(getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	FileConfiguration config = this.getConfig();
	
	
	@Override
	public void onEnable() // When the plugin is enabled
	{
		if (!((new File(getDataFolder() + File.separator + "config.yml")).exists())) // In the config. Ability for per world teleportation enable/disable or w/e
		{
			// ADD ALL NEW WORLD NAMES HERE, CONFIG VALUES MUST MATCH WORLD NAMES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (also add in plugin.yml)
			config.addDefault("hub", true);
			config.addDefault("survival", true);
			config.addDefault("creative", true);
			config.addDefault("prison", true);
			config.addDefault("parkour", true);
	        config.options().copyDefaults(true);
	        saveConfig();
		}

		plugin = this;
		
		
		
		this.getCommand("goto").setExecutor(new Goto());
		
		
		if (!file.exists()) { try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); } }
		
		
		
		
		
		
		Bukkit.getConsoleSender().sendMessage("[PGF Hub] Enabled...");
		
	}
	
	@Override
	public void onDisable() // When the plugin is disabled
	{
		Bukkit.getConsoleSender().sendMessage("[PGF Hub] Disabled...");
	}
	
	
	
	
	
	
	
	
	
	
	public static void save(Player player, Player sender, Location loc, Location dest, FileConfiguration db, File file) // Saves the data to the file when called
	{
		String playerUUID = player.getUniqueId().toString();
		String senderUUID = sender.getUniqueId().toString();
		String worldName = loc.getWorld().getName();
		
		if (worldName.equals("hub")) // Need to make sure they spawn at 0, 0 so we will save their coordinates as 0, 0
		{
			loc = new Location(loc.getWorld(), 0.5, 193.0, 0.5);
		}
		
		// We want the "path" to have the root world name as the MAIN world name (no _nether/_the_end). This information is still saved in <world>.uuid.<uuid>.world
		// The reason for this is because we want to overwrite the saved world with the new world which might be a different dimension WITHOUT creating a new directory for dimensions of a world
		if (worldName.contains("nether")) { db.set(worldName.substring(0, worldName.length() - 7) + ".uuid." + playerUUID, loc); } // If teleporting from Nether
		
		if (worldName.contains("the_end")) { db.set(worldName.substring(0, worldName.length() - 8) + ".uuid." + playerUUID, loc); } // If teleporting from The End
		
		if (!(worldName.contains("the_end")) && !(worldName.contains("nether"))) { db.set(worldName + ".uuid." + playerUUID, loc); } // If teleporting from Overworld
		
		
		try {
			db.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.teleport(dest); // Teleports sender to the hub if no errors while saving

		if (playerUUID.equals(senderUUID)) // If the sender and the person teleporting is the same
		{
			sender.sendMessage("§aSuccssfully sent to " + dest.getWorld().getName() + "!");
			return;
		}
		
		// If a staff ran /<command> [player]
		sender.sendMessage("§a" + player.getName() + " was successfully sent to " + dest.getWorld().getName() + "!");
		player.sendMessage("§aYou've been sent to " + dest.getWorld().getName() + " by a staff member!");
		
	}
	
	
	

	public static Location load(String uuid, World dest, FileConfiguration db, File file) // Loads the data to the plugin when called
	{

		
		if (file.exists()) {
			try {
				db.load(file); // loads file
				
				if (db.get(dest.getName() + ".uuid." + uuid) == null)
				{
					return null;
				}
				
				return (Location) db.get(dest.getName() + ".uuid." + uuid);
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	public static boolean isInWorld(String currLoc, String expectedWorld)
	{
		String[] dims = { currLoc, currLoc + "_nether", currLoc + "_the_end" };
		for (String dim : dims)
		{
			if (expectedWorld == dim)
			{
				return true;
			}
		}
		return false;
	}
}