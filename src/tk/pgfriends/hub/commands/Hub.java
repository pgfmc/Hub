package tk.pgfriends.hub.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tk.pgfriends.hub.Main;

public class Hub implements CommandExecutor { // /hub
	
	private Main plugin;

	public Hub(Main plugin) // Helps me from having to use static because File is weird
	{
	   this.plugin = plugin;
	}
	
	File file = new File(plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { return true; }
		
			Player player = (Player) sender; // new Player object from CommandSender
			
			Location dest = new Location(Bukkit.getWorld("hub"), 0.5, 193, 0.5);
			
			
			
			if (args.length == 3) { return false; } // Returns false usage of /hub if more than 3 arguments (/hub bkYT str)
			
			if (args.length == 2) // If the command looks like: /hub <player>
			{
				if (Bukkit.getPlayer(args[1]) == null) { return false; } // If the requested player name doesn't match any online, return false usage
				
				player = Bukkit.getPlayer(args[1]); // Get the Player object from the requested player name
				
				Main.save(player, player.getLocation(), dest, database); // Puts the players UUID and pairs it with their Location in the HashMap in Main
				
				return true; // End the event, we are done, kick us out (return true; if successful, return false; if unsucessful)
			}
			
			
			Main.save(player, player.getLocation(), dest, database); // Puts the players UUID and pairs it with their Location in the HashMap in Main
			
			return true; // You NEED to return a boolean or else it will give error
	}


}