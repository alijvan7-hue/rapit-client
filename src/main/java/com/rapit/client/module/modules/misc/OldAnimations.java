package com.rapit.client.module.modules.misc;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.BoolSetting;

/** OldAnimations – restores pre-1.9 sword/blocking animations. */
public class OldAnimations extends Module {
    public final BoolSetting blocking = addSetting(new BoolSetting("Old Blocking","Show old blocking anim",true,""));
    public final BoolSetting hitting  = addSetting(new BoolSetting("Old Hitting", "Show old hit animation", true,""));
    public OldAnimations() { super("OldAnimations","Classic pre-1.9 animations.",Category.MISC); }
}
