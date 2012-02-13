package com.djupfryst.RegExChatFilter;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Filters the chat from unwanted characters.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class RegExChatFilter extends JavaPlugin {
	public void onEnable() {
		Config config = new Config(this);
		getServer().getPluginManager().registerEvents(new ChatListener(config.getFilterPatterns(), config.getMode()), this);

		Log.info("Version " + getDescription().getVersion() + ". Enabled");
	}

	public void onDisable() {
		Log.info("Version " + getDescription().getVersion() + ". Disabled");
	}
}
