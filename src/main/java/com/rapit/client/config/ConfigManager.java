package com.rapit.client.config;

import com.rapit.client.RapitClient;
import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.module.Module;
import com.rapit.client.module.setting.Setting;
import com.rapit.client.util.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Manages Rapit Client configuration files.
 *
 * <p>Config format: human-readable properties-like text stored under
 * {@code .minecraft/rapit/configs/<name>.rcfg}.
 *
 * <p>Structure of each .rcfg file:
 * <pre>
 * # Module states
 * module.Sprint.enabled=true
 * module.Sprint.keybind=0
 * # Module settings
 * module.Sprint.setting.Mode=Omni
 * # HUD positions
 * hud.FPS.x=5
 * hud.FPS.y=5
 * hud.FPS.enabled=true
 * </pre>
 */
public class ConfigManager {

    private static final String CONFIG_DIR     = "rapit/configs";
    private static final String DEFAULT_CONFIG = "default";
    private static final String EXT            = ".rcfg";

    private final Path configDir;
    private String     currentProfile = DEFAULT_CONFIG;

    public ConfigManager() {
        configDir = Paths.get(CONFIG_DIR);
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            Logger.error("Could not create config directory: " + e.getMessage());
        }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void saveDefault()             { save(DEFAULT_CONFIG); }
    public void loadDefault()             { load(DEFAULT_CONFIG); }
    public void save(String name)         { writeConfig(name); }
    public void load(String name)         { readConfig(name); currentProfile = name; }
    public String getCurrentProfile()     { return currentProfile; }

    public List<String> listProfiles() {
        List<String> result = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(configDir, "*" + EXT)) {
            for (Path p : ds) {
                String n = p.getFileName().toString();
                result.add(n.substring(0, n.length() - EXT.length()));
            }
        } catch (IOException e) {
            Logger.error("Could not list configs: " + e.getMessage());
        }
        return result;
    }

    public void deleteProfile(String name) {
        try { Files.deleteIfExists(configDir.resolve(name + EXT)); }
        catch (IOException e) { Logger.error("Could not delete config: " + e.getMessage()); }
    }

    // ── Write ─────────────────────────────────────────────────────────────────

    private void writeConfig(String name) {
        Path file = configDir.resolve(name + EXT);
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            bw.write("# Rapit Client config - " + name); bw.newLine();
            bw.write("# Generated: " + new java.util.Date()); bw.newLine();
            bw.newLine();

            // Modules
            for (Module m : RapitClient.getInstance().getModuleManager().getAll()) {
                String prefix = "module." + m.getName() + ".";
                writeProp(bw, prefix + "enabled", String.valueOf(m.isEnabled()));
                writeProp(bw, prefix + "keybind", String.valueOf(m.getKeybind()));
                writeProp(bw, prefix + "favourite", String.valueOf(m.isFavourite()));
                for (Setting<?> s : m.getSettings()) {
                    writeProp(bw, prefix + "setting." + s.getName(), s.serialise());
                }
            }

            bw.newLine();

            // HUD elements
            for (HUDElement el : RapitClient.getInstance().getHUDManager().getElements()) {
                String prefix = "hud." + el.getName() + ".";
                writeProp(bw, prefix + "x",       String.valueOf(el.getX()));
                writeProp(bw, prefix + "y",       String.valueOf(el.getY()));
                writeProp(bw, prefix + "enabled", String.valueOf(el.isEnabled()));
            }

            Logger.info("Config saved: " + name);
        } catch (IOException e) {
            Logger.error("Could not save config '" + name + "': " + e.getMessage());
        }
    }

    private void writeProp(BufferedWriter bw, String key, String value) throws IOException {
        bw.write(key + "=" + value);
        bw.newLine();
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    private void readConfig(String name) {
        Path file = configDir.resolve(name + EXT);
        if (!Files.exists(file)) {
            Logger.info("Config '" + name + "' not found, using defaults.");
            return;
        }

        Map<String, String> props = new LinkedHashMap<>();
        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if (eq < 0) continue;
                props.put(line.substring(0, eq).trim(), line.substring(eq + 1).trim());
            }
        } catch (IOException e) {
            Logger.error("Could not read config '" + name + "': " + e.getMessage());
            return;
        }

        // Apply to modules
        for (Module m : RapitClient.getInstance().getModuleManager().getAll()) {
            String prefix = "module." + m.getName() + ".";
            applyBool(props, prefix + "enabled",   v -> m.setEnabled(Boolean.parseBoolean(v)));
            applyBool(props, prefix + "favourite",  v -> m.setFavourite(Boolean.parseBoolean(v)));
            if (props.containsKey(prefix + "keybind"))
                m.setKeybind(Integer.parseInt(props.get(prefix + "keybind")));
            for (Setting<?> s : m.getSettings()) {
                String val = props.get(prefix + "setting." + s.getName());
                if (val != null) s.deserialise(val);
            }
        }

        // Apply to HUD
        for (HUDElement el : RapitClient.getInstance().getHUDManager().getElements()) {
            String prefix = "hud." + el.getName() + ".";
            if (props.containsKey(prefix + "x")) el.setX(Integer.parseInt(props.get(prefix + "x")));
            if (props.containsKey(prefix + "y")) el.setY(Integer.parseInt(props.get(prefix + "y")));
            applyBool(props, prefix + "enabled", v -> el.setEnabled(Boolean.parseBoolean(v)));
        }

        Logger.info("Config loaded: " + name);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void applyBool(Map<String, String> props, String key, java.util.function.Consumer<String> fn) {
        if (props.containsKey(key)) fn.accept(props.get(key));
    }
}
