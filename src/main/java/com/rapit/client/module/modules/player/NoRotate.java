package com.rapit.client.module.modules.player;

import com.rapit.client.module.Category;
import com.rapit.client.module.Module;

/** NoRotate – prevents server from changing the player's rotation. */
public class NoRotate extends Module {
    public NoRotate() { super("NoRotate","Stop server-side rotation changes.",Category.PLAYER); }
}
