package com.rapit.client.event.events;

/** Fired when the HUD overlay is rendered. */
public class RenderHUDEvent extends RapitEvent {
    private final float partialTicks;
    public RenderHUDEvent(float partialTicks) { this.partialTicks = partialTicks; }
    public float getPartialTicks() { return partialTicks; }
}
