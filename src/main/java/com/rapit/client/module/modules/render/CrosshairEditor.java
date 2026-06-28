package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;
import com.rapit.client.module.setting.SliderSetting;

/** CrosshairEditor – replaces the default crosshair with a custom one. */
public class CrosshairEditor extends Module {

    public final ModeSetting  style = addSetting(new ModeSetting("Style","Crosshair shape","Cross","Cross","Dot","Circle","Plus"));
    public final SliderSetting size  = addSetting(new SliderSetting("Size","Crosshair size",5,1,20,1));
    public final SliderSetting red   = addSetting(new SliderSetting("Red",  "R",255,0,255,1));
    public final SliderSetting green = addSetting(new SliderSetting("Green","G",255,0,255,1));
    public final SliderSetting blue  = addSetting(new SliderSetting("Blue", "B",255,0,255,1));

    public CrosshairEditor() { super("CrosshairEditor","Custom crosshair.",Category.RENDER); }

    public int getColor() {
        return 0xFF000000
             | (red.getValue().intValue()   << 16)
             | (green.getValue().intValue() << 8)
             |  blue.getValue().intValue();
    }
}
