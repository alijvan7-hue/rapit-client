package com.rapit.client.util;

/** Simple millisecond timer used by modules for cooldowns. */
public class TimerUtil {

    private long lastTime = 0;

    /** Returns true if {@code ms} milliseconds have elapsed since the last reset. */
    public boolean elapsed(long ms) {
        return System.currentTimeMillis() - lastTime >= ms;
    }

    /** Resets the timer to now. */
    public void reset() {
        lastTime = System.currentTimeMillis();
    }

    /** Time in ms since last reset. */
    public long getElapsed() {
        return System.currentTimeMillis() - lastTime;
    }
}
