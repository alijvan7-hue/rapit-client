package com.rapit.client.module.modules.movement;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/** Toggle Sneak – keeps the player sneaking automatically. */
public class Sneak extends Module {

    public Sneak() {
        super("Sneak", "Automatically sneaks for you.", Category.MOVEMENT);
    }

    @EventListener
    public void onUpdate(PlayerUpdateEvent event) {
        if (mc.thePlayer != null) {
            mc.gameSettings.keyBindSneak.pressed = true;
        }
    }

    @Override
    protected void onDisable() {
        if (mc.thePlayer != null) {
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }
}
