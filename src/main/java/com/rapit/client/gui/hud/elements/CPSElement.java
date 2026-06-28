package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CPSElement extends HUDElement {
    private final List<Long> clicks = new ArrayList<>();
    public CPSElement(int x, int y) { super("CPS", x, y, 60, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (Mouse.isButtonDown(0)) clicks.add(System.currentTimeMillis());
        clicks.removeIf(t -> System.currentTimeMillis() - t > 1000);
        drawBackground();
        drawBoldText("CPS ", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(String.valueOf(clicks.size()), 30, 2, ThemeManager.COLOR_PRIMARY);
    }
}
