package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class FPSElement extends HUDElement {
    public FPSElement(int x, int y) { super("FPS", x, y, 55, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        int fps = Minecraft.getDebugFPS();
        int color = fps >= 60 ? ThemeManager.COLOR_ENABLED :
                    fps >= 30 ? ThemeManager.COLOR_PRIMARY  : 0xFFFF4444;
        drawBoldText("FPS ", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(String.valueOf(fps), 28, 2, color);
    }
}
