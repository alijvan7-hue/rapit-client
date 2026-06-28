package com.rapit.client.module.modules.movement;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.MotionEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/** NoFall – prevents fall damage by spoofing onGround. */
public class NoFall extends Module {

    public NoFall() { super("NoFall", "Prevents fall damage.", Category.MOVEMENT); }

    @EventListener
    public void onMotion(MotionEvent event) {
        if (event.getStage() != MotionEvent.Stage.PRE) return;
        if (mc.thePlayer == null) return;
        if (mc.thePlayer.fallDistance > 2.5f) {
            event.setOnGround(true);
        }
    }
}
