package com.rapit.client.module.modules.movement;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/** Speed – boosts the player's movement speed. */
public class Speed extends Module {

    private final SliderSetting speed = addSetting(
            new SliderSetting("Speed", "Movement multiplier", 1.5, 1.0, 5.0, 0.1));

    public Speed() { super("Speed", "Move faster than usual.", Category.MOVEMENT); }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (mc.thePlayer == null) return;
        boolean moving = mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
        if (!moving) return;

        double motX = mc.thePlayer.motionX;
        double motZ = mc.thePlayer.motionZ;
        double len  = Math.sqrt(motX * motX + motZ * motZ);
        if (len == 0) return;

        double factor = speed.getValue() * 0.2873;
        mc.thePlayer.motionX = (motX / len) * factor;
        mc.thePlayer.motionZ = (motZ / len) * factor;
    }
}
