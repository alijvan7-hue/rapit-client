package com.rapit.client;

import com.rapit.client.account.AccountManager;
import com.rapit.client.command.CommandManager;
import com.rapit.client.config.ConfigManager;
import com.rapit.client.event.bus.EventBus;
import com.rapit.client.gui.clickgui.ClickGUI;
import com.rapit.client.gui.hud.HUDManager;
import com.rapit.client.module.ModuleManager;
import com.rapit.client.profile.ProfileManager;
import com.rapit.client.render.font.FontManager;
import com.rapit.client.render.theme.ThemeManager;
import com.rapit.client.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Rapit Client - Main Entry Point
 * Professional Minecraft 1.8.9 Client Mod
 *
 * @version 1.0.0
 */
@Mod(
    modid       = RapitClient.MOD_ID,
    name        = RapitClient.MOD_NAME,
    version     = RapitClient.VERSION,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true
)
public class RapitClient {

    public static final String MOD_ID   = "rapitclient";
    public static final String MOD_NAME = "Rapit Client";
    public static final String VERSION  = "1.0.0";

    @Mod.Instance(MOD_ID)
    public static RapitClient INSTANCE;

    // Core systems
    private EventBus       eventBus;
    private ModuleManager  moduleManager;
    private HUDManager     hudManager;
    private ConfigManager  configManager;
    private CommandManager commandManager;
    private FontManager    fontManager;
    private ThemeManager   themeManager;
    private AccountManager accountManager;
    private ProfileManager profileManager;
    private ClickGUI       clickGUI;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logger.info("Rapit Client v" + VERSION + " pre-initializing...");
        eventBus       = new EventBus();
        themeManager   = new ThemeManager();
        fontManager    = new FontManager();
        accountManager = new AccountManager();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Logger.info("Rapit Client initializing systems...");
        profileManager  = new ProfileManager();
        configManager   = new ConfigManager();
        moduleManager   = new ModuleManager();
        hudManager      = new HUDManager();
        commandManager  = new CommandManager();
        clickGUI        = new ClickGUI();

        // Register global event listener with Forge
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        Logger.info("All systems initialized successfully.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        configManager.loadDefault();
        Logger.info("Rapit Client v" + VERSION + " is ready!");
    }

    // ── Static accessors ──────────────────────────────────────────────────────

    public static RapitClient getInstance()        { return INSTANCE; }
    public static Minecraft    mc()                { return Minecraft.getMinecraft(); }

    public EventBus       getEventBus()            { return eventBus; }
    public ModuleManager  getModuleManager()       { return moduleManager; }
    public HUDManager     getHUDManager()          { return hudManager; }
    public ConfigManager  getConfigManager()       { return configManager; }
    public CommandManager getCommandManager()      { return commandManager; }
    public FontManager    getFontManager()         { return fontManager; }
    public ThemeManager   getThemeManager()        { return themeManager; }
    public AccountManager getAccountManager()      { return accountManager; }
    public ProfileManager getProfileManager()      { return profileManager; }
    public ClickGUI       getClickGUI()            { return clickGUI; }
}
