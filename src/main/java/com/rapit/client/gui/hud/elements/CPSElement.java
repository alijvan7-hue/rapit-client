package com.rapit.client.gui.hud.elements;

import com.rapit.client.gui.hud.HUDElement;
import com.rapit.client.render.theme.ThemeManager;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class CPSElement extends HUDElement {

    private final List<Long> leftClicks  = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();
    private boolean wasLeft  = false;
    private boolean wasRight = false;

    public CPSElement(int x, int y) { super("CPS", x, y, 75, 14); }

    @Override
    public void render(ScaledResolution sr) {
        long now = System.currentTimeMillis();

        // Only register on press (edge detection), not hold
        boolean leftDown  = Mouse.isButtonDown(0);
        boolean rightDown = Mouse.isButtonDown(1);
        if (leftDown  && !wasLeft)  leftClicks.add(now);
        if (rightDown && !wasRight) rightClicks.add(now);
        wasLeft  = leftDown;
        wasRight = rightDown;

        // Remove clicks older than 1 second
        leftClicks.removeIf(t  -> now - t > 1000);
        rightClicks.removeIf(t -> now - t > 1000);

        drawBackground();
        drawBoldText("CPS", 4, 2, ThemeManager.COLOR_TEXT_MUTED);
        drawBoldText(leftClicks.size() + "L", 28, 2, ThemeManager.COLOR_PRIMARY);
        drawBoldText(rightClicks.size() + "R", 50, 2, ThemeManager.COLOR_WHITE);
    }
}
