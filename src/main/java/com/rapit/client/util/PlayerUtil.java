package com.rapit.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/** Player-related utility methods. */
public final class PlayerUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private PlayerUtil() {}

    /** Returns the player's eye position as a Vec3. */
    public static Vec3 getEyePos() {
        return new Vec3(
            mc.thePlayer.posX,
            mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
            mc.thePlayer.posZ
        );
    }

    /** Returns the horizontal speed (blocks per tick). */
    public static double getHorizontalSpeed() {
        double motX = mc.thePlayer.motionX;
        double motZ = mc.thePlayer.motionZ;
        return Math.sqrt(motX * motX + motZ * motZ);
    }

    /** Returns true if the player is moving in any direction. */
    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    /** Returns true if the player can see the sky at their position. */
    public static boolean canSeeSky() {
        if (mc.theWorld == null || mc.thePlayer == null) return false;
        return mc.theWorld.canSeeSky(
            (int) mc.thePlayer.posX,
            (int)(mc.thePlayer.posY + mc.thePlayer.getEyeHeight()),
            (int) mc.thePlayer.posZ
        );
    }
}
