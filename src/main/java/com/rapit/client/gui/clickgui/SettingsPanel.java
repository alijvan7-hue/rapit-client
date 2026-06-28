package com.rapit.client.gui.clickgui;

import com.rapit.client.RapitClient;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.*;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;

import java.util.List;

/**
 * Renders the settings panel on the right side of the ClickGUI
 * when a module is selected (right-clicked).
 */
public class SettingsPanel {

    private double scrollOffset = 0;
    private double targetScroll = 0;
    private int    dragSetting  = -1;   // index of slider being dragged

    // ── Draw ──────────────────────────────────────────────────────────────────

    public void draw(int x, int y, int w, int h, Module module, int mx, int my) {
        FontManager fm = RapitClient.getInstance().getFontManager();

        // Panel bg
        RenderUtil.drawRect(x, y, w, h, ThemeManager.withAlpha(ThemeManager.COLOR_SURFACE, 220));
        RenderUtil.drawRect(x, y, 1, h, ThemeManager.COLOR_BORDER);

        // Title
        RenderUtil.drawRect(x, y, w, 36, ThemeManager.COLOR_SURFACE_HOVER);
        fm.drawString(FontManager.FONT_BOLD, module.getName(), x + 10, y + 10,
                ThemeManager.COLOR_PRIMARY);

        // Toggle indicator line
        RenderUtil.drawRect(x, y + 36, w, 1, ThemeManager.COLOR_BORDER);

        // Settings list
        scrollOffset += (targetScroll - scrollOffset) * 0.15;
        List<Setting<?>> settings = module.getSettings();

        if (settings.isEmpty()) {
            fm.drawString(FontManager.FONT_REGULAR, "No settings", x + 10, y + 50,
                    ThemeManager.COLOR_TEXT_MUTED);
            return;
        }

        int sy = (int)(y + 44 - scrollOffset);
        int idx = 0;
        for (Setting<?> setting : settings) {
            sy = drawSetting(setting, x + 6, sy, w - 12, mx, my, idx);
            idx++;
        }
    }

    private int drawSetting(Setting<?> s, int x, int y, int w, int mx, int my, int idx) {
        FontManager fm = RapitClient.getInstance().getFontManager();

        if (s instanceof BoolSetting) {
            BoolSetting bs = (BoolSetting) s;
            RenderUtil.drawRect(x, y, w, 26, ThemeManager.withAlpha(ThemeManager.COLOR_BACKGROUND, 100));
            fm.drawString(FontManager.FONT_REGULAR, s.getName(), x + 6, y + 7,
                    ThemeManager.COLOR_WHITE);
            // Mini toggle
            int tx = x + w - 34, ty = y + 7;
            int bg = bs.getValue() ? ThemeManager.COLOR_TOGGLE_ON : ThemeManager.COLOR_TOGGLE_OFF;
            RenderUtil.drawRoundedRect(tx, ty, 24, 12, 6, bg);
            double thumbX = bs.getValue() ? tx + 14 : tx + 2;
            RenderUtil.drawRoundedRect(thumbX, ty + 2, 8, 8, 4, ThemeManager.COLOR_WHITE);
            return y + 30;

        } else if (s instanceof SliderSetting) {
            SliderSetting ss = (SliderSetting) s;
            fm.drawString(FontManager.FONT_REGULAR, s.getName(), x + 6, y + 6,
                    ThemeManager.COLOR_WHITE);
            String valStr = String.format("%.1f", ss.getValue());
            fm.drawString(FontManager.FONT_MONO, valStr, x + w - fm.getStringWidth(FontManager.FONT_MONO, valStr) - 6,
                    y + 6, ThemeManager.COLOR_PRIMARY);

            // Track
            int trackY = y + 22, trackH = 4;
            RenderUtil.drawRoundedRect(x + 6, trackY, w - 12, trackH, 2, ThemeManager.COLOR_BORDER);
            double pct = (ss.getValue() - ss.getMin()) / (ss.getMax() - ss.getMin());
            RenderUtil.drawRoundedRect(x + 6, trackY, (int)((w - 12) * pct), trackH, 2, ThemeManager.COLOR_PRIMARY);

            // Thumb
            int thumbX = (int)(x + 6 + (w - 12) * pct - 5);
            RenderUtil.drawRoundedRect(thumbX, trackY - 3, 10, 10, 5, ThemeManager.COLOR_PRIMARY);
            return y + 36;

        } else if (s instanceof ModeSetting) {
            ModeSetting ms = (ModeSetting) s;
            fm.drawString(FontManager.FONT_REGULAR, s.getName(), x + 6, y + 7,
                    ThemeManager.COLOR_WHITE);
            // Current mode button
            String cur = ms.getValue();
            int mw = fm.getStringWidth(FontManager.FONT_REGULAR, cur) + 12;
            RenderUtil.drawRoundedRect(x + w - mw - 4, y + 4, mw, 18, 4,
                    ThemeManager.COLOR_PRIMARY);
            fm.drawString(FontManager.FONT_REGULAR, cur, x + w - mw + 2, y + 8,
                    ThemeManager.COLOR_BACKGROUND);
            return y + 30;
        }

        return y + 28;
    }

    // ── Input handlers (called from ClickGUI) ─────────────────────────────────

    public void mouseClicked(int mx, int my, int button, int panelX, int panelY,
                              int w, Module module) {
        if (module == null) return;
        FontManager fm = RapitClient.getInstance().getFontManager();
        List<Setting<?>> settings = module.getSettings();
        int sy = (int)(panelY + 44 - scrollOffset);
        for (Setting<?> s : settings) {
            if (s instanceof BoolSetting) {
                BoolSetting bs = (BoolSetting) s;
                int tx = panelX + w - 34, ty = sy + 7;
                if (mx >= tx && mx <= tx + 24 && my >= ty && my <= ty + 12) {
                    bs.toggle();
                }
                sy += 30;
            } else if (s instanceof SliderSetting) {
                sy += 36;
            } else if (s instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting) s;
                String cur = ms.getValue();
                int mw = fm.getStringWidth(FontManager.FONT_REGULAR, cur) + 12;
                int bx = panelX + w - mw - 4, by = sy + 4;
                if (mx >= bx && mx <= bx + mw && my >= by && my <= by + 18) {
                    ms.cycle();
                }
                sy += 30;
            } else {
                sy += 28;
            }
        }
    }

    public void onScroll(int amount) {
        targetScroll = Math.max(0, targetScroll - amount * 0.08);
    }
}
