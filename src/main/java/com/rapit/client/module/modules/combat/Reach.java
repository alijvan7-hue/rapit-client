package com.rapit.client.module.modules.combat;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.SliderSetting;

/**
 * Reach – extends the player's attack reach.
 * Actual reach modification requires a mixin into EntityRenderer or
 * GameSettings.  This module stores the setting for config persistence.
 */
public class Reach extends Module {

    public final SliderSetting reach = addSetting(
            new SliderSetting("Reach", "Attack reach distance", 3.5, 3.0, 6.0, 0.1));

    public Reach() { super("Reach", "Increases attack reach distance.", Category.COMBAT); }
}
