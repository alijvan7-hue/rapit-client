package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;

public class KeystrokesElement extends HUDElement {
    public KeystrokesElement(int x, int y) { super("Keystrokes", x, y, 60, 50); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        drawKey("W", 22, 2,  mc.gameSettings.keyBindForward.isKeyDown());
        drawKey("A", 2,  16, mc.gameSettings.keyBindLeft.isKeyDown());
        drawKey("S", 22, 16, mc.gameSettings.keyBindBack.isKeyDown());
        drawKey("D", 42, 16, mc.gameSettings.keyBindRight.isKeyDown());
        drawKey("SPC", 2, 30, mc.gameSettings.keyBindJump.isKeyDown());
    }

    private void drawKey(String label, int ox, int oy, boolean pressed) {
        int bg = pressed ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_SURFACE;
        int tc = pressed ? ThemeManager.COLOR_BACKGROUND : ThemeManager.COLOR_WHITE;
        RenderUtil.drawRoundedRect(getX() + ox, getY() + oy, 16, 12, 3, bg);
        drawBoldText(label, ox + 2, oy + 1, tc);
    }
}
