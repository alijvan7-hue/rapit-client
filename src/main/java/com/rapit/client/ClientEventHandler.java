package com.rapit.client;

import com.rapit.client.command.CommandManager;
import com.rapit.client.event.events.*;
import com.rapit.client.gui.clickgui.ClickGUI;
import com.rapit.client.gui.hud.HUDEditor;
import com.rapit.client.gui.mainmenu.RapitMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiMainMenu;

/**
 * Global Forge event bridge.
 * Translates Forge events into Rapit's internal bus + handles global keys.
 */
public class ClientEventHandler {

    private final Minecraft mc = Minecraft.getMinecraft();

    // ── Main Menu Replacement ─────────────────────────────────────────────────

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu && !(event.gui instanceof RapitMainMenu)) {
            event.gui = new RapitMainMenu();
        }
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;
        RapitClient.getInstance().getEventBus().post(new PlayerUpdateEvent());
    }

    // ── Keyboard ──────────────────────────────────────────────────────────────

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        int key = Keyboard.getEventKey();
        if (!Keyboard.getEventKeyState()) return;

        // RSHIFT → ClickGUI
        if (key == Keyboard.KEY_RSHIFT) {
            if (mc.currentScreen instanceof ClickGUI) {
                mc.displayGuiScreen(null);
            } else {
                mc.displayGuiScreen(RapitClient.getInstance().getClickGUI());
            }
            return;
        }

        // RCONTROL → HUD Editor
        if (key == Keyboard.KEY_RCONTROL) {
            if (mc.currentScreen instanceof HUDEditor) {
                mc.displayGuiScreen(null);
            } else {
                mc.displayGuiScreen(new HUDEditor());
            }
            return;
        }

        // Module keybinds
        RapitClient.getInstance().getModuleManager().onKey(key);
    }

    // ── Chat Commands ─────────────────────────────────────────────────────────

    @SubscribeEvent
    public void onChatSent(net.minecraftforge.client.event.ClientChatEvent event) {
        if (RapitClient.getInstance().getCommandManager().handleMessage(event.message)) {
            event.setCanceled(true);
        }
    }

    // ── HUD Rendering ─────────────────────────────────────────────────────────

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (mc.thePlayer == null) return;
        RapitClient.getInstance().getHUDManager().render();
        RapitClient.getInstance().getEventBus().post(new RenderHUDEvent(event.partialTicks));
    }

    // ── World Rendering ───────────────────────────────────────────────────────

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        RapitClient.getInstance().getEventBus().post(new RenderWorldEvent(event.partialTicks));
    }
}
