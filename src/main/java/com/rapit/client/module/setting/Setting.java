package com.rapit.client.module.setting;

/**
 * Generic setting that a module can expose in the ClickGUI settings panel.
 *
 * @param <T> value type (Boolean, Double, String, Enum …)
 */
public abstract class Setting<T> {

    private final String name;
    private final String description;
    protected T value;

    public Setting(String name, String description, T defaultValue) {
        this.name        = name;
        this.description = description;
        this.value       = defaultValue;
    }

    public String getName()        { return name; }
    public String getDescription() { return description; }
    public T      getValue()       { return value; }
    public void   setValue(T v)    { this.value = v; }

    /** Returns the JSON-serialisable string representation. */
    public abstract String serialise();

    /** Restores value from a JSON-deserialized string. */
    public abstract void   deserialise(String raw);
}
