package com.rapit.client.gui.hud;

import com.rapit.client.RapitClient;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.List;

/**
 * HUD Editor – lets the player drag HUD elements to custom positions.
 * Open with Right Control. Each element shows a labeled box.
 */
public class HUDEditor extends GuiScreen {

    private HUDElement dragging   = null;
    private int        dragOffsetX = 0;
    private int        dragOffsetY = 0;
    private HUDElement hovering   = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        FontManager fm = RapitClient.getInstance().getFontManager();

        // Dim background
        RenderUtil.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(),
                ThemeManager.withAlpha(0x000000, 130));

        // Title
        fm.drawString(FontManager.FONT_BOLD, "HUD EDITOR",
                sr.getScaledWidth() / 2 - 40, 8, ThemeManager.COLOR_PRIMARY);
        fm.drawString(FontManager.FONT_REGULAR, "Drag elements to reposition  |  Right-click to toggle  |  ESC to close",
                sr.getScaledWidth() / 2 - 130, 22, ThemeManager.COLOR_TEXT_MUTED);

        HUDManager hm = RapitClient.getInstance().getHUDManager();
        hovering = hm.getElementAt(mouseX, mouseY);

        // Update dragged element position
        if (dragging != null) {
            dragging.setPosition(mouseX - dragOffsetX, mouseY - dragOffsetY);
        }

        // Draw all elements with their outlines
        for (HUDElement el : hm.getElements()) {
            // Render the element itself
            el.render(sr);

            // Draw editor outline
            boolean isHovered = el == hovering || el == dragging;
            el.drawEditorOutline(isHovered);

            // Label
            int labelColor = el.isEnabled() ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_TEXT_MUTED;
            fm.drawString(FontManager.FONT_REGULAR, el.getName(),
                    el.getX(), el.getY() - 12, labelColor);
        }

        // Corner info
        fm.drawString(FontManager.FONT_REGULAR, "Rapit Client v" + RapitClient.VERSION,
                4, sr.getScaledHeight() - 12, ThemeManager.COLOR_TEXT_MUTED);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        HUDManager hm = RapitClient.getInstance().getHUDManager();
        HUDElement target = hm.getElementAt(mouseX, mouseY);

        if (button == 0 && target != null) {
            dragging    = target;
            dragOffsetX = mouseX - target.getX();
            dragOffsetY = mouseY - target.getY();
        } else if (button == 1 && target != null) {
            target.setEnabled(!target.isEnabled());
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        if (button == 0) dragging = null;
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) {
            mc.displayGuiScreen(null);
            // Auto-save positions
            RapitClient.getInstance().getConfigManager().saveDefault();
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
