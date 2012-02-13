package com.djupfryst.ChatFilter;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Listens for chat messages and commands, then filters them.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class ChatListener implements Listener {
	private Pattern[] patterns;
	private byte mode;

	public ChatListener(Pattern[] patterns, byte mode) {
		this.patterns = patterns;
		this.mode = mode;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent event) {
		// Make sure our string is in UTF-8
		String message = event.getMessage();
		try {
			message = new String(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Log.severe("This system does not support UTF-8, what a shame.");
		}

		// Replace the patterns
		boolean isFiltered = false;
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				message = matcher.replaceAll("");
				isFiltered = true;
			}
		}

		if (isFiltered) {
			if (mode == 0) {
				event.setMessage(message);
			} else {
				event.setCancelled(true);
				event.getPlayer().chat(message);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		onPlayerChat((PlayerChatEvent) event);
	}
}
