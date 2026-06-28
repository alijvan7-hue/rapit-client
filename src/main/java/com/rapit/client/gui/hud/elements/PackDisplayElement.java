package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.IResourcePack;

import java.util.List;

public class PackDisplayElement extends HUDElement {
    public PackDisplayElement(int x, int y) { super("Pack", x, y, 130, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        List<IResourcePack> packs = mc.defaultResourcePacks;
        // Try to get active non-default pack
        String pack = "Default";
        try {
            List<?> repoPacks = mc.getResourcePackRepository().getRepositoryEntries();
            if (!repoPacks.isEmpty()) {
                pack = repoPacks.get(repoPacks.size() - 1).toString();
                if (pack.length() > 16) pack = pack.substring(0, 13) + "...";
            }
        } catch (Exception ignored) {}
        drawBoldText("\u25A6 " + pack, 4, 2, ThemeManager.COLOR_WHITE);
    }
}
