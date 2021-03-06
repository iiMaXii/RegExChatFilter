package com.djupfryst.RegExChatFilter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Handles the configuration file.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class Config {
	private File file;
	private YamlConfiguration config = new YamlConfiguration();

	public Config(RegExChatFilter plugin) {
		file = new File(plugin.getDataFolder(), "config.yml");

		if (!file.exists()) {
			file.getParentFile().mkdir();
			plugin.saveResource("config.yml", true);
		}

		try {
			config.load(file);
		} catch (IOException e) {
			Log.warning("Could not load the file configuration file");
		} catch (InvalidConfigurationException e) {
			Log.warning("Could not load the file configuration file, invalid configuration");
		}
	}

	public boolean reload() {
		try {
			config.load(file);
			return true;
		} catch (IOException e) {
			Log.warning("Could not reload the file configuration file");
		} catch (InvalidConfigurationException e) {
			Log.warning("Could not reload the file configuration file, invalid configuration");
		}
		return false;
	}

	public Pattern[] getFilterPatterns() {
		// Load the patterns to a list of strings
		List<String> stringPatterns = config.getStringList("filter");
		if (stringPatterns == null)
			stringPatterns = new LinkedList<String>();

		if (stringPatterns.size() == 0) {
			String stringPattern = config.getString("filter");
			if (stringPattern != null) {
				stringPatterns.add(stringPattern);
			}
		}

		// Compile the patterns
		List<Pattern> patterns = new LinkedList<Pattern>();
		for (int i = 0; i < stringPatterns.size(); i++) {
			try {
				patterns.add(Pattern.compile(stringPatterns.get(i), Pattern.CASE_INSENSITIVE));
			} catch (PatternSyntaxException e) {
				Log.warning("The pattern \"" + stringPatterns.get(i) + "\" is not a valid regular expression.");
			}
		}
		Log.info("Successfully compiled " + patterns.size() + " of " + stringPatterns.size() + " patterns");

		// Convert to an array
		Pattern[] filterPatterns;
		filterPatterns = new Pattern[patterns.size()];
		patterns.toArray(filterPatterns);

		return filterPatterns;
	}
}
