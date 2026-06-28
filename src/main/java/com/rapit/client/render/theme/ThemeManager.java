package com.rapit.client.render.theme;

/** Centralized theme constants for Rapit Client. */
public final class ThemeManager {

    public static final int COLOR_PRIMARY       = 0xFFFFD400;
    public static final int COLOR_BACKGROUND    = 0xFF0E0E0E;
    public static final int COLOR_SURFACE       = 0xFF1A1A1A;
    public static final int COLOR_SURFACE_HOVER = 0xFF222222;
    public static final int COLOR_BORDER        = 0xFF2A2A2A;
    public static final int COLOR_WHITE         = 0xFFFFFFFF;
    public static final int COLOR_TEXT_MUTED    = 0xFF888888;
    public static final int COLOR_ENABLED       = 0xFF4CAF50;
    public static final int COLOR_DISABLED      = 0xFF555555;
    public static final int COLOR_OVERLAY       = 0xCC0E0E0E;
    public static final int COLOR_GLOW          = 0x99FFD400;
    public static final int COLOR_TOGGLE_ON     = COLOR_PRIMARY;
    public static final int COLOR_TOGGLE_OFF    = COLOR_DISABLED;
    public static final int ANIM_DURATION_MS    = 200;
    public static final float CORNER_RADIUS     = 6f;

    private static ThemeManager instance;
    public ThemeManager() { instance = this; }
    public static ThemeManager getInstance() { return instance; }

    public static int withAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | ((alpha & 0xFF) << 24);
    }

    public static int multiplyAlpha(int color, float factor) {
        int a = (int)(((color >> 24) & 0xFF) * factor);
        return (color & 0x00FFFFFF) | ((a & 0xFF) << 24);
    }
}
