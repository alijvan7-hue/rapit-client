package com.rapit.client.gui.hud;

import com.rapit.client.RapitClient;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Base class for every draggable HUD element.
 */
public abstract class HUDElement {

    protected final Minecraft mc = Minecraft.getMinecraft();

    private final String name;
    private int    x, y;
    private int    width, height;
    private boolean enabled = true;

    protected HUDElement(String name, int x, int y, int width, int height) {
        this.name   = name;
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
    }

    // ── Abstract ──────────────────────────────────────────────────────────────

    /** Override to render this element. Called every frame when enabled. */
    public abstract void render(ScaledResolution sr);

    // ── Shared rendering helpers ──────────────────────────────────────────────

    /**
     * Draws the standard HUD element background pill.
     * Call at the start of {@link #render(ScaledResolution)}.
     */
    protected void drawBackground() {
        RenderUtil.drawRoundedRect(x, y, width, height, 4,
                ThemeManager.withAlpha(ThemeManager.COLOR_BACKGROUND, 180));
    }

    /** Draws a single line of text inside the element. */
    protected void drawText(String text, int offsetX, int offsetY, int color) {
        RapitClient.getInstance().getFontManager()
                .drawString(FontManager.FONT_REGULAR, text, x + offsetX, y + offsetY, color);
    }

    protected void drawBoldText(String text, int offsetX, int offsetY, int color) {
        RapitClient.getInstance().getFontManager()
                .drawString(FontManager.FONT_BOLD, text, x + offsetX, y + offsetY, color);
    }

    // ── HUD Editor support ───────────────────────────────────────────────────

    /** Draws an editor outline (used in HUD Editor mode). */
    public void drawEditorOutline(boolean hovered) {
        int borderColor = hovered ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_BORDER;
        RenderUtil.drawBorder(x - 1, y - 1, width + 2, height + 2, borderColor);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String  getName()            { return name; }
    public int     getX()              { return x; }
    public int     getY()              { return y; }
    public int     getWidth()          { return width; }
    public int     getHeight()         { return height; }
    public boolean isEnabled()         { return enabled; }

    public void setX(int x)            { this.x = x; }
    public void setY(int y)            { this.y = y; }
    public void setWidth(int w)        { this.width  = w; }
    public void setHeight(int h)       { this.height = h; }
    public void setEnabled(boolean en) { this.enabled = en; }

    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
