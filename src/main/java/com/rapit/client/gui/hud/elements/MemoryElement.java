package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class MemoryElement extends HUDElement {
    public MemoryElement(int x, int y) { super("Memory", x, y, 100, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        long used  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        long total = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        drawBoldText("RAM " + used + "/" + total + "MB", 4, 2, ThemeManager.COLOR_WHITE);
    }
}
