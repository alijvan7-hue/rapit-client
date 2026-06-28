package com.rapit.client.gui.clickgui;

import com.rapit.client.RapitClient;
import com.rapit.client.animation.AnimationUtil;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.render.RenderUtil;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.List;

/**
 * Rapit Client ClickGUI - Module selection and settings interface.
 * Open with RSHIFT. Left-click to toggle, Right-click for settings.
 */
public class ClickGUI extends GuiScreen {

    // Layout
    private static final int NAV_WIDTH     = 130;
    private static final int HEADER_HEIGHT = 44;
    private static final int CARD_W        = 140;
    private static final int CARD_H        = 60;
    private static final int CARD_PAD      = 8;
    private static final int COLS          = 3;

    // State
    private Category selectedCategory = Category.COMBAT;
    private Module   selectedModule   = null;
    private String   searchQuery      = "";
    private boolean  searchFocused    = false;
    private double   openAnimation    = 0.0;
    private double   scrollOffset     = 0.0;
    private double   targetScroll     = 0.0;

    private SettingsPanel settingsPanel;

    @Override
    public void initGui() {
        openAnimation = 0.0;
        settingsPanel = new SettingsPanel();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        int sw = sr.getScaledWidth();
        int sh = sr.getScaledHeight();

        openAnimation = AnimationUtil.lerp(openAnimation, 1.0, 0.12);
        float anim = (float) AnimationUtil.easeOut(openAnimation);

        // Dimmed background
        RenderUtil.drawRect(0, 0, sw, sh, ThemeManager.withAlpha(0x000000, (int)(150 * anim)));

        // Panel geometry
        boolean showSettings = selectedModule != null;
        int panelW = NAV_WIDTH + COLS * (CARD_W + CARD_PAD) + CARD_PAD + (showSettings ? 200 : 0);
        int panelH = (int)(sh * 0.80);
        int panelX = (sw - panelW) / 2;
        int panelY = (sh - panelH) / 2;

        // Animated scale from center
        double cx = panelX + panelW / 2.0;
        double cy = panelY + panelH / 2.0;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)cx, (float)cy, 0);
        GL11.glScalef(anim, anim, 1f);
        GL11.glTranslatef(-(float)cx, -(float)cy, 0);

        // Panel
        RenderUtil.drawRoundedRect(panelX, panelY, panelW, panelH, ThemeManager.CORNER_RADIUS,
                ThemeManager.COLOR_BACKGROUND);
        drawHeader(panelX, panelY, panelW, mouseX, mouseY);
        drawNavSidebar(panelX, panelY + HEADER_HEIGHT, panelH - HEADER_HEIGHT, mouseX, mouseY);

        // Module cards with scissor
        int cardsX = panelX + NAV_WIDTH;
        int cardsY = panelY + HEADER_HEIGHT;
        int cardsW = panelW - NAV_WIDTH - (showSettings ? 200 : 0);
        int cardsH = panelH - HEADER_HEIGHT;

        RenderUtil.beginScissor(cardsX, cardsY, cardsW, cardsH, sr.getScaleFactor());
        drawModuleCards(cardsX, cardsY, mouseX, mouseY);
        RenderUtil.endScissor();

        // Divider
        RenderUtil.drawRect(cardsX, panelY + HEADER_HEIGHT, 1, cardsH, ThemeManager.COLOR_BORDER);

        // Settings panel
        if (showSettings) {
            settingsPanel.draw(panelX + panelW - 200, cardsY, 200, cardsH, selectedModule, mouseX, mouseY);
        }

        // Glow
        RenderUtil.drawGlow(panelX, panelY, panelW, panelH, ThemeManager.COLOR_GLOW, 5);

        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawHeader(int x, int y, int w, int mx, int my) {
        RenderUtil.drawRect(x, y, w, HEADER_HEIGHT, ThemeManager.COLOR_SURFACE);
        RenderUtil.drawRect(x, y + HEADER_HEIGHT - 1, w, 1, ThemeManager.COLOR_BORDER);

        FontManager fm = RapitClient.getInstance().getFontManager();
        fm.drawString(FontManager.FONT_BOLD,    "RAPIT",   x + 14, y + 13, ThemeManager.COLOR_PRIMARY);
        fm.drawString(FontManager.FONT_REGULAR, " CLIENT", x + 14 + 40, y + 13, ThemeManager.COLOR_WHITE);

        // Search box
        int sx = x + w - 175, sy = y + 11, sW = 160, sH = 20;
        RenderUtil.drawRoundedRect(sx, sy, sW, sH, 4, ThemeManager.COLOR_BORDER);
        String display = (searchQuery.isEmpty() && !searchFocused) ? "Search..." : searchQuery + (searchFocused ? "|" : "");
        int dc = searchQuery.isEmpty() ? ThemeManager.COLOR_TEXT_MUTED : ThemeManager.COLOR_WHITE;
        fm.drawString(FontManager.FONT_REGULAR, display, sx + 6, sy + 4, dc);
    }

    private void drawNavSidebar(int x, int y, int h, int mx, int my) {
        RenderUtil.drawRect(x, y, NAV_WIDTH, h, ThemeManager.COLOR_SURFACE);
        FontManager fm = RapitClient.getInstance().getFontManager();
        int itemH = 36, idx = 0;
        for (Category cat : Category.values()) {
            int iy = y + idx * itemH + 6;
            boolean hover    = mx >= x && mx <= x + NAV_WIDTH && my >= iy && my <= iy + itemH;
            boolean selected = cat == selectedCategory;
            if (selected) {
                RenderUtil.drawRect(x, iy, NAV_WIDTH, itemH, ThemeManager.COLOR_SURFACE_HOVER);
                RenderUtil.drawRect(x, iy, 3, itemH, ThemeManager.COLOR_PRIMARY);
            } else if (hover) {
                RenderUtil.drawRect(x, iy, NAV_WIDTH, itemH, ThemeManager.withAlpha(ThemeManager.COLOR_SURFACE_HOVER, 80));
            }
            int tc = selected ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_WHITE;
            fm.drawString(FontManager.FONT_REGULAR, cat.getDisplayName(), x + 12, iy + 10, tc);
            idx++;
        }
    }

    private void drawModuleCards(int x, int y, int mx, int my) {
        scrollOffset += (targetScroll - scrollOffset) * 0.15;
        FontManager fm = RapitClient.getInstance().getFontManager();

        List<Module> modules = searchQuery.isEmpty()
                ? RapitClient.getInstance().getModuleManager().getByCategory(selectedCategory)
                : RapitClient.getInstance().getModuleManager().search(searchQuery);

        int col = 0, startX = x + CARD_PAD, curY = (int)(y + CARD_PAD - scrollOffset);
        for (Module module : modules) {
            int cx = startX + col * (CARD_W + CARD_PAD);
            boolean hover    = mx >= cx && mx <= cx + CARD_W && my >= curY && my <= curY + CARD_H;
            boolean selct    = module == selectedModule;
            double  ta       = module.getToggleAnimation(1f);

            int bgColor = (hover || selct) ? ThemeManager.COLOR_SURFACE_HOVER : ThemeManager.COLOR_SURFACE;
            RenderUtil.drawRoundedRect(cx, curY, CARD_W, CARD_H, ThemeManager.CORNER_RADIUS, bgColor);

            if (module.isEnabled()) {
                RenderUtil.drawRect(cx, curY + 6, 3, CARD_H - 12, ThemeManager.COLOR_PRIMARY);
            }

            fm.drawString(FontManager.FONT_BOLD, module.getName(), cx + 10, curY + 8,
                    module.isEnabled() ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_WHITE);

            String desc = module.getDescription();
            if (desc.length() > 22) desc = desc.substring(0, 19) + "...";
            fm.drawString(FontManager.FONT_REGULAR, desc, cx + 10, curY + 24, ThemeManager.COLOR_TEXT_MUTED);

            drawToggle(cx + CARD_W - 36, curY + CARD_H - 22, ta);

            RenderUtil.drawBorder(cx, curY, CARD_W, CARD_H,
                    selct ? ThemeManager.COLOR_PRIMARY : ThemeManager.COLOR_BORDER);

            col++;
            if (col >= COLS) { col = 0; curY += CARD_H + CARD_PAD; }
        }
    }

    private void drawToggle(int x, int y, double anim) {
        int tw = 28, th = 14;
        int bg = AnimationUtil.lerpColor(ThemeManager.COLOR_TOGGLE_OFF, ThemeManager.COLOR_TOGGLE_ON, (float)anim);
        RenderUtil.drawRoundedRect(x, y, tw, th, th / 2.0, bg);
        double tx = x + 2 + anim * (tw - th + 2);
        RenderUtil.drawRoundedRect(tx, y + 2, th - 4, th - 4, (th - 4) / 2.0, ThemeManager.COLOR_WHITE);
    }

    @Override
    protected void mouseClicked(int mx, int my, int button) throws IOException {
        super.mouseClicked(mx, my, button);
        ScaledResolution sr = new ScaledResolution(mc);
        int sw = sr.getScaledWidth(), sh = sr.getScaledHeight();
        boolean showSettings = selectedModule != null;
        int panelW = NAV_WIDTH + COLS * (CARD_W + CARD_PAD) + CARD_PAD + (showSettings ? 200 : 0);
        int panelH = (int)(sh * 0.80);
        int panelX = (sw - panelW) / 2;
        int panelY = (sh - panelH) / 2;

        // Search
        int sx = panelX + panelW - 175, sy = panelY + 11;
        searchFocused = mx >= sx && mx <= sx + 160 && my >= sy && my <= sy + 20;

        // Nav
        int navY = panelY + HEADER_HEIGHT, itemH = 36, idx = 0;
        for (Category cat : Category.values()) {
            int iy = navY + idx * itemH + 6;
            if (mx >= panelX && mx <= panelX + NAV_WIDTH && my >= iy && my <= iy + itemH) {
                selectedCategory = cat; selectedModule = null; targetScroll = 0; return;
            }
            idx++;
        }

        // Cards
        int cardsX = panelX + NAV_WIDTH + CARD_PAD;
        int cardsY = (int)(panelY + HEADER_HEIGHT + CARD_PAD - scrollOffset);
        List<Module> modules = searchQuery.isEmpty()
                ? RapitClient.getInstance().getModuleManager().getByCategory(selectedCategory)
                : RapitClient.getInstance().getModuleManager().search(searchQuery);

        int col = 0, curY = cardsY;
        for (Module module : modules) {
            int cx = cardsX + col * (CARD_W + CARD_PAD);
            if (mx >= cx && mx <= cx + CARD_W && my >= curY && my <= curY + CARD_H) {
                if (button == 0) module.toggle();
                else if (button == 1) selectedModule = (selectedModule == module) ? null : module;
                return;
            }
            col++;
            if (col >= COLS) { col = 0; curY += CARD_H + CARD_PAD; }
        }
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) { mc.displayGuiScreen(null); return; }
        if (searchFocused) {
            if (key == 14 && !searchQuery.isEmpty()) searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
            else if (c >= 32) searchQuery += c;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) targetScroll = Math.max(0, targetScroll - scroll * 0.08);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
