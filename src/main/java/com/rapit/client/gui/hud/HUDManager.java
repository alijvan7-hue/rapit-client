package com.rapit.client.gui.hud;

import com.rapit.client.RapitClient;
import com.rapit.client.gui.hud.elements.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all HUD elements. Each element is independently positioned
 * and rendered. The HUD editor allows drag-and-drop repositioning.
 */
public class HUDManager {

    private final List<HUDElement> elements = new ArrayList<>();
    private final Minecraft mc = Minecraft.getMinecraft();

    public HUDManager() {
        registerDefaults();
    }

    // ── Registration ──────────────────────────────────────────────────────────

    private void registerDefaults() {
        register(new FPSElement(5, 5));
        register(new CPSElement(5, 20));
        register(new PingElement(5, 35));
        register(new BPSElement(5, 50));
        register(new CoordinatesElement(5, 65));
        register(new DirectionElement(5, 80));
        register(new ArmorStatusElement(5, 95));
        register(new PotionEffectsElement(5, 110));
        register(new KeystrokesElement(200, 5));
        register(new SessionTimeElement(5, 160));
        register(new ClockElement(5, 175));
        register(new MemoryElement(5, 190));
        register(new SpeedDisplayElement(5, 205));
        register(new ComboCounterElement(200, 80));
        register(new PackDisplayElement(5, 220));
    }

    private void register(HUDElement element) {
        elements.add(element);
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    public void render() {
        if (mc.thePlayer == null) return;
        ScaledResolution sr = new ScaledResolution(mc);

        for (HUDElement element : elements) {
            if (element.isEnabled()) {
                element.render(sr);
            }
        }
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public List<HUDElement> getElements() { return elements; }

    public HUDElement getElementAt(int x, int y) {
        for (HUDElement el : elements) {
            if (x >= el.getX() && x <= el.getX() + el.getWidth()
             && y >= el.getY() && y <= el.getY() + el.getHeight()) {
                return el;
            }
        }
        return null;
    }
}
