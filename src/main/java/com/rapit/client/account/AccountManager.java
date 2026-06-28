package com.rapit.client.account;

import com.rapit.client.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;
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

    // ── Session info ──────────────────────────────────────────────────────────

    public String getUsername() {
        return mc.getSession().getUsername();
    }

    public String getUUID() {
        return mc.getSession().getPlayerID();
    }

    public boolean isPremium() {
        return mc.getSession().getSessionType() == Session.Type.MOJANG;
    }

    // ── Offline username changer ──────────────────────────────────────────────

    /**
     * Changes the username for offline play.
     * Works by reflectively replacing the {@link Session} object.
     *
     * @param newName  desired username (2–16 chars, alphanumeric + underscore)
     * @return true on success
     */
    public boolean setUsername(String newName) {
        if (!newName.matches("[a-zA-Z0-9_]{2,16}")) {
            Logger.error("Invalid username: " + newName);
            return false;
        }
        if (isPremium()) {
            Logger.warn("Cannot change username on a premium account.");
            return false;
        }
        try {
            // Build a new offline Session with the desired name
            String uuid = UUID.nameUUIDFromBytes(
                    ("OfflinePlayer:" + newName).getBytes(java.nio.charset.StandardCharsets.UTF_8))
                    .toString().replace("-", "");

            Session newSession = new Session(newName, uuid, "invalid", "legacy");

            // Inject via reflection (field name is obfuscated in SRG; using MCP name)
            Field sessionField = Minecraft.class.getDeclaredField("session");
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
