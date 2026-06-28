package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumFacing;

public class DirectionElement extends HUDElement {
    public DirectionElement(int x, int y) { super("Direction", x, y, 80, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer == null) return;
        drawBackground();
        EnumFacing facing = mc.thePlayer.getHorizontalFacing();
        drawBoldText("Facing ", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(facing.name(), 44, 2, ThemeManager.COLOR_PRIMARY);
    }
}
