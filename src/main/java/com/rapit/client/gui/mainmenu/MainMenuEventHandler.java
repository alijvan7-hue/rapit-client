package com.rapit.client.gui.mainmenu;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Intercepts the vanilla GuiMainMenu open event and replaces it
 * with our custom RapitMainMenu.
 *
 * Register via: MinecraftForge.EVENT_BUS.register(new MainMenuEventHandler());
 */
public class MainMenuEventHandler {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu && !(event.gui instanceof RapitMainMenu)) {
            event.gui = new RapitMainMenu();
        }
    }
}
