package com.rapit.client.animation;

/**
 * Stateful smooth animation helper.
 * Stores current and target values and interpolates each frame.
 *
 * <pre>
 *   SmoothAnimation anim = new SmoothAnimation(0.0, 0.15);
 *   // each frame:
 *   double val = anim.getAndUpdate(targetValue);
 * </pre>
 */
public class SmoothAnimation {

    private double current;
    private final double speed;

    /**
     * @param initial  starting value
     * @param speed    lerp factor per frame (0.0–1.0); ~0.15 gives 200ms feel
     */
    public SmoothAnimation(double initial, double speed) {
        this.current = initial;
        this.speed   = speed;
    }

    /** Updates the animation toward {@code target} and returns current value. */
    public double getAndUpdate(double target) {
        current = AnimationUtil.lerp(current, target, speed);
        return current;
    }

    /** Returns the current animated value without updating. */
    public double get() { return current; }

    /** Snaps immediately to the target without interpolation. */
    public void snap(double target) { current = target; }

    /** Resets to 0. */
    public void reset() { current = 0; }
}
