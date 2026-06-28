package com.rapit.client.module.modules.render;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/** MotionBlur – adds a smooth trailing effect to the screen. */
public class MotionBlur extends Module {

    public final SliderSetting strength = addSetting(
            new SliderSetting("Strength","Blur intensity",0.5,0.1,0.9,0.05));

    public MotionBlur() { super("MotionBlur","Screen motion blur effect.",Category.RENDER); }
}
