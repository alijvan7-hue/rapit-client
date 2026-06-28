package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;

public class PotionEffectsElement extends HUDElement {
    public PotionEffectsElement(int x, int y) { super("Potions", x, y, 130, 14); }

    @Override
    public void render(ScaledResolution sr) {
        if (mc.thePlayer == null) return;
        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
        if (effects.isEmpty()) return;
        setHeight(effects.size() * 13 + 4);
        drawBackground();
        int oy = 2;
        for (PotionEffect effect : effects) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            if (potion == null) continue;
            String name = potion.getName().replace("potion.", "");
            int secs = effect.getDuration() / 20;
            String line = name + " " + (effect.getAmplifier() + 1) + " (" + secs + "s)";
            drawBoldText(line, 4, oy, ThemeManager.COLOR_WHITE);
            oy += 13;
        }
    }
}
