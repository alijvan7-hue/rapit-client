package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class CoordinatesElement extends HUDElement {
    public CoordinatesElement(int x, int y) { super("Coordinates", x, y, 140, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer == null) return;
        drawBackground();
        String coords = String.format("XYZ %.0f / %.0f / %.0f",
                mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        drawBoldText(coords, 4, 2, ThemeManager.COLOR_WHITE);
    }
}
