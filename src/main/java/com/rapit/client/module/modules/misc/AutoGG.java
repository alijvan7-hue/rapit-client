package com.rapit.client.module.modules.misc;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;

/** AutoGG – sends a configurable message at the end of a game. */
public class AutoGG extends Module {
    public final ModeSetting msg = addSetting(new ModeSetting("Message","GG message","gg","gg","GG","Good Game","gg ez"));
    public AutoGG() { super("AutoGG","Auto-send GG at end of game.",Category.MISC); }
}
