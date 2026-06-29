package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.ResourcePackRepository;

import java.util.List;

public class PackDisplayElement extends HUDElement {

    public PackDisplayElement(int x, int y) { super("Pack", x, y, 130, 14); }

    @Override
    public void render(ScaledResolution sr) {
        drawBackground();
        String pack = "Default";
        try {
            List<ResourcePackRepository.Entry> entries =
                    mc.getResourcePackRepository().getRepositoryEntries();
            if (!entries.isEmpty()) {
                pack = entries.get(entries.size() - 1).getResourcePackName();
                if (pack.length() > 15) pack = pack.substring(0, 12) + "...";
            }
        } catch (Exception ignored) {}
        drawBoldText("\u25A6 " + pack, 4, 2, ThemeManager.COLOR_WHITE);
    }
}
