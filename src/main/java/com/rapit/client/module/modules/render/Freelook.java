package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/**
 * Freelook – allows the camera to rotate freely without moving the player.
 * Requires a mixin into EntityRenderer/MouseHelper to redirect camera yaw/pitch.
 */
public class Freelook extends Module {

    private float savedYaw, savedPitch;

    public Freelook() { super("Freelook", "Look around without turning.", Category.RENDER); }

    @Override
    protected void onEnable() {
        if (mc.thePlayer == null) return;
        savedYaw   = mc.thePlayer.rotationYaw;
        savedPitch = mc.thePlayer.rotationPitch;
    }

    @Override
    protected void onDisable() {
        // Restore rotations via mixin hook
    }

    public float getSavedYaw()   { return savedYaw; }
    public float getSavedPitch() { return savedPitch; }
}
