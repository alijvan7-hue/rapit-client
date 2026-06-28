# 📦 راهنمای نصب Rapit Client با VSCode

> این راهنما به زبان فارسی نوشته شده است. تمام مراحل را به ترتیب دنبال کنید.

---

## 🔧 پیش‌نیازها

قبل از شروع، مطمئن شوید که موارد زیر را دارید:

| نرم‌افزار | نسخه | لینک دانلود |
|----------|------|------------|
| Java JDK | دقیقاً نسخه 8 | adoptium.net |
| Minecraft Launcher | آخرین نسخه | minecraft.net |
| Forge Installer | 1.8.9 - 11.15.1.2318 | files.minecraftforge.net |
| VSCode | آخرین نسخه | code.visualstudio.com |
| Git | هر نسخه | git-scm.com |

---

## مرحله ۱ — نصب Java 8

1. به سایت **https://adoptium.net** بروید
2. **Temurin 8 (LTS)** را انتخاب کنید
3. نسخه **JDK** (نه JRE) را برای سیستم‌عامل خود دانلود کنید
4. نصب‌کننده را اجرا کنید و گزینه‌ی "Set JAVA_HOME variable" را تیک بزنید

**تأیید نصب:** در Command Prompt یا Terminal تایپ کنید:
```bash
java -version
```
باید چیزی مثل `java version "1.8.0_xxx"` ببینید.

> ⚠️ مهم: فقط Java 8 کار می‌کند. Java 11 یا 17 با ForgeGradle 2.x سازگار نیست.

---

## مرحله ۲ — نصب Forge برای Minecraft 1.8.9

1. Minecraft را یک بار اجرا کنید تا فایل‌های پایه نصب شوند
2. به **https://files.minecraftforge.net** بروید
3. از منو، **1.8.9** را انتخاب کنید
4. نسخه‌ی **11.15.1.2318** را دانلود کنید (ستون Installer)
5. فایل `.jar` دانلود شده را اجرا کنید (دابل کلیک)
6. گزینه **Install Client** را انتخاب و OK کنید

---

## مرحله ۳ — نصب افزونه‌های VSCode

1. VSCode را باز کنید
2. به بخش Extensions بروید (Ctrl+Shift+X)
3. افزونه‌های زیر را نصب کنید:

```
Extension Pack for Java     (Microsoft)
Gradle for Java             (Microsoft)
```

4. پس از نصب، VSCode را ری‌استارت کنید

---

## مرحله ۴ — باز کردن پروژه در VSCode

1. فولدر **RapitClient** را در جایی مثل `C:\Projects\RapitClient` کپی کنید
2. در VSCode از منو انتخاب کنید: **File → Open Folder**
3. فولدر `RapitClient` را انتخاب کنید
4. وقتی VSCode سؤال کرد "Do you trust the authors?" — **Yes** را بزنید

---

## مرحله ۵ — تنظیم JAVA_HOME در VSCode

1. در VSCode: **Ctrl+Shift+P** را بزنید
2. تایپ کنید: `Java: Configure Java Runtime`
3. مطمئن شوید JDK 8 (نه 11 یا 17) انتخاب شده

یا به روش دستی:

**Windows:**
```
JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-8.x.x.x-hotspot
```

**macOS/Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
```

---

## مرحله ۶ — دانلود Forge Workspace (مهم‌ترین مرحله)

**Terminal در VSCode را باز کنید** (Ctrl+\`) و دستورات زیر را اجرا کنید:

### ویندوز:
```batch
cd C:\Projects\RapitClient
gradlew.bat setupDecompWorkspace
```

### لینوکس / مک:
```bash
cd ~/Projects/RapitClient
chmod +x gradlew
./gradlew setupDecompWorkspace
```

> ⏳ این مرحله ۵ تا ۲۰ دقیقه طول می‌کشد و به اینترنت نیاز دارد.
> Gradle در حال دانلود Minecraft، Forge و dependency ها است.

---

## مرحله ۷ — کامپایل پروژه (Build)

پس از اتمام مرحله قبل، دستور build را اجرا کنید:

### ویندوز:
```batch
gradlew.bat build
```

### لینوکس / مک:
```bash
./gradlew build
```

اگر همه چیز درست باشد، در انتها می‌بینید:
```
BUILD SUCCESSFUL
```

فایل نهایی در این مسیر قرار می‌گیرد:
```
RapitClient/build/libs/RapitClient-1.0.0.jar
```

---

## مرحله ۸ — نصب Mod در Minecraft

1. فایل `RapitClient-1.0.0.jar` را کپی کنید
2. فولدر `.minecraft\mods\` را باز کنید:
   - **Windows:** `%APPDATA%\.minecraft\mods\`
   - **macOS:** `~/Library/Application Support/minecraft/mods/`
   - **Linux:** `~/.minecraft/mods/`
3. اگر فولدر `mods` وجود ندارد، آن را بسازید
4. فایل JAR را در این فولدر جای‌گذاری کنید

---

## مرحله ۹ — اجرای Minecraft با Rapit Client

1. **Minecraft Launcher** را باز کنید
2. از بالا، پروفایل **Forge 1.8.9** را انتخاب کنید
3. **Play** را بزنید
4. منتظر بمانید تا Minecraft کامل لود شود
5. در منوی اصلی، لوگو و طراحی Rapit Client را می‌بینید ✅

---

## ✅ تست نهایی

وقتی وارد دنیا یا سرور شدید:

| کلید | نتیجه |
|------|-------|
| `Right Shift` | ClickGUI با تم زرد-مشکی باز می‌شود |
| `Right Control` | HUD Editor باز می‌شود (drag & drop) |
| `.help` در چت | لیست دستورات |

---

## 🔴 رفع مشکلات رایج

### ❌ خطای "Could not resolve com.mojang:authlib"
```batch
gradlew.bat setupDecompWorkspace --refresh-dependencies
```

### ❌ خطای JAVA_HOME
مطمئن شوید متغیر JAVA_HOME را به Java 8 تنظیم کرده‌اید (نه 11 یا 17).

### ❌ Minecraft کرش می‌کند
لاگ را در `.minecraft/crash-reports/` بررسی کنید.
احتمالاً یک Mod دیگر با Rapit Client تداخل دارد — فقط RapitClient.jar را نگه دارید.

### ❌ ClickGUI باز نمی‌شود
مطمئن شوید که داخل دنیا هستید. ClickGUI فقط در داخل دنیا کار می‌کند.

---

## 📁 ساختار فایل‌های Config

پس از اجرای اول، فولدر `.minecraft/rapit/configs/` ساخته می‌شود.
تنظیمات شما در فایل `default.rcfg` ذخیره می‌شود.

برای ساختن پروفایل جدید در چت تایپ کنید:
```
.profile save MyProfile
```

---

> 🛠️ پروژه توسط RapitDev ساخته شده — Minecraft 1.8.9 • Forge 11.15.1.2318
