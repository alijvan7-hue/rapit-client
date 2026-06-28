package com.rapit.client.module;

/**
 * ClickGUI categories – each maps to a navigation tab.
 */
public enum Category {
    COMBAT   ("Combat",    "\u2694"),   // ⚔
    MOVEMENT ("Movement",  "\u26A1"),   // ⚡
    RENDER   ("Render",    "\u2728"),   // ✨
    HUD      ("HUD",       "\ud83d\udcca"), // 📊
    PLAYER   ("Player",    "\ud83d\udc64"), // 👤
    WORLD    ("World",     "\ud83c\udf0d"), // 🌍
    MISC     ("Misc",      "\ud83d\udd27"); // 🔧

    private final String displayName;
    private final String icon;

    Category(String displayName, String icon) {
        this.displayName = displayName;
        this.icon        = icon;
    }

    public String getDisplayName() { return displayName; }
    public String getIcon()        { return icon; }
}
