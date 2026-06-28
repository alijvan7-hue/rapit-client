package com.rapit.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/** Chat messaging helpers. */
public final class ChatUtil {

    private static final String PREFIX = "\u00A78[\u00A76Rapit\u00A78] \u00A7r";

    private ChatUtil() {}

    public static void send(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(PREFIX + message));
        }
    }

    public static void sendRaw(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }

    public static void sendChat(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && mc.thePlayer.sendQueue != null) {
            mc.thePlayer.sendChatMessage(message);
        }
    }
}
