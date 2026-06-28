package com.rapit.client.module.setting;

public class BoolSetting extends Setting<Boolean> {

    public BoolSetting(String name, String description, boolean defaultValue) {
        super(name, description, defaultValue);
    }

    // Convenience constructor used in some modules (ignores extra arg)
    public BoolSetting(String name, String description, boolean defaultValue, String ignored) {
        this(name, description, defaultValue);
    }

    public void toggle() { value = !value; }

    @Override public String serialise()           { return value.toString(); }
    @Override public void   deserialise(String s) { value = Boolean.parseBoolean(s); }
}
