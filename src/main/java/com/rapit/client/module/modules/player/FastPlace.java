package com.rapit.client.module.modules.player;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/** FastPlace – removes the delay between block placements. */
public class FastPlace extends Module {
    public FastPlace() { super("FastPlace","No delay between block placements.",Category.PLAYER); }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (mc.thePlayer != null) mc.rightClickDelayTimer = 0;
    }
}
