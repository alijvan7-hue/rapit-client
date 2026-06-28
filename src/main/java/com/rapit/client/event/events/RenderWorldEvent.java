package com.rapit.client.event.events;

/** Fired after the world finishes rendering (RenderWorldLast). */
public class RenderWorldEvent extends RapitEvent {
    private final float partialTicks;
    public RenderWorldEvent(float partialTicks) { this.partialTicks = partialTicks; }
    public float getPartialTicks() { return partialTicks; }
}
