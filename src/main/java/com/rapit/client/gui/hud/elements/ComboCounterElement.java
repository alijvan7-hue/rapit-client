package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class ComboCounterElement extends HUDElement {
    private int combo = 0;
    public ComboCounterElement(int x, int y) { super("Combo", x, y, 70, 14); }

    public void incrementCombo() { combo++; }
    public void resetCombo()     { combo = 0; }

    @Override
    public void render(ScaledResolution sr) {
        if (combo == 0) return;
        drawBackground();
        drawBoldText("Combo x" + combo, 4, 2, ThemeManager.COLOR_PRIMARY);
    }
}
