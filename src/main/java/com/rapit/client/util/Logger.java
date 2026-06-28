package com.rapit.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Centralized logging wrapper for Rapit Client.
 * Uses Log4J2 (bundled with Forge) and prefixes all messages.
 */
public final class Logger {

    private static final org.apache.logging.log4j.Logger LOG =
            LogManager.getLogger("RapitClient");

    private Logger() {}

    public static void info(String msg)  { LOG.info("[Rapit] " + msg); }
    public static void warn(String msg)  { LOG.warn("[Rapit] " + msg); }
    public static void error(String msg) { LOG.error("[Rapit] " + msg); }
    public static void debug(String msg) { LOG.debug("[Rapit] " + msg); }
}
