package com.rapit.client.module.setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting<String> {

    private final List<String> modes;

    public ModeSetting(String name, String description, String defaultMode, String... modes) {
        super(name, description, defaultMode);
        this.modes = Arrays.asList(modes);
    }

    public void cycle() {
        int idx = (modes.indexOf(value) + 1) % modes.size();
        value = modes.get(idx);
    }

    public List<String> getModes() { return modes; }

    @Override public String serialise()           { return value; }
    @Override public void   deserialise(String s) { if (modes.contains(s)) value = s; }
}
