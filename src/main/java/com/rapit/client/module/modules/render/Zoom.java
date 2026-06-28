package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/**
 * Zoom – reduces FOV for a sniper-like zoom effect.
 * The FOV override is applied via a mixin hook into EntityRenderer.
 */
public class Zoom extends Module {

    public final SliderSetting zoomLevel = addSetting(
            new SliderSetting("Zoom Level", "How much to zoom", 4.0, 1.0, 10.0, 0.5));

    public Zoom() { super("Zoom", "Zoom in like a telescope.", Category.RENDER); }

    /** Returns FOV divisor used in EntityRenderer mixin. */
    public float getFovDivisor() {
        return isEnabled() ? zoomLevel.getValue().floatValue() : 1f;
    }
}
