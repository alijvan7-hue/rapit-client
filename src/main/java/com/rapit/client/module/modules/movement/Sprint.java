package com.rapit.client.module.modules.movement;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;

/**
 * Toggle Sprint – automatically sprints for the player.
 */
public class Sprint extends Module {

    private final ModeSetting mode = addSetting(new ModeSetting(
            "Mode", "Sprint behaviour",
            "Legit", "Legit", "Omni"
    ));

    public Sprint() {
        super("Sprint", "Automatically sprints for you.", Category.MOVEMENT);
    }

    @EventListener
    public void onUpdate(PlayerUpdateEvent event) {
        if (mc.thePlayer == null) return;

        boolean moving = mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
        boolean canSprint = !mc.thePlayer.isSprinting()
                         && !mc.thePlayer.isCollidedHorizontally
                         && mc.thePlayer.getFoodStats().getFoodLevel() > 6;

        if (mode.getValue().equals("Legit")) {
            if (mc.thePlayer.moveForward > 0 && canSprint) {
                mc.thePlayer.setSprinting(true);
            }
        } else {
            // Omni – sprint in all move directions
            if (moving && canSprint) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}
