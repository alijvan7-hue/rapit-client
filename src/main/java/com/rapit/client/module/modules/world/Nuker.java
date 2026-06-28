package com.rapit.client.module.modules.world;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/** Nuker – breaks blocks around the player automatically. */
public class Nuker extends Module {
    public final SliderSetting range = addSetting(new SliderSetting("Range","Break radius",3,1,5,0.5));
    public Nuker() { super("Nuker","Auto-break nearby blocks.",Category.WORLD); }
}
