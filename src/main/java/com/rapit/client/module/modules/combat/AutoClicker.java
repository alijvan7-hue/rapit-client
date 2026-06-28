package com.rapit.client.module.modules.combat;

import com.rapit.client.event.bus.EventListener;
import com.rapit.client.event.events.PlayerUpdateEvent;
import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {

    private final SliderSetting cps = addSetting(
            new SliderSetting("CPS", "Clicks per second", 12, 1, 20, 1));

    private long lastClick = 0;

    public AutoClicker() { super("AutoClicker", "Automatically clicks for you.", Category.COMBAT); }

    @EventListener
    public void onUpdate(PlayerUpdateEvent e) {
        if (!Mouse.isButtonDown(0)) return;
        long delay = (long)(1000.0 / cps.getValue());
        if (System.currentTimeMillis() - lastClick >= delay) {
            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            lastClick = System.currentTimeMillis();
        }
    }
}
