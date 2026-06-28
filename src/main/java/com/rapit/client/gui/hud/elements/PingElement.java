package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;

public class PingElement extends HUDElement {
    public PingElement(int x, int y) { super("Ping", x, y, 70, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        int ping = 0;
        if (mc.thePlayer != null) {
            NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
            if (info != null) ping = info.getResponseTime();
        }
        int color = ping < 60 ? ThemeManager.COLOR_ENABLED :
                    ping < 150 ? ThemeManager.COLOR_PRIMARY : 0xFFFF4444;
        drawBoldText("PING ", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(ping + "ms", 34, 2, color);
    }
}
