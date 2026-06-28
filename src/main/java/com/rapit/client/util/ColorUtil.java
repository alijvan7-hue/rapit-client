package com.rapit.client.util;

import java.awt.Color;

/** Color manipulation utilities. */
public final class ColorUtil {

    private ColorUtil() {}

    /** Converts ARGB int to AWT Color. */
    public static Color toAWT(int argb) {
        return new Color(argb, true);
    }

    /** Converts AWT Color to ARGB int. */
    public static int fromAWT(Color c) {
        return c.getRGB();
    }

    /** Returns ARGB with the given alpha (0-255). */
    public static int withAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | ((alpha & 0xFF) << 24);
    }

    /** Extracts alpha (0-255) from ARGB. */
    public static int getAlpha(int color) { return (color >> 24) & 0xFF; }
    public static int getRed(int color)   { return (color >> 16) & 0xFF; }
    public static int getGreen(int color) { return (color >>  8) & 0xFF; }
    public static int getBlue(int color)  { return  color        & 0xFF; }

    /**
     * Rainbow color cycling based on system time.
     * @param offset  phase offset in ms (use different values per element for spread)
     * @param period  full cycle period in ms
     */
    public static int rainbow(long offset, long period) {
        float hue = ((System.currentTimeMillis() + offset) % period) / (float) period;
        return Color.HSBtoRGB(hue, 0.8f, 1.0f) | 0xFF000000;
    }
}
