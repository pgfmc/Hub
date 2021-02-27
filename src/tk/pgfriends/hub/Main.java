package tk.pgfriends.hub;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tk.pgfriends.hub.commands.Hub;

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
		}
		
		
		
		
		
		
		
		
	}
	
	@Override
	public void onDisable() // When the plugin is disabled
	{
		// database.set("blockBroken.playerData." + uuid, pLoc.get(uuid).toString());
		
		
		try {
			database.save(file); // Tries to save file

		} catch (IOException e) {
			e.printStackTrace(); // Doesn't crash plugin if the above fails
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void save(Player player, Location loc, Location dest, FileConfiguration db, File file) // Saves the data to the file when called
	{
		db.set(loc.getWorld().getName() + ".uuid." + player.getUniqueId().toString() + "." + , loc.serialize());
		
		try {
			db.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.teleport(dest); // Teleports sender to the hub if no errors while saving
	}
	
	
	
	
	
	@SuppressWarnings({ "unchecked" })
	public static Location load(String uuid, World world, FileConfiguration db) // Loads the data to the plugin when called
	{
		
		return (Location.deserialize((Map<String, Object>) (db.get(world.getName() + "." + uuid))));
	}
}