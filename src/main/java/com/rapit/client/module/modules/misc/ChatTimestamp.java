package com.rapit.client.module.modules.misc;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.ModeSetting;

/** ChatTimestamp – prepends a timestamp to every chat message. */
public class ChatTimestamp extends Module {
    public final ModeSetting format = addSetting(new ModeSetting("Format","Time format","HH:mm","HH:mm","HH:mm:ss","hh:mm a"));
    public ChatTimestamp() { super("ChatTimestamp","Show time on chat messages.",Category.MISC); }
}
