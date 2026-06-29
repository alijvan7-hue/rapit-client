package com.rapit.client.account;

import com.rapit.client.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Manages the current Minecraft session.
 * Supports offline username changes without restarting the game.
 */
public class AccountManager {

    private final Minecraft mc = Minecraft.getMinecraft();

    public AccountManager() {
        Logger.info("AccountManager ready. Current user: " + getUsername());
    }

    public String getUsername() {
        return mc.getSession().getUsername();
    }

    public String getUUID() {
        return mc.getSession().getPlayerID();
    }

    public boolean isPremium() {
        Session.Type type = mc.getSession().getSessionType();
        return type == Session.Type.MOJANG || type == Session.Type.LEGACY;
    }

    /**
     * Changes the username for offline play via reflection.
     * Only works on offline/cracked accounts.
     */
    public boolean setUsername(String newName) {
        if (!newName.matches("[a-zA-Z0-9_]{2,16}")) {
            Logger.error("Invalid username: " + newName);
            return false;
        }
        try {
            String uuid = UUID.nameUUIDFromBytes(
                    ("OfflinePlayer:" + newName).getBytes(StandardCharsets.UTF_8))
                    .toString().replace("-", "");

            Session newSession = new Session(newName, uuid, "invalid", "legacy");

            // Try MCP field name first, then obfuscated fallback
            Field sessionField = null;
            for (String fieldName : new String[]{"theSession", "session", "field_71449_j"}) {
                try {
                    sessionField = Minecraft.class.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException ignored) {}
            }
            if (sessionField == null) throw new NoSuchFieldException("Cannot find session field");

            sessionField.setAccessible(true);
            sessionField.set(mc, newSession);
            Logger.info("Username changed to: " + newName);
            return true;
        } catch (Exception e) {
            Logger.error("Failed to change username: " + e.getMessage());
            return false;
        }
    }
}
