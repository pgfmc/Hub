package tk.pgfriends.hub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hub implements CommandExecutor { // /hub

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { return true; }
		
		
		if (cmd.equals("hub"))
		{
			Player player = (Player) sender;
			
			if (args.length == 2) // If the command looks like: /hub <player>
			{
				//TODO send <player> to the hub
				
				return true; // End the event, we are done
			}
			
			// If the command is only: /hub
			// TODO send sender to the hub
		}
		
		
		
		
		return false;
	}


}