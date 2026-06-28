package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class SpeedDisplayElement extends HUDElement {
    public SpeedDisplayElement(int x, int y) { super("Speed", x, y, 80, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer == null) return;
        drawBackground();
        double motX = mc.thePlayer.motionX, motZ = mc.thePlayer.motionZ;
        double spd  = Math.sqrt(motX*motX + motZ*motZ);
        drawBoldText(String.format("SPD %.2f", spd), 4, 2, ThemeManager.COLOR_PRIMARY);
    }
}
