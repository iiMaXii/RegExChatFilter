package com.djupfryst.RegExChatFilter;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Listens for chat messages and commands, then filters them.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class ChatListener implements Listener {
	private Pattern[] patterns;

	public ChatListener(Pattern[] patterns) {
		this.patterns = patterns;
	}

	public void setPatterns(Pattern[] patterns) {
		this.patterns = patterns;
	}

	private String filter(String string) {
		// Make sure our string is in UTF-8
		try {
			string = new String(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Log.severe("This system does not support UTF-8, what a shame.");
		}

		// Replace the patterns
		boolean isFiltered = false;
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				string = matcher.replaceAll("");
				isFiltered = true;
			}
		}

		return (isFiltered) ? string : null;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		String message = filter(event.getMessage());

		if (message != null) {
			event.setMessage(message);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String message = filter(event.getMessage());

		if (message != null) {
			event.setMessage(message);
		}
	}
}
