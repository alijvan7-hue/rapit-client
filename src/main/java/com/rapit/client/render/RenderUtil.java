package com.rapit.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/** Low-level OpenGL 2D drawing utilities used by the ClickGUI and HUD. */
public final class RenderUtil {

    private RenderUtil() {}

    public static void drawRect(double x, double y, double w, double h, int color) {
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >>  8) & 0xFF) / 255f;
        float b = ( color        & 0xFF) / 255f;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        wr.pos(x,     y + h, 0).endVertex();
        wr.pos(x + w, y + h, 0).endVertex();
        wr.pos(x + w, y,     0).endVertex();
        wr.pos(x,     y,     0).endVertex();
        tess.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorder(double x, double y, double w, double h, int color) {
        drawRect(x,         y,         w, 1, color);
        drawRect(x,         y + h - 1, w, 1, color);
        drawRect(x,         y,         1, h, color);
        drawRect(x + w - 1, y,         1, h, color);
    }

    public static void drawGradientV(double x, double y, double w, double h,
                                      int topColor, int bottomColor) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        applyColor(wr, x,     y,     topColor);
        applyColor(wr, x + w, y,     topColor);
        applyColor(wr, x + w, y + h, bottomColor);
        applyColor(wr, x,     y + h, bottomColor);
        tess.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private static void applyColor(WorldRenderer wr, double x, double y, int color) {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >>  8) & 0xFF;
        int b =  color        & 0xFF;
        wr.pos(x, y, 0).color(r, g, b, a).endVertex();
    }

    public static void drawRoundedRect(double x, double y, double w, double h,
                                        double radius, int color, int segments) {
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >>  8) & 0xFF) / 255f;
        float b = ( color        & 0xFF) / 255f;

        radius = Math.min(radius, Math.min(w, h) / 2.0);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);

        wr.pos(x + w / 2.0, y + h / 2.0, 0).endVertex();

        double[][] corners = {
            { x + w - radius, y + radius,     0,              -Math.PI / 2.0 },
            { x + radius,     y + radius,     -Math.PI / 2.0, -Math.PI       },
            { x + radius,     y + h - radius, -Math.PI,       -Math.PI * 1.5 },
            { x + w - radius, y + h - radius, -Math.PI * 1.5, -Math.PI * 2.0 }
        };

        for (double[] corner : corners) {
            for (int s = 0; s <= segments; s++) {
                double angle = corner[2] + (corner[3] - corner[2]) * s / segments;
                wr.pos(corner[0] + Math.cos(angle) * radius,
                       corner[1] - Math.sin(angle) * radius, 0).endVertex();
            }
        }
        tess.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedRect(double x, double y, double w, double h,
                                        double radius, int color) {
        drawRoundedRect(x, y, w, h, radius, color, 12);
    }

    public static void drawGlow(double x, double y, double w, double h,
                                 int glowColor, int layers) {
        int baseAlpha = (glowColor >> 24) & 0xFF;
        for (int i = layers; i > 0; i--) {
            double expand = layers - i + 1;
            int alpha = (int)(baseAlpha * (i / (float) layers) * 0.4f);
            int c = (glowColor & 0x00FFFFFF) | (alpha << 24);
            drawRoundedRect(x - expand, y - expand,
                            w + expand * 2, h + expand * 2,
                            expand + 4, c, 12);
        }
    }

    public static void beginScissor(int x, int y, int w, int h, int scaleFactor) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * scaleFactor, y * scaleFactor, w * scaleFactor, h * scaleFactor);
    }

    public static void endScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void setAlpha(float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1f, 1f, 1f, alpha);
    }

    public static void resetAlpha() {
        GlStateManager.color(1f, 1f, 1f, 1f);
    }
}
