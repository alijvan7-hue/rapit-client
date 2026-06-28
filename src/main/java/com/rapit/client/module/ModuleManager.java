package com.rapit.client.module;

import com.rapit.client.module.modules.combat.*;
import com.rapit.client.module.modules.movement.*;
import com.rapit.client.module.modules.render.*;
import com.rapit.client.module.modules.player.*;
import com.rapit.client.module.modules.world.*;
import com.rapit.client.module.modules.misc.*;
import com.rapit.client.util.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Central registry for all client modules.
 * Provides lookup by name, category filtering and keybind dispatch.
 */
public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        registerAll();
        Logger.info("Loaded " + modules.size() + " modules.");
    }

    // ── Registration ──────────────────────────────────────────────────────────

    private void registerAll() {
        // Combat
        register(new KillAura());
        register(new Reach());
        register(new AutoClicker());
        register(new HitColor());

        // Movement
        register(new Sprint());
        register(new Sneak());
        register(new Fly());
        register(new Speed());
        register(new NoFall());
        register(new Blink());

        // Render
        register(new Zoom());
        register(new Fullbright());
        register(new Freelook());
        register(new BlockOverlay());
        register(new MotionBlur());
        register(new ChamsModule());
        register(new Tracers());
        register(new ESP());
        register(new ItemPhysics());
        register(new CrosshairEditor());

        // Player
        register(new AutoArmor());
        register(new NoRotate());
        register(new FastPlace());
        register(new Scaffold());

        // World
        register(new Nuker());
        register(new Timer());
        register(new ChestStealer());

        // Misc
        register(new AutoGG());
        register(new BetterChat());
        register(new ChatTimestamp());
        register(new ScreenshotViewer());
        register(new OldAnimations());
        register(new AntiBlind());
        register(new PackDisplay());
    }

    private void register(Module module) {
        modules.add(module);
    }

    // ── Key dispatch ──────────────────────────────────────────────────────────

    public void onKey(int key) {
        if (key == 0) return;
        modules.stream()
               .filter(m -> m.getKeybind() == key)
               .forEach(Module::toggle);
    }

    // ── Queries ───────────────────────────────────────────────────────────────

    public List<Module> getAll() {
        return Collections.unmodifiableList(modules);
    }

    public List<Module> getByCategory(Category category) {
        return modules.stream()
                      .filter(m -> m.getCategory() == category)
                      .collect(Collectors.toList());
    }

    public Optional<Module> getByName(String name) {
        return modules.stream()
                      .filter(m -> m.getName().equalsIgnoreCase(name))
                      .findFirst();
    }

    public List<Module> search(String query) {
        String q = query.toLowerCase();
        return modules.stream()
                      .filter(m -> m.getName().toLowerCase().contains(q)
                               || m.getDescription().toLowerCase().contains(q))
                      .collect(Collectors.toList());
    }

    public boolean isEnabled(String name) {
        return getByName(name).map(Module::isEnabled).orElse(false);
    }
}
