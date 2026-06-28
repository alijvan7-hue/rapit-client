package com.rapit.client.command;

/** Base class for all Rapit Client chat commands. */
public abstract class Command {

    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name        = name;
        this.description = description;
    }

    /** Override to implement command logic. */
    public abstract void execute(String[] args);

    public String getName()        { return name; }
    public String getDescription() { return description; }

    // ── Helper for sending feedback messages ──────────────────────────────────
    protected void sendMessage(String msg) { CommandManager.sendMessage(msg); }
}
