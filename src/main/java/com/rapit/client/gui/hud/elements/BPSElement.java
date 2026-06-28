package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

public class BPSElement extends HUDElement {
    private double lastX, lastZ;
    private double bps;
    private long   lastTime = System.currentTimeMillis();

    public BPSElement(int x, int y) { super("BPS", x, y, 70, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer != null) {
            long now = System.currentTimeMillis();
            long dt  = now - lastTime;
            if (dt >= 50) {
                double dx = mc.thePlayer.posX - lastX;
                double dz = mc.thePlayer.posZ - lastZ;
                bps = Math.sqrt(dx*dx + dz*dz) / (dt / 1000.0);
                lastX = mc.thePlayer.posX; lastZ = mc.thePlayer.posZ;
                lastTime = now;
            }
        }
        drawBackground();
        drawBoldText("BPS ", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(String.format("%.1f", bps), 28, 2, ThemeManager.COLOR_PRIMARY);
    }
}
