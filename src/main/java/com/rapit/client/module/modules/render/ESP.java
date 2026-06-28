package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;

/** ESP – draws boxes around entities. */
public class ESP extends Module {
    public final ModeSetting mode = addSetting(new ModeSetting("Mode","ESP mode","2D Box","2D Box","3D Box","Corner"));
    public ESP() { super("ESP","Highlight nearby entities.",Category.RENDER); }
}
