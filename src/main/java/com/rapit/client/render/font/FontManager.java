package com.rapit.client.render.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages custom TTF fonts for Rapit Client's UI.
 * Falls back gracefully to Minecraft's built-in FontRenderer.
 */
public class FontManager {

    private final Map<String, CustomFontRenderer> fonts = new HashMap<>();

    public static final String FONT_REGULAR = "regular";
    public static final String FONT_BOLD    = "bold";
    public static final String FONT_MONO    = "mono";

    public FontManager() {
        loadDefaults();
    }

    private void loadDefaults() {
        try {
            fonts.put(FONT_REGULAR, new CustomFontRenderer(new Font("SansSerif",  Font.PLAIN, 18), 18));
            fonts.put(FONT_BOLD,    new CustomFontRenderer(new Font("SansSerif",  Font.BOLD,  18), 18));
            fonts.put(FONT_MONO,    new CustomFontRenderer(new Font("Monospaced", Font.PLAIN, 16), 16));
        } catch (Exception ignored) {}
    }

    public CustomFontRenderer getFont(String key) { return fonts.get(key); }
    public CustomFontRenderer getRegular()        { return getFont(FONT_REGULAR); }
    public CustomFontRenderer getBold()           { return getFont(FONT_BOLD); }
    public CustomFontRenderer getMono()           { return getFont(FONT_MONO); }

    public int drawString(String key, String text, float x, float y, int color) {
        CustomFontRenderer cfr = getFont(key);
        if (cfr != null) return cfr.drawString(text, x, y, color);
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawString(text, x, y, color, false);
        return fr.getStringWidth(text);
    }

    public int getStringWidth(String key, String text) {
        CustomFontRenderer cfr = getFont(key);
        if (cfr != null) return cfr.getStringWidth(text);
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
    }
}
