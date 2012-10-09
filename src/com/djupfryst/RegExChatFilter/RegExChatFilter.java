package com.djupfryst.RegExChatFilter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Filters the chat from unwanted characters.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class RegExChatFilter extends JavaPlugin implements CommandExecutor {
	private Config config;
	private ChatListener chatListener;

	public void onEnable() {
		config = new Config(this);
		chatListener = new ChatListener(config.getFilterPatterns());

		getServer().getPluginManager().registerEvents(chatListener, this);
	}

	public void onDisable() {
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1 && args[0].toLowerCase().equals("reload")) {

			if (config.reload()) {
				chatListener.setPatterns(config.getFilterPatterns());
				sender.sendMessage(ChatColor.GRAY + "The configuration has been reloaded.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error occured when trying to reload the configuration file.");
			}

			return true;
		} else {
			return false;
		}
	}
}
