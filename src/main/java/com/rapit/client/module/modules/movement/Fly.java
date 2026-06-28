package com.rapit.client.module.modules.movement;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;
import com.rapit.client.module.setting.SliderSetting;

/** Fly – allows the player to fly in survival. */
public class Fly extends Module {

    private final ModeSetting mode  = addSetting(new ModeSetting("Mode","Fly mode","Vanilla","Vanilla","Boost"));
    private final SliderSetting speed = addSetting(new SliderSetting("Speed","Fly speed",1.0,0.1,10.0,0.1));

    public Fly() { super("Fly", "Fly around in survival mode.", Category.MOVEMENT); }

    @Override protected void onEnable() {
        if (mc.thePlayer != null) mc.thePlayer.capabilities.isFlying = true;
    }

    @Override protected void onDisable() {
        if (mc.thePlayer != null && !mc.thePlayer.capabilities.isCreativeMode)
            mc.thePlayer.capabilities.isFlying = false;
    }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (mc.thePlayer == null) return;
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.flySpeed  = speed.getValue().floatValue() * 0.05f;
    }
}
