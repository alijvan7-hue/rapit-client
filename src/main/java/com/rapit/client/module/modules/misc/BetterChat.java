package com.rapit.client.module.modules.misc;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.BoolSetting;

public class BetterChat extends Module {
    public final BoolSetting copy   = addSetting(new BoolSetting("Copy on Click", "Click to copy messages", true));
    public final BoolSetting filter = addSetting(new BoolSetting("Filter Spam",   "Hide duplicate messages", true));
    public BetterChat() { super("BetterChat", "Chat improvements.", Category.MISC); }
}
