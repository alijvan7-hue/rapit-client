package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/** BlockOverlay – draws a colored outline when looking at a block. */
public class BlockOverlay extends Module {

    public final SliderSetting red   = addSetting(new SliderSetting("Red",   "R",255,0,255,1));
    public final SliderSetting green = addSetting(new SliderSetting("Green", "G",215,0,255,1));
    public final SliderSetting blue  = addSetting(new SliderSetting("Blue",  "B",0,  0,255,1));
    public final SliderSetting alpha = addSetting(new SliderSetting("Alpha", "A",200,0,255,1));
    public final SliderSetting width = addSetting(new SliderSetting("Width","Line width",2.0,0.5,5.0,0.5));

    public BlockOverlay() { super("BlockOverlay","Custom block outline color.",Category.RENDER); }

    public int getColor() {
        return ((alpha.getValue().intValue() & 0xFF) << 24)
             | ((red.getValue().intValue()   & 0xFF) << 16)
             | ((green.getValue().intValue() & 0xFF) << 8)
             |  (blue.getValue().intValue()  & 0xFF);
    }
}
