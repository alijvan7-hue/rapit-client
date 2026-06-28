package com.rapit.client.animation;

/**
 * Utility class for smooth UI animations.
 */
public final class AnimationUtil {

    private AnimationUtil() {}

    public static double lerp(double a, double b, double t) {
        t = Math.max(0, Math.min(1, t));
        return a + (b - a) * t;
    }

    public static float lerp(float a, float b, float t) {
        t = Math.max(0, Math.min(1, t));
        return a + (b - a) * t;
    }

    public static double easeInOut(double t) {
        t = Math.max(0, Math.min(1, t));
        return t * t * (3 - 2 * t);
    }

    public static double easeOut(double t) {
        t = Math.max(0, Math.min(1, t));
        return 1 - (1 - t) * (1 - t);
    }

    public static double easeIn(double t) {
        t = Math.max(0, Math.min(1, t));
        return t * t;
    }

    public static float pulse(long periodMs) {
        double t = (System.currentTimeMillis() % periodMs) / (double) periodMs;
        return (float)((Math.sin(t * Math.PI * 2) + 1) / 2.0);
    }

    public static float progress(long startMs, long durationMs) {
        float p = (System.currentTimeMillis() - startMs) / (float) durationMs;
        return Math.max(0f, Math.min(1f, p));
    }

    public static int lerpColor(int c1, int c2, float t) {
        int a1 = (c1 >> 24) & 0xFF, a2 = (c2 >> 24) & 0xFF;
        int r1 = (c1 >> 16) & 0xFF, r2 = (c2 >> 16) & 0xFF;
        int g1 = (c1 >>  8) & 0xFF, g2 = (c2 >>  8) & 0xFF;
        int b1 =  c1        & 0xFF, b2 =  c2        & 0xFF;
        int a = (int)(a1 + (a2 - a1) * t);
        int r = (int)(r1 + (r2 - r1) * t);
        int g = (int)(g1 + (g2 - g1) * t);
        int b = (int)(b1 + (b2 - b1) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
