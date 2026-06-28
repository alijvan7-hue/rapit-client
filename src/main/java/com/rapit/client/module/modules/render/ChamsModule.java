package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;

/** Chams – renders entities through walls with a custom color. */
public class ChamsModule extends Module {
    public final ModeSetting mode = addSetting(new ModeSetting("Mode","Chams style","Flat","Flat","Textured","Wireframe"));
    public ChamsModule() { super("Chams","See entities through walls.",Category.RENDER); }
}
