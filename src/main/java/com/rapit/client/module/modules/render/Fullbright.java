package com.rapit.client.module.modules.render;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/** Fullbright – sets gamma to maximum so everything is visible. */
public class Fullbright extends Module {

    private float prevGamma = 1.0f;

    public Fullbright() { super("Fullbright", "See in the dark.", Category.RENDER); }

    @Override
    protected void onEnable() {
        prevGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 1000f;
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.gammaSetting = prevGamma;
    }
}
