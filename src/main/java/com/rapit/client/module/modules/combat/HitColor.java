package com.rapit.client.module.modules.combat;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/**
 * HitColor – changes the entity hurt color (red flash) when attacked.
 * Actual color change requires a mixin into Entity#setEntityState.
 */
public class HitColor extends Module {

    public final SliderSetting red   = addSetting(new SliderSetting("Red",   "Red value",   255, 0, 255, 1));
    public final SliderSetting green = addSetting(new SliderSetting("Green", "Green value", 255, 0, 255, 1));
    public final SliderSetting blue  = addSetting(new SliderSetting("Blue",  "Blue value",  0,   0, 255, 1));

    public HitColor() { super("HitColor", "Changes the hurt color on hit.", Category.COMBAT); }

    /** Returns the configured hurt color as packed ARGB int. */
    public int getColor() {
        return 0xFF000000
             | (red.getValue().intValue()   << 16)
             | (green.getValue().intValue() << 8)
             |  blue.getValue().intValue();
    }
}
