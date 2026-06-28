package com.rapit.client.util;

/** General math utilities for the client. */
public final class MathUtil {

    private MathUtil() {}

    /** Clamps {@code value} between {@code min} and {@code max}. */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Wraps angle to [-180, 180]. */
    public static float wrapAngle(float angle) {
        while (angle >  180f) angle -= 360f;
        while (angle < -180f) angle += 360f;
        return angle;
    }

    /** Returns the horizontal distance between two (x,z) positions. */
    public static double distance2D(double x1, double z1, double x2, double z2) {
        double dx = x2 - x1, dz = z2 - z1;
        return Math.sqrt(dx * dx + dz * dz);
    }
}
