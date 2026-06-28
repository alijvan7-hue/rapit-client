package com.rapit.client.event.events;

/** Base class for all Rapit Client internal events. */
public abstract class RapitEvent {

    private boolean cancelled = false;

    /** Returns true if this event has been cancelled. */
    public boolean isCancelled() { return cancelled; }

    /** Cancel the event (prevents further processing). */
    public void cancel() { this.cancelled = true; }
}
