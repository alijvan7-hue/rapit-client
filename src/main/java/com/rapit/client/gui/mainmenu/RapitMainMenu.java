package com.rapit.client.gui.mainmenu;

import com.rapit.client.RapitClient;
import com.rapit.client.animation.AnimationUtil;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

/**
 * Custom Rapit Client main menu.
 * Replaces the default Minecraft main menu with a branded dark UI.
 */
public class RapitMainMenu extends GuiMainMenu {

    // ── Animation state ───────────────────────────────────────────────────────
    private double fadeIn      = 0.0;
    private double logoFloat   = 0.0;
    private long   openTime;

    // ── Button IDs ────────────────────────────────────────────────────────────
    private static final int BTN_SINGLEPLAYER  = 1;
    private static final int BTN_MULTIPLAYER   = 2;
    private static final int BTN_OPTIONS       = 3;
    private static final int BTN_RESOURCE_PACK = 4;
    private static final int BTN_QUIT          = 5;

    @Override
    public void initGui() {
        openTime   = System.currentTimeMillis();
        fadeIn     = 0.0;

        int cx = width / 2;
        int by = height / 2 - 20;

        buttonList.clear();
        buttonList.add(new GuiButton(BTN_SINGLEPLAYER,  cx - 100, by,       200, 20, I18n.format("menu.singleplayer")));
        buttonList.add(new GuiButton(BTN_MULTIPLAYER,   cx - 100, by + 24,  200, 20, I18n.format("menu.multiplayer")));
        buttonList.add(new GuiButton(BTN_OPTIONS,       cx - 100, by + 48,  97,  20, I18n.format("menu.options")));
        buttonList.add(new GuiButton(BTN_RESOURCE_PACK, cx + 3,   by + 48,  97,  20, I18n.format("menu.resourcePack")));
        buttonList.add(new GuiButton(BTN_QUIT,          cx - 100, by + 72,  200, 20, I18n.format("menu.quit")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Smooth fade in
        fadeIn    = AnimationUtil.lerp(fadeIn, 1.0, 0.08);
        logoFloat = Math.sin(System.currentTimeMillis() / 1200.0) * 3.0;

        float alpha = (float) AnimationUtil.easeOut(fadeIn);

        int w = width, h = height;

        // ── Animated gradient background ──────────────────────────────────────
        RenderUtil.drawGradientV(0, 0, w, h,
                ThemeManager.COLOR_BACKGROUND,
                ThemeManager.withAlpha(ThemeManager.COLOR_SURFACE, 255));

        // Decorative accent bar at top
        RenderUtil.drawGradientV(0, 0, w, 3,
                ThemeManager.COLOR_PRIMARY, ThemeManager.withAlpha(ThemeManager.COLOR_PRIMARY, 0));

        // Subtle grid pattern (faint lines)
        RenderUtil.setAlpha(0.03f * alpha);
        for (int y = 0; y < h; y += 20) RenderUtil.drawRect(0, y, w, 1, 0xFFFFFFFF);
        for (int x = 0; x < w; x += 20) RenderUtil.drawRect(x, 0, 1, h, 0xFFFFFFFF);
        RenderUtil.resetAlpha();

        // ── Logo ──────────────────────────────────────────────────────────────
        FontManager fm = RapitClient.getInstance().getFontManager();

        int logoY = (int)(h / 2 - 120 + logoFloat);

        // Glow behind logo
        RenderUtil.drawGlow(w / 2 - 80, logoY - 5, 160, 50,
                ThemeManager.COLOR_GLOW, 8);

        // Draw "RAPIT" large
        int nameW = fm.getStringWidth(FontManager.FONT_BOLD, "RAPIT CLIENT") * 2;
        // Scale up: push matrix
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glTranslatef(w / 2f, logoY + 20, 0);
        org.lwjgl.opengl.GL11.glScalef(2.5f, 2.5f, 1f);
        fm.drawString(FontManager.FONT_BOLD, "RAPIT",
                -fm.getStringWidth(FontManager.FONT_BOLD, "RAPIT") / 2, 0,
                ThemeManager.COLOR_PRIMARY);
        org.lwjgl.opengl.GL11.glPopMatrix();

        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glTranslatef(w / 2f, logoY + 52, 0);
        org.lwjgl.opengl.GL11.glScalef(1.5f, 1.5f, 1f);
        fm.drawString(FontManager.FONT_REGULAR, "CLIENT",
                -fm.getStringWidth(FontManager.FONT_REGULAR, "CLIENT") / 2, 0,
                ThemeManager.COLOR_WHITE);
        org.lwjgl.opengl.GL11.glPopMatrix();

        // Version badge
        String ver = "v" + RapitClient.VERSION + " | MC 1.8.9";
        fm.drawString(FontManager.FONT_MONO, ver,
                w / 2 - fm.getStringWidth(FontManager.FONT_MONO, ver) / 2,
                logoY + 75, ThemeManager.COLOR_TEXT_MUTED);

        // ── Account info (top-right) ───────────────────────────────────────────
        String user = mc.getSession().getUsername();
        fm.drawString(FontManager.FONT_REGULAR, "\u25CF " + user, w - 8 - fm.getStringWidth(FontManager.FONT_REGULAR, "\u25CF " + user), 8,
                ThemeManager.COLOR_TEXT_MUTED);

        // ── Draw buttons ──────────────────────────────────────────────────────
        RenderUtil.setAlpha(alpha);
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtil.resetAlpha();

        // Bottom credit
        fm.drawString(FontManager.FONT_REGULAR, "Rapit Client \u2022 Minecraft 1.8.9", 4, h - 12,
                ThemeManager.COLOR_TEXT_MUTED);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case BTN_SINGLEPLAYER:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case BTN_MULTIPLAYER:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case BTN_OPTIONS:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case BTN_RESOURCE_PACK:
                mc.displayGuiScreen(new GuiScreenResourcePacks(this));
                break;
            case BTN_QUIT:
                mc.shutdown();
                break;
        }
    }
}
