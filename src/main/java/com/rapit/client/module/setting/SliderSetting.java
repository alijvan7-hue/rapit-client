package com.rapit.client.module.setting;

public class SliderSetting extends Setting<Double> {

    private final double min;
    private final double max;
    private final double increment;

    public SliderSetting(String name, String description,
                         double defaultValue, double min, double max, double increment) {
        super(name, description, defaultValue);
        this.min       = min;
        this.max       = max;
        this.increment = increment;
    }

    @Override
    public void setValue(Double v) {
        // Clamp and snap to nearest increment
        double clamped = Math.max(min, Math.min(max, v));
        super.setValue(Math.round(clamped / increment) * increment);
    }

    public double getMin()       { return min; }
    public double getMax()       { return max; }
    public double getIncrement() { return increment; }

    @Override public String serialise()           { return value.toString(); }
    @Override public void   deserialise(String s) { setValue(Double.parseDouble(s)); }
}
