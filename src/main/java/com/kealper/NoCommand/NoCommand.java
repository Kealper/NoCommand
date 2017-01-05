package com.kealper.NoCommand;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.kealper.NoCommand.EventListener;

public class NoCommand extends JavaPlugin {

	public static Plugin plugin;
	public static String version;
	public static List<String> authors;
	public static List<String> blockedCommands;
	public static Boolean shouldCancelOps;
	public static Logger logger;
	private PluginDescriptionFile info;
	private EventListener el = new EventListener();
	private FileConfiguration config;
	
	public void onEnable() {
		info = this.getDescription();
		version = info.getVersion();
		authors = info.getAuthors();
		plugin = this;
		logger = getLogger();
		saveDefaultConfig();
		config = getConfig();
		shouldCancelOps = config.getBoolean("shouldCancelOps");
		blockedCommands = config.getStringList("blockedCommands");
		getServer().getPluginManager().registerEvents(el, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("nocommand")) {
			if (args.length < 1) {
				sender.sendMessage(ChatColor.GREEN + "NoCommand version " + version + " by " + authors);
				sender.sendMessage(ChatColor.GREEN + "Usage:");
				sender.sendMessage(ChatColor.YELLOW + "/nocommand reload" + ChatColor.GREEN + " - Reloads the configuration");
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.isOp()) {
						reloadConfig();
						config = getConfig();
						shouldCancelOps = config.getBoolean("shouldCancelOps");
						blockedCommands = config.getStringList("blockedCommands");
						sender.sendMessage(ChatColor.GREEN + "NoCommand configuration has been reloaded!");
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + args[0] + " is not a valid NoCommand command!");
				}
			}
			return true;
		}
		return false;
	}
	
	public void onDisable() {

	}

}