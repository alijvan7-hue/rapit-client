package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockElement extends HUDElement {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    public ClockElement(int x, int y) { super("Clock", x, y, 80, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        drawBoldText(LocalTime.now().format(FMT), 4, 2, ThemeManager.COLOR_PRIMARY);
    }
}
