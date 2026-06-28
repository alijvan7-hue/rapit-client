package com.rapit.client.command;

import com.rapit.client.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.*;

/**
 * Handles in-game chat commands prefixed with ".".
 */
public class CommandManager {

    private final List<Command> commands = new ArrayList<>();
    private final Minecraft     mc       = Minecraft.getMinecraft();

    public static final String PREFIX = ".";

    public CommandManager() {
        registerBuiltins();
    }

    private void registerBuiltins() {
        register(new Command("help", "List all commands") {
            @Override
            public void execute(String[] args) {
                sendMessage("\u00A7eRapit Client Commands:");
                for (Command cmd : commands) {
                    sendMessage("\u00A76" + PREFIX + cmd.getName()
                            + " \u00A77- " + cmd.getDescription());
                }
            }
        });

        register(new Command("toggle", "Toggle a module") {
            @Override
            public void execute(String[] args) {
                if (args.length < 1) { sendMessage("\u00A7cUsage: .toggle <module>"); return; }
                String name = String.join(" ", args);
                Optional<com.rapit.client.module.Module> opt =
                        com.rapit.client.RapitClient.getInstance()
                                .getModuleManager().getByName(name);
                if (opt.isPresent()) {
                    com.rapit.client.module.Module m = opt.get();
                    m.toggle();
                    sendMessage("\u00A7a" + m.getName() + (m.isEnabled() ? " enabled" : " disabled"));
                } else {
                    sendMessage("\u00A7cModule not found: " + name);
                }
            }
        });

        register(new Command("profile", "Save/load/list config profiles") {
            @Override
            public void execute(String[] args) {
                if (args.length < 1) { sendMessage("\u00A7cUsage: .profile <save|load|list|delete> [name]"); return; }
                com.rapit.client.profile.ProfileManager pm =
                        com.rapit.client.RapitClient.getInstance().getProfileManager();
                switch (args[0]) {
                    case "save":
                        if (args.length < 2) { sendMessage("\u00A7cProvide a name."); return; }
                        pm.saveProfile(args[1]);
                        sendMessage("\u00A7aProfile saved: " + args[1]);
                        break;
                    case "load":
                        if (args.length < 2) { sendMessage("\u00A7cProvide a name."); return; }
                        pm.loadProfile(args[1]);
                        sendMessage("\u00A7aProfile loaded: " + args[1]);
                        break;
                    case "list":
                        sendMessage("\u00A7eProfiles: " + String.join(", ", pm.getProfiles()));
                        break;
                    case "delete":
                        if (args.length < 2) { sendMessage("\u00A7cProvide a name."); return; }
                        pm.deleteProfile(args[1]);
                        sendMessage("\u00A7cDeleted: " + args[1]);
                        break;
                    default:
                        sendMessage("\u00A7cUnknown sub-command: " + args[0]);
                }
            }
        });

        register(new Command("name", "Change offline username") {
            @Override
            public void execute(String[] args) {
                if (args.length < 1) { sendMessage("\u00A7cUsage: .name <username>"); return; }
                boolean ok = com.rapit.client.RapitClient.getInstance()
                        .getAccountManager().setUsername(args[0]);
                if (ok) sendMessage("\u00A7aUsername changed to: " + args[0]);
                else    sendMessage("\u00A7cFailed to change username.");
            }
        });
    }

    public void register(Command command) { commands.add(command); }

    /**
     * Processes a raw chat message.
     * @return true if the message was a client command (should be suppressed)
     */
    public boolean handleMessage(String raw) {
        if (!raw.startsWith(PREFIX)) return false;
        String[] parts = raw.substring(PREFIX.length()).split(" ");
        if (parts.length == 0) return true;
        String   name = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(name)) {
                try { cmd.execute(args); }
                catch (Exception e) { sendMessage("\u00A7cError: " + e.getMessage()); }
                return true;
            }
        }
        sendMessage("\u00A7cUnknown command: " + name + ". Type .help for help.");
        return true;
    }

    public static void sendMessage(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(
                    "\u00A78[\u00A76Rapit\u00A78] \u00A7r" + text));
        }
    }

    public List<Command> getCommands() { return commands; }
}
