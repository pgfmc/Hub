package tk.pgfriends.savetest.events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tk.pgfriends.savetest.Main;

public class PlayerEvents implements Listener {
	
	@EventHandler
	public void onBreak(PlayerInteractEvent e)
	{
		
		if (!e.getAction().equals(Action.LEFT_CLICK_BLOCK)) { return; }
		
		UUID uuid = e.getPlayer().getUniqueId();
		
		Main.blBr.put(uuid.toString(), e.getPlayer().getLocation());
		
	}

}
