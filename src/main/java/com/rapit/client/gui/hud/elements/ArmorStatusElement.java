package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

public class ArmorStatusElement extends HUDElement {
    public ArmorStatusElement(int x, int y) { super("Armor", x, y, 95, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer == null) return;
        int totalDurability = 0, maxDurability = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack armor = mc.thePlayer.inventory.armorInventory[i];
            if (armor != null && armor.isItemStackDamageable()) {
                totalDurability += armor.getMaxDamage() - armor.getItemDamage();
                maxDurability   += armor.getMaxDamage();
            }
        }
        drawBackground();
        if (maxDurability == 0) {
            drawBoldText("No armor", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
            return;
        }
        float pct = totalDurability / (float) maxDurability;
        int color = pct > 0.5f ? ThemeManager.COLOR_ENABLED :
                    pct > 0.2f ? ThemeManager.COLOR_PRIMARY  : 0xFFFF4444;
        drawBoldText("Armor " + (int)(pct * 100) + "%", 4, 2, color);
    }
}
