package com.rapit.client.module;

import com.rapit.client.RapitClient;
import com.rapit.client.animation.AnimationUtil;
import com.rapit.client.module.setting.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for every Rapit Client module (feature/cheat/utility).
 *
 * <p>Subclasses override {@link #onEnable()}, {@link #onDisable()},
 * and register event listeners via {@link #onEnable()} / {@link #onDisable()}.
 */
public abstract class Module {

    // ── Fields ────────────────────────────────────────────────────────────────

    protected final Minecraft mc = Minecraft.getMinecraft();

    private final String   name;
    private final String   description;
    private final Category category;

    private boolean enabled   = false;
    private boolean favourite = false;
    private int     keybind   = 0; // LWJGL key code, 0 = none

    /** Visual toggle animation (0.0 → 1.0) */
    private double toggleAnimation = 0.0;

    /** Ordered list of all settings belonging to this module */
    private final List<Setting<?>> settings = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────────────────

    protected Module(String name, String description, Category category) {
        this.name        = name;
        this.description = description;
        this.category    = category;
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    /** Called when the module is toggled ON. Register event listeners here. */
    protected void onEnable() {}

    /** Called when the module is toggled OFF. Unregister event listeners here. */
    protected void onDisable() {}

    // ── Toggle ────────────────────────────────────────────────────────────────

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean state) {
        if (this.enabled == state) return;
        this.enabled = state;

        if (state) {
            RapitClient.getInstance().getEventBus().register(this);
            onEnable();
        } else {
            RapitClient.getInstance().getEventBus().unregister(this);
            onDisable();
        }
    }

    // ── Settings helpers ──────────────────────────────────────────────────────

    /** Registers a setting and returns it for field assignment chaining. */
    protected <T extends Setting<?>> T addSetting(T setting) {
        settings.add(setting);
        return setting;
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    /**
     * Updates and returns the smooth toggle animation value (0 = off, 1 = on).
     * Call each frame from the ClickGUI renderer.
     */
    public double getToggleAnimation(float partialTicks) {
        double target = enabled ? 1.0 : 0.0;
        toggleAnimation = AnimationUtil.lerp(toggleAnimation, target, 0.15);
        return toggleAnimation;
    }

    // ── Getters/Setters ───────────────────────────────────────────────────────

    public String      getName()        { return name; }
    public String      getDescription() { return description; }
    public Category    getCategory()    { return category; }
    public boolean     isEnabled()      { return enabled; }
    public boolean     isFavourite()    { return favourite; }
    public void        setFavourite(boolean fav) { this.favourite = fav; }
    public int         getKeybind()     { return keybind; }
    public void        setKeybind(int k){ this.keybind = k; }
    public List<Setting<?>> getSettings(){ return settings; }
}
