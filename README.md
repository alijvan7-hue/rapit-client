# ⚡ Rapit Client v1.0.0

> A professional, production-quality Minecraft 1.8.9 Forge client mod.
> Theme: Yellow `#FFD400` on Black `#0E0E0E` • Smooth animations • Modern rounded UI

---

## 📦 Project Stats

| Item | Value |
|------|-------|
| Minecraft | 1.8.9 |
| Forge | 11.15.1.2318 |
| Language | Java 8 |
| Total Java files | 90+ |
| Modules | 30+ |
| HUD Elements | 15 |

---

## 🚀 Quick Start (Build)

### Prerequisites
- Java 8 JDK (exactly – not 11, not 17)
- Git
- Internet connection (for Gradle/Forge downloads)

### Steps

```bash
# 1. Clone / open the project folder
cd RapitClient

# 2. Set up ForgeGradle workspace
./gradlew setupDecompWorkspace   # Linux/Mac
gradlew.bat setupDecompWorkspace # Windows

# 3. Generate IDE project files (IntelliJ)
./gradlew idea

# 4. Build the JAR
./gradlew build

# Output: build/libs/RapitClient-1.0.0.jar
```

---

## 📥 Installation

1. Install **Minecraft 1.8.9** via the official launcher
2. Install **Forge 11.15.1.2318** for 1.8.9
3. Copy `RapitClient-1.0.0.jar` into `.minecraft/mods/`
4. Launch with the **Forge 1.8.9** profile

---

## 🎮 Keybinds

| Key | Action |
|-----|--------|
| `Right Shift` | Open / close ClickGUI |
| `Right Control` | Open / close HUD Editor |

---

## 🖥️ ClickGUI Usage

- **Left-click** a module card → Toggle ON/OFF
- **Right-click** a module card → Open settings panel
- **Search bar** (top-right) → Filter modules by name/description
- **Left sidebar** → Switch categories
- **Scroll** → Scroll through module cards

---

## 📊 HUD Elements

| Element | Default Position |
|---------|-----------------|
| FPS | Top-left |
| CPS | Top-left |
| Ping | Top-left |
| BPS | Top-left |
| Coordinates | Top-left |
| Direction | Top-left |
| Armor Status | Top-left |
| Potion Effects | Top-left |
| Keystrokes | Top-center |
| Session Time | Top-left |
| Clock | Top-left |
| Memory Usage | Top-left |
| Speed Display | Top-left |
| Combo Counter | Top-center |
| Pack Display | Top-left |

---

## 💬 Commands

All commands are prefixed with `.` in chat.

| Command | Description |
|---------|-------------|
| `.help` | List all commands |
| `.toggle <module>` | Toggle a module by name |
| `.profile save <name>` | Save current config |
| `.profile load <name>` | Load a config |
| `.profile list` | List all profiles |
| `.profile delete <name>` | Delete a profile |
| `.name <username>` | Change username (offline only) |

---

## 🗂️ Module List

### ⚔ Combat
- KillAura, Reach, AutoClicker, HitColor

### ⚡ Movement
- Sprint, Sneak, Fly, Speed, NoFall, Blink

### ✨ Render
- Zoom, Fullbright, Freelook, BlockOverlay, MotionBlur,
  Chams, Tracers, ESP, ItemPhysics, CrosshairEditor

### 👤 Player
- AutoArmor, NoRotate, FastPlace, Scaffold

### 🌍 World
- Nuker, Timer, ChestStealer

### 🔧 Misc
- AutoGG, BetterChat, ChatTimestamp, ScreenshotViewer,
  OldAnimations, AntiBlind, PackDisplay

---

## 📁 Package Structure

```
com.rapit.client/
├── RapitClient.java          Main mod entry point
├── ClientEventHandler.java   Global Forge event bridge
├── account/                  Session & username management
├── animation/                Lerp, easing, smooth animation utilities
├── command/                  Chat command system (.help, .toggle, etc.)
├── config/                   .rcfg save/load system
├── event/
│   ├── bus/                  Annotation-driven EventBus
│   └── events/               Event classes (PlayerUpdate, Motion, Render…)
├── gui/
│   ├── clickgui/             ClickGUI + SettingsPanel
│   ├── hud/                  HUDManager, HUDElement base, HUDEditor
│   │   └── elements/         15 individual HUD element classes
│   └── mainmenu/             Custom branded main menu
├── module/
│   ├── Module.java           Base module class
│   ├── ModuleManager.java    Registry + keybind dispatch
│   ├── Category.java         COMBAT/MOVEMENT/RENDER/HUD/PLAYER/WORLD/MISC
│   ├── setting/              BoolSetting, SliderSetting, ModeSetting
│   └── modules/              All 30+ module implementations
│       ├── combat/
│       ├── movement/
│       ├── render/
│       ├── player/
│       ├── world/
│       └── misc/
├── profile/                  Profile switching wrapper
├── render/
│   ├── RenderUtil.java       OpenGL drawing (rects, gradients, rounded, glow)
│   ├── font/                 Custom TTF font atlas renderer
│   └── theme/                ThemeManager (brand colors + constants)
└── util/                     Logger, MathUtil, TimerUtil, ColorUtil, ChatUtil
```

---

## ⚙️ Config Files

Configs are stored in `.minecraft/rapit/configs/` as `.rcfg` files.
The default profile is `default.rcfg` and auto-saves when you close the HUD editor.

---

## 🎨 Theme

| Token | Color | Hex |
|-------|-------|-----|
| Primary | Yellow | `#FFD400` |
| Background | Near-black | `#0E0E0E` |
| Surface | Dark gray | `#1A1A1A` |
| Border | Gray | `#2A2A2A` |
| Text muted | Dim | `#888888` |
| Enabled | Green | `#4CAF50` |

---

## 📜 License

Rapit Client is a private educational/personal project.
Not affiliated with Mojang, Microsoft, or Minecraft.
