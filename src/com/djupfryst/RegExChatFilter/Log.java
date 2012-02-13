package com.djupfryst.RegExChatFilter;

import java.util.logging.Logger;

/**
 * Write to the log in a convenient way.
 * 
 * @author iiMaXii (theiiMaXii at gmail dot com)
 * 
 */
public class Log {
	private final static Logger LOGGER = Logger.getLogger("Minecraft");

	public final static void info(String msg) {
		LOGGER.info("[ColorFilter] " + msg);
	}

	public final static void warning(String msg) {
		LOGGER.warning("[ColorFilter] " + msg);
	}

	public final static void severe(String msg) {
		LOGGER.severe("[ColorFilter] " + msg);
	}
}
