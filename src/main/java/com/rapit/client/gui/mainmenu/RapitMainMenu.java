package com.rapit.client.gui.mainmenu;

import com.rapit.client.RapitClient;
import com.rapit.client.animation.AnimationUtil;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

/**
 * Custom Rapit Client main menu — replaces vanilla GuiMainMenu.
 */
public class RapitMainMenu extends GuiScreen {

    private static final int BTN_SINGLEPLAYER  = 1;
    private static final int BTN_MULTIPLAYER   = 2;
    private static final int BTN_OPTIONS       = 3;
    private static final int BTN_RESOURCE_PACK = 4;
    private static final int BTN_QUIT          = 5;

    private double fadeIn    = 0.0;
    private double logoFloat = 0.0;

    @Override
    public void initGui() {
        fadeIn = 0.0;
        int cx = width / 2;
        int by = height / 2 - 10;

        buttonList.clear();
        buttonList.add(new GuiButton(BTN_SINGLEPLAYER,  cx - 100, by,       200, 20, I18n.format("menu.singleplayer")));
        buttonList.add(new GuiButton(BTN_MULTIPLAYER,   cx - 100, by + 24,  200, 20, I18n.format("menu.multiplayer")));
        buttonList.add(new GuiButton(BTN_OPTIONS,       cx - 100, by + 48,  97,  20, I18n.format("menu.options")));
        buttonList.add(new GuiButton(BTN_RESOURCE_PACK, cx + 3,   by + 48,  97,  20, I18n.format("menu.resourcePack")));
        buttonList.add(new GuiButton(BTN_QUIT,          cx - 100, by + 72,  200, 20, I18n.format("menu.quit")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        fadeIn    = AnimationUtil.lerp(fadeIn, 1.0, 0.08);
        logoFloat = Math.sin(System.currentTimeMillis() / 1200.0) * 3.0;
        float alpha = (float) AnimationUtil.easeOut(fadeIn);

        int w = width, h = height;

        // Background gradient
        RenderUtil.drawGradientV(0, 0, w, h, ThemeManager.COLOR_BACKGROUND, ThemeManager.COLOR_SURFACE);
        // Top accent bar
        RenderUtil.drawGradientV(0, 0, w, 3, ThemeManager.COLOR_PRIMARY,
                ThemeManager.withAlpha(ThemeManager.COLOR_PRIMARY, 0));

        FontManager fm = RapitClient.getInstance().getFontManager();

        // Logo area
        int logoY = (int)(h / 2 - 110 + logoFloat);
        RenderUtil.drawGlow(w / 2 - 80, logoY - 5, 160, 50, ThemeManager.COLOR_GLOW, 8);

        // "RAPIT" large
        GL11.glPushMatrix();
        GL11.glTranslatef(w / 2f, logoY + 18, 0);
        GL11.glScalef(2.5f, 2.5f, 1f);
        int rapitW = fm.getStringWidth(FontManager.FONT_BOLD, "RAPIT");
        fm.drawString(FontManager.FONT_BOLD, "RAPIT", -rapitW / 2, 0, ThemeManager.COLOR_PRIMARY);
        GL11.glPopMatrix();

        // "CLIENT" medium
        GL11.glPushMatrix();
        GL11.glTranslatef(w / 2f, logoY + 50, 0);
        GL11.glScalef(1.5f, 1.5f, 1f);
        int clientW = fm.getStringWidth(FontManager.FONT_REGULAR, "CLIENT");
        fm.drawString(FontManager.FONT_REGULAR, "CLIENT", -clientW / 2, 0, ThemeManager.COLOR_WHITE);
        GL11.glPopMatrix();

        // Version
        String ver = "v" + RapitClient.VERSION + " | MC 1.8.9";
        int verW = fm.getStringWidth(FontManager.FONT_MONO, ver);
        fm.drawString(FontManager.FONT_MONO, ver, w / 2 - verW / 2, logoY + 72, ThemeManager.COLOR_TEXT_MUTED);

        // Account info top-right
        String user = mc.getSession().getUsername();
        fm.drawString(FontManager.FONT_REGULAR, "\u25CF " + user,
                w - 8 - fm.getStringWidth(FontManager.FONT_REGULAR, "\u25CF " + user),
                8, ThemeManager.COLOR_TEXT_MUTED);

        // Buttons
        RenderUtil.setAlpha(alpha);
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtil.resetAlpha();

        // Bottom credit
        fm.drawString(FontManager.FONT_REGULAR, "Rapit Client \u2022 Minecraft 1.8.9",
                4, h - 12, ThemeManager.COLOR_TEXT_MUTED);
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

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
