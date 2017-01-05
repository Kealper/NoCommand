package com.kealper.NoCommand;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.ChatColor;

import com.kealper.NoCommand.NoCommand;

public class EventListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void handlePlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!NoCommand.shouldCancelOps && event.getPlayer().isOp()) {
			return;
		}
		for (String cmd: NoCommand.blockedCommands) {
			if (event.getMessage().toLowerCase().startsWith(cmd)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED+"You aren't allowed to use that command!");
				NoCommand.logger.info(event.getPlayer().getName() + " was blocked from issuing server command: " + event.getMessage());
			}
		}
	}

}