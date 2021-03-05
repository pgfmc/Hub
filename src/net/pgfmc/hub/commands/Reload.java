package net.pgfmc.hub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.pgfmc.hub.Main;

public class Reload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {

		Main.plugin.reloadConfig(); // ???
		
		return true;
	}
	

}
