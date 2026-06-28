package com.rapit.client.module.modules.world;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/** Timer – modifies the game's internal timer speed. */
public class Timer extends Module {
    public final SliderSetting speed = addSetting(new SliderSetting("Speed","Timer multiplier",1.0,0.1,10.0,0.1));
    public Timer() { super("Timer","Speed up or slow down game time.",Category.WORLD); }
}
