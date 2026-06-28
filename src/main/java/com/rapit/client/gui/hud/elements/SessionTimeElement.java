package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class SessionTimeElement extends HUDElement {
    private final long startTime = System.currentTimeMillis();
    public SessionTimeElement(int x, int y) { super("Session", x, y, 90, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        long secs = (System.currentTimeMillis() - startTime) / 1000;
        long m = secs / 60, s = secs % 60;
        drawBoldText(String.format("Session %02d:%02d", m, s), 4, 2, ThemeManager.COLOR_WHITE);
    }
}
