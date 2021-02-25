package tk.pgfriends.hub;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import tk.pgfriends.hub.events.PlayerEvents;

public class Main extends JavaPlugin {
	
	public static HashMap<String, Location> blBr = new HashMap<String, Location>();
	
	@Override
	public void onEnable()
	{
		File file = new File(getDataFolder() + File.separator + "database.yml");
		FileConfiguration database = YamlConfiguration.loadConfiguration(file);
		
		if (!file.exists())
		{
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (file.exists())
		{
			// ignore for now lol
			// get the variables from database here
		}
		
		
		
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
	}
	
	@Override
	public void onDisable()
	{
		
		File file = new File(getDataFolder() + File.separator + "database.yml");
		FileConfiguration database = YamlConfiguration.loadConfiguration(file);
		
		for (String uuid : blBr.keySet())
		{
			database.set("blockBroken.playerData." + uuid, blBr.get(uuid).toString());
		}
		
		
		
		
		try {
			database.save(file);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//nice
