package com.rapit.client.module.modules.combat;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.BoolSetting;
import com.rapit.client.module.setting.ModeSetting;
import com.rapit.client.module.setting.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class KillAura extends Module {

    private final SliderSetting range  = addSetting(new SliderSetting("Range","Attack range",3.5,1.0,6.0,0.1));
    private final SliderSetting cps    = addSetting(new SliderSetting("CPS","Clicks per second",10,1,20,1));
    private final ModeSetting   target = addSetting(new ModeSetting("Target","Who to attack","Players","Players","Mobs","All"));
    private final BoolSetting   rotate = addSetting(new BoolSetting("Rotate","Rotate to target",true));
    private final BoolSetting   walls  = addSetting(new BoolSetting("Through Walls","Attack through walls",false));

    private long lastAttack = 0;

    public KillAura() { super("KillAura", "Automatically attacks entities.", Category.COMBAT); }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        long delay = (long)(1000.0 / cps.getValue());
        if (System.currentTimeMillis() - lastAttack < delay) return;

        EntityLivingBase best = findTarget();
        if (best == null) return;

        if (!walls.getValue()) {
            Vec3 eyes = mc.thePlayer.getPositionEyes(1f);
            Vec3 targetEyes = best.getPositionEyes(1f);
            MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(eyes, targetEyes);
            if (ray != null) return;
        }

        if (rotate.getValue()) rotateToward(best);

        mc.thePlayer.swingItem();
        mc.thePlayer.attackTargetEntityWithCurrentItem(best);
        lastAttack = System.currentTimeMillis();
    }

    private EntityLivingBase findTarget() {
        double r = range.getValue();
        EntityLivingBase closest = null;
        double closestDist = Double.MAX_VALUE;

        for (Entity e : new ArrayList<Entity>(mc.theWorld.loadedEntityList)) {
            if (!(e instanceof EntityLivingBase)) continue;
            if (e == mc.thePlayer) continue;
            if (!matchesTarget(e)) continue;
            EntityLivingBase living = (EntityLivingBase) e;
            if (living.isDead || living.getHealth() <= 0) continue;
            double dist = mc.thePlayer.getDistanceToEntity(e);
            if (dist <= r && dist < closestDist) {
                closest = living;
                closestDist = dist;
            }
        }
        return closest;
    }

    private boolean matchesTarget(Entity e) {
        switch (target.getValue()) {
            case "Players": return e instanceof EntityPlayer;
            case "Mobs":    return !(e instanceof EntityPlayer);
            default:        return true;
        }
    }

    private void rotateToward(EntityLivingBase entity) {
        double dx = entity.posX - mc.thePlayer.posX;
        double dz = entity.posZ - mc.thePlayer.posZ;
        double dy = (entity.posY + entity.getEyeHeight())
                  - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double dist = Math.sqrt(dx * dx + dz * dz);
        mc.thePlayer.rotationYaw   = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        mc.thePlayer.rotationPitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
    }
}
