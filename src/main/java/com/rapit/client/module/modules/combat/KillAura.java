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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KillAura – automatically attacks nearby entities.
 */
public class KillAura extends Module {

    private final SliderSetting range     = addSetting(new SliderSetting("Range","Attack range",3.5,1.0,6.0,0.1));
    private final SliderSetting cps       = addSetting(new SliderSetting("CPS","Clicks per second",10,1,20,1));
    private final ModeSetting   target    = addSetting(new ModeSetting("Target","Who to attack","Players","Players","Mobs","All"));
    private final BoolSetting   rotate    = addSetting(new BoolSetting("Rotate","Rotate to target",true,""));
    private final BoolSetting   throughWalls = addSetting(new BoolSetting("Through Walls","Attack through walls",false,""));

    private long lastAttack = 0;

    public KillAura() { super("KillAura", "Automatically attacks entities.", Category.COMBAT); }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        long delay = (long)(1000.0 / cps.getValue());
        if (System.currentTimeMillis() - lastAttack < delay) return;

        EntityLivingBase best = findTarget();
        if (best == null) return;

        if (!throughWalls.getValue()) {
            MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(
                    mc.thePlayer.getPositionEyes(1f),
                    best.getPositionEyes(1f));
            if (ray != null) return;
        }

        if (rotate.getValue()) {
            rotateToward(best);
        }

        mc.thePlayer.swingItem();
        mc.thePlayer.attackTargetEntityWithCurrentItem(best);
        lastAttack = System.currentTimeMillis();
    }

    private EntityLivingBase findTarget() {
        double r = range.getValue();
        List<Entity> entities = mc.theWorld.loadedEntityList;
        return entities.stream()
                .filter(e -> e instanceof EntityLivingBase)
                .filter(e -> e != mc.thePlayer)
                .filter(e -> matchesTarget(e))
                .filter(e -> mc.thePlayer.getDistanceToEntity(e) <= r)
                .map(e -> (EntityLivingBase) e)
                .min(Comparator.comparingDouble(e -> mc.thePlayer.getDistanceToEntity(e)))
                .orElse(null);
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
        double dy = (entity.posY + entity.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double dist = Math.sqrt(dx * dx + dz * dz);
        float yaw   = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        float pitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
        mc.thePlayer.rotationYaw   = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }
}
