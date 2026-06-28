package com.rapit.client.render.font;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * OpenGL texture-atlas based custom font renderer.
 *
 * Generates a texture atlas from an AWT Font at construction time,
 * then uses it for all subsequent string draws.
 */
public class CustomFontRenderer {

    private static final int ATLAS_SIZE   = 512;
    private static final int CHAR_START   = 32;
    private static final int CHAR_END     = 256;
    private static final int PADDING      = 2;

    private final Font   awtFont;
    private final int    fontSize;

    // Per-character UV and width data
    private final float[] charX     = new float[CHAR_END];
    private final float[] charY     = new float[CHAR_END];
    private final float[] charW     = new float[CHAR_END];
    private final float[] charH     = new float[CHAR_END];
    private final float[] charAdv   = new float[CHAR_END];

    private int textureId = -1;
    private int fontHeight;

    public CustomFontRenderer(Font font, int size) {
        this.awtFont  = font.deriveFont((float) size);
        this.fontSize = size;
        buildAtlas();
    }

    // ── Atlas construction ────────────────────────────────────────────────────

    private void buildAtlas() {
        BufferedImage img   = new BufferedImage(ATLAS_SIZE, ATLAS_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D    g2d   = img.createGraphics();

        g2d.setFont(awtFont);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fm = g2d.getFontMetrics();
        fontHeight     = fm.getHeight();

        int cx = PADDING, cy = PADDING;
        g2d.setColor(Color.WHITE);

        for (int c = CHAR_START; c < CHAR_END; c++) {
            String s = String.valueOf((char) c);
            int cw   = fm.stringWidth(s) + PADDING * 2;

            if (cx + cw >= ATLAS_SIZE) { cx = PADDING; cy += fontHeight + PADDING; }
            if (cy + fontHeight >= ATLAS_SIZE) break;

            g2d.drawString(s, cx, cy + fm.getAscent());

            charX[c]   = cx / (float) ATLAS_SIZE;
            charY[c]   = cy / (float) ATLAS_SIZE;
            charW[c]   = cw / (float) ATLAS_SIZE;
            charH[c]   = fontHeight / (float) ATLAS_SIZE;
            charAdv[c] = cw - PADDING;

            cx += cw + PADDING;
        }
        g2d.dispose();

        // Upload to GL texture
        int[] pixels = new int[ATLAS_SIZE * ATLAS_SIZE];
        img.getRGB(0, 0, ATLAS_SIZE, ATLAS_SIZE, pixels, 0, ATLAS_SIZE);

        textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        java.nio.IntBuffer buf = java.nio.ByteBuffer.allocateDirect(pixels.length * 4)
                .order(java.nio.ByteOrder.nativeOrder()).asIntBuffer();
        buf.put(pixels).flip();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, ATLAS_SIZE, ATLAS_SIZE,
                          0, GL11.GL_BGRA, GL11.GL_UNSIGNED_BYTE, buf);
    }

    // ── Drawing ───────────────────────────────────────────────────────────────

    public int drawString(String text, float x, float y, int color) {
        if (textureId == -1) return 0;

        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >>  8) & 0xFF) / 255f;
        float b = ( color        & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float drawX = x;
        for (char c : text.toCharArray()) {
            if (c < CHAR_START || c >= CHAR_END) { drawX += fontSize * 0.5f; continue; }

            float u1 = charX[c], v1 = charY[c];
            float u2 = u1 + charW[c], v2 = v1 + charH[c];
            float cw  = charW[c]  * ATLAS_SIZE;
            float ch  = charH[c]  * ATLAS_SIZE;

            wr.pos(drawX,      y,      0).tex(u1, v1).endVertex();
            wr.pos(drawX,      y + ch, 0).tex(u1, v2).endVertex();
            wr.pos(drawX + cw, y + ch, 0).tex(u2, v2).endVertex();
            wr.pos(drawX + cw, y,      0).tex(u2, v1).endVertex();

            drawX += charAdv[c];
        }
        tess.draw();

        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.disableBlend();
        return (int)(drawX - x);
    }

    public int getStringWidth(String text) {
        float w = 0;
        for (char c : text.toCharArray()) {
            if (c >= CHAR_START && c < CHAR_END) w += charAdv[c];
            else w += fontSize * 0.5f;
        }
        return (int) w;
    }

    public int getFontHeight() { return fontHeight; }
}
