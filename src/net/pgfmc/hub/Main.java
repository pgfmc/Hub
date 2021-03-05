package net.pgfmc.hub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.hub.commands.Hub;
import net.pgfmc.hub.commands.Survival;

public class Main extends JavaPlugin {
	
	public static Main plugin; // Used for constructor
	
	File file = new File(getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data	
	
	
	@Override
	public void onEnable() // When the plugin is enabled
	{
		plugin = this;
		
		
		//REGISTRATION for Events and Commands!
		// (We don't need this right now) -- getServer().getPluginManager().registerEvents(new PlayerEvents(), this); // Registers PlayerEvents events class
        this.getCommand("hub").setExecutor(new Hub()); // Registers Hub command class
        this.getCommand("survival").setExecutor(new Survival()); // Registers Survival command class
        
		
		if (!file.exists()) // If the file doesn't exist, create one
		{
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (file.exists()) // If it does exist, load in some data if needed
		{
			// ignore for now lol
			// get the variables from database here
			// Probably won't need this
		}
		
		
		
		
		
		
		
		
	}
	
	@Override
	public void onDisable() // When the plugin is disabled
	{
		System.out.println("[Hub] Disabling plugin...");
	}
	
	
	
	
	
	
	
	
	
	
	public static void save(Player player, Player sender, Location loc, Location dest, FileConfiguration db, File file) // Saves the data to the file when called
	{
		String playerUUID = player.getUniqueId().toString();
		String senderUUID = sender.getUniqueId().toString();
		String worldName = loc.getWorld().getName();
		
		if (worldName.equals("hub")) // Need to make sure they spawn at 0, 0 so we will save their coordinates as 0, 0
		{
			loc.setX(0.5);
			loc.setZ(193.0);
			loc.setY(0.5);
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
		}
		
		// If a staff ran /<command> [player]
		sender.sendMessage("§a" + player.getName() + " was successfully sent to " + dest.getWorld().getName() + "!");
		player.sendMessage("§aYou've been sent to " + dest.getWorld().getName() + " by a staff member!");
		
	}
	
	
	

	public static Location load(String uuid, World dest, FileConfiguration db, File file) // Loads the data to the plugin when called
	{
		/*
		if (dest.getName().equals("hub")) // This isn't actually being used yet
		{
			Location loc = new Location(dest, 0.5, 193.0, 0.5);
			return loc;
		}
		*/
		
		if (file.exists()) {
			try {
				db.load(file); // loads file (duh)
				
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
		
		return new Location(dest, 0.5, 300.0, 0.5);
		
	}
}