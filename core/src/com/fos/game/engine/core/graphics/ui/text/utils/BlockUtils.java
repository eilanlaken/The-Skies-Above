// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

public class BlockUtils
{
    public static final float THIN_START = 0.45f;
    public static final float THIN_END = 0.55f;
    public static final float THIN_ACROSS = 0.1f;
    public static final float THIN_OVER = 0.55f;
    public static final float WIDE_START = 0.4f;
    public static final float WIDE_END = 0.6f;
    public static final float WIDE_ACROSS = 0.2f;
    public static final float WIDE_OVER = 0.6f;
    public static final float TWIN_START1 = 0.35f;
    public static final float TWIN_END1 = 0.45f;
    public static final float TWIN_START2 = 0.55f;
    public static final float TWIN_END2 = 0.65f;
    public static final float TWIN_ACROSS = 0.1f;
    public static final float TWIN_OVER1 = 0.65f;
    public static final float TWIN_OVER2 = 0.45f;
    public static final float[][] BOX_DRAWING;
    
    public static boolean isBlockGlyph(final int c) {
        return (c >= 9472 && c <= 9580) || (c >= 9588 && c <= 9616) || (c >= 9620 && c <= 9631);
    }
    
    static {
        BOX_DRAWING = new float[][] { { 0.0f, 0.45f, 1.0f, 0.1f }, { 0.0f, 0.4f, 1.0f, 0.2f }, { 0.45f, 0.0f, 0.1f, 1.0f }, { 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.45f, 0.2f, 0.1f, 0.4f, 0.45f, 0.2f, 0.1f, 0.8f, 0.45f, 0.2f, 0.1f }, { 0.0f, 0.4f, 0.2f, 0.2f, 0.4f, 0.4f, 0.2f, 0.2f, 0.8f, 0.4f, 0.2f, 0.2f }, { 0.45f, 0.0f, 0.1f, 0.2f, 0.45f, 0.4f, 0.1f, 0.2f, 0.45f, 0.8f, 0.1f, 0.2f }, { 0.4f, 0.0f, 0.2f, 0.2f, 0.4f, 0.4f, 0.2f, 0.2f, 0.4f, 0.8f, 0.2f, 0.2f }, { 0.0f, 0.45f, 0.14285715f, 0.1f, 0.2857143f, 0.45f, 0.14285715f, 0.1f, 0.5714286f, 0.45f, 0.14285715f, 0.1f, 0.85714287f, 0.45f, 0.14285715f, 0.1f }, { 0.0f, 0.4f, 0.14285715f, 0.2f, 0.2857143f, 0.4f, 0.14285715f, 0.2f, 0.5714286f, 0.4f, 0.14285715f, 0.2f, 0.85714287f, 0.4f, 0.14285715f, 0.2f }, { 0.45f, 0.0f, 0.1f, 0.14285715f, 0.45f, 0.2857143f, 0.1f, 0.14285715f, 0.45f, 0.5714286f, 0.1f, 0.14285715f, 0.45f, 0.85714287f, 0.1f, 0.14285715f }, { 0.4f, 0.0f, 0.2f, 0.14285715f, 0.4f, 0.2857143f, 0.2f, 0.14285715f, 0.4f, 0.5714286f, 0.2f, 0.14285715f, 0.4f, 0.85714287f, 0.2f, 0.14285715f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.45f, 0.4f, 0.55f, 0.2f, 0.45f, 0.0f, 0.1f, 0.6f }, { 0.4f, 0.45f, 0.6f, 0.1f, 0.4f, 0.0f, 0.2f, 0.55f }, { 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.55f, 0.2f, 0.45f, 0.0f, 0.1f, 0.6f }, { 0.0f, 0.45f, 0.6f, 0.1f, 0.4f, 0.0f, 0.2f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.45f, 0.4f, 0.55f, 0.2f, 0.45f, 0.4f, 0.1f, 0.6f }, { 0.4f, 0.45f, 0.6f, 0.1f, 0.4f, 0.45f, 0.2f, 0.55f }, { 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.55f, 0.2f, 0.45f, 0.4f, 0.1f, 0.6f }, { 0.0f, 0.45f, 0.6f, 0.1f, 0.4f, 0.45f, 0.2f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.45f, 0.4f, 0.55f, 0.2f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f, 0.4f, 0.45f, 0.2f, 0.55f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.45f, 0.45f, 0.55f, 0.1f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.4f, 0.55f, 0.2f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f, 0.4f, 0.45f, 0.2f, 0.55f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.55f, 0.2f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.45f, 0.45f, 0.1f, 0.55f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.45f, 0.0f, 0.1f, 1.0f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 1.0f, 0.1f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.0f, 0.4f, 1.0f, 0.2f, 0.4f, 0.0f, 0.2f, 1.0f }, { 0.125f, 0.45f, 0.25f, 0.1f, 0.625f, 0.45f, 0.25f, 0.1f }, { 0.125f, 0.4f, 0.25f, 0.2f, 0.625f, 0.4f, 0.25f, 0.2f }, { 0.45f, 0.125f, 0.1f, 0.25f, 0.45f, 0.625f, 0.1f, 0.25f }, { 0.4f, 0.125f, 0.2f, 0.25f, 0.4f, 0.625f, 0.2f, 0.25f }, { 0.0f, 0.35f, 1.0f, 0.1f, 0.0f, 0.55f, 1.0f, 0.1f }, { 0.35f, 0.0f, 0.1f, 1.0f, 0.55f, 0.0f, 0.1f, 1.0f }, { 0.45f, 0.0f, 0.1f, 0.65f, 0.45f, 0.35f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.1f }, { 0.35f, 0.0f, 0.1f, 0.55f, 0.55f, 0.0f, 0.1f, 0.55f, 0.35f, 0.45f, 0.65f, 0.1f }, { 0.35f, 0.0f, 0.1f, 0.65f, 0.55f, 0.0f, 0.1f, 0.45f, 0.55f, 0.35f, 0.45f, 0.1f, 0.35f, 0.55f, 0.65f, 0.1f }, { 0.45f, 0.0f, 0.1f, 0.65f, 0.0f, 0.35f, 0.55f, 0.1f, 0.0f, 0.55f, 0.55f, 0.1f }, { 0.35f, 0.0f, 0.1f, 0.55f, 0.55f, 0.0f, 0.1f, 0.55f, 0.0f, 0.45f, 0.65f, 0.1f }, { 0.35f, 0.0f, 0.1f, 0.45f, 0.55f, 0.0f, 0.1f, 0.65f, 0.0f, 0.35f, 0.45f, 0.1f, 0.0f, 0.55f, 0.65f, 0.1f }, { 0.45f, 0.35f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.1f, 0.45f, 0.35f, 0.1f, 0.65f }, { 0.35f, 0.45f, 0.1f, 0.55f, 0.55f, 0.45f, 0.1f, 0.55f, 0.35f, 0.45f, 0.65f, 0.1f }, { 0.35f, 0.35f, 0.1f, 0.65f, 0.55f, 0.55f, 0.1f, 0.45f, 0.35f, 0.35f, 0.65f, 0.1f, 0.55f, 0.55f, 0.45f, 0.1f }, { 0.45f, 0.35f, 0.1f, 0.65f, 0.0f, 0.35f, 0.55f, 0.1f, 0.0f, 0.55f, 0.55f, 0.1f }, { 0.35f, 0.45f, 0.1f, 0.55f, 0.55f, 0.45f, 0.1f, 0.55f, 0.0f, 0.45f, 0.65f, 0.1f }, { 0.35f, 0.55f, 0.1f, 0.45f, 0.55f, 0.35f, 0.1f, 0.65f, 0.0f, 0.35f, 0.65f, 0.1f, 0.0f, 0.55f, 0.45f, 0.1f }, { 0.45f, 0.0f, 0.1f, 1.0f, 0.45f, 0.35f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.1f }, { 0.35f, 0.0f, 0.1f, 1.0f, 0.55f, 0.0f, 0.1f, 1.0f, 0.55f, 0.45f, 0.45f, 0.1f }, { 0.35f, 0.0f, 0.1f, 1.0f, 0.55f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.45f, 0.1f, 0.55f, 0.0f, 0.1f, 0.45f, 0.55f, 0.35f, 0.45f, 0.1f }, { 0.45f, 0.0f, 0.1f, 1.0f, 0.0f, 0.35f, 0.55f, 0.1f, 0.0f, 0.55f, 0.55f, 0.1f }, { 0.35f, 0.0f, 0.1f, 1.0f, 0.55f, 0.0f, 0.1f, 1.0f, 0.0f, 0.45f, 0.35f, 0.1f }, { 0.55f, 0.0f, 0.1f, 1.0f, 0.35f, 0.0f, 0.1f, 0.45f, 0.0f, 0.35f, 0.45f, 0.1f, 0.35f, 0.55f, 0.1f, 0.45f, 0.0f, 0.55f, 0.45f, 0.1f }, { 0.45f, 0.0f, 0.1f, 0.35f, 0.0f, 0.35f, 1.0f, 0.1f, 0.0f, 0.55f, 1.0f, 0.1f }, { 0.35f, 0.0f, 0.1f, 0.55f, 0.55f, 0.0f, 0.1f, 0.55f, 0.0f, 0.45f, 1.0f, 0.1f }, { 0.0f, 0.55f, 1.0f, 0.1f, 0.55f, 0.0f, 0.1f, 0.45f, 0.55f, 0.35f, 0.45f, 0.1f, 0.35f, 0.0f, 0.1f, 0.45f, 0.0f, 0.35f, 0.45f, 0.1f }, { 0.45f, 0.55f, 0.1f, 0.45f, 0.0f, 0.35f, 1.0f, 0.1f, 0.0f, 0.55f, 1.0f, 0.1f }, { 0.35f, 0.45f, 0.1f, 0.55f, 0.55f, 0.45f, 0.1f, 0.55f, 0.0f, 0.45f, 1.0f, 0.1f }, { 0.0f, 0.35f, 1.0f, 0.1f, 0.55f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.45f, 0.1f, 0.35f, 0.55f, 0.1f, 0.45f, 0.0f, 0.55f, 0.45f, 0.1f }, { 0.45f, 0.0f, 0.1f, 1.0f, 0.0f, 0.35f, 1.0f, 0.1f, 0.0f, 0.55f, 1.0f, 0.1f }, { 0.35f, 0.0f, 0.1f, 1.0f, 0.55f, 0.0f, 0.1f, 1.0f, 0.0f, 0.45f, 1.0f, 0.1f }, { 0.55f, 0.55f, 0.1f, 0.45f, 0.55f, 0.55f, 0.45f, 0.1f, 0.55f, 0.0f, 0.1f, 0.45f, 0.55f, 0.35f, 0.45f, 0.1f, 0.35f, 0.0f, 0.1f, 0.45f, 0.0f, 0.35f, 0.45f, 0.1f, 0.35f, 0.55f, 0.1f, 0.45f, 0.0f, 0.55f, 0.45f, 0.1f }, new float[0], new float[0], new float[0], new float[0], new float[0], new float[0], new float[0], { 0.0f, 0.45f, 0.55f, 0.1f }, { 0.45f, 0.45f, 0.1f, 0.55f }, { 0.45f, 0.45f, 0.55f, 0.1f }, { 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.4f, 0.6f, 0.2f }, { 0.4f, 0.4f, 0.2f, 0.6f }, { 0.4f, 0.4f, 0.6f, 0.2f }, { 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.45f, 0.55f, 0.1f, 0.4f, 0.4f, 0.6f, 0.2f }, { 0.45f, 0.45f, 0.1f, 0.55f, 0.4f, 0.0f, 0.2f, 0.6f }, { 0.0f, 0.4f, 0.6f, 0.2f, 0.45f, 0.45f, 0.55f, 0.1f }, { 0.4f, 0.4f, 0.2f, 0.6f, 0.45f, 0.0f, 0.1f, 0.55f }, { 0.0f, 0.5f, 1.0f, 0.5f }, { 0.0f, 0.0f, 1.0f, 0.125f }, { 0.0f, 0.0f, 1.0f, 0.25f }, { 0.0f, 0.0f, 1.0f, 0.375f }, { 0.0f, 0.0f, 1.0f, 0.5f }, { 0.0f, 0.0f, 1.0f, 0.625f }, { 0.0f, 0.0f, 1.0f, 0.75f }, { 0.0f, 0.0f, 1.0f, 0.875f }, { 0.0f, 0.0f, 1.0f, 1.0f }, { 0.0f, 0.0f, 0.875f, 1.0f }, { 0.0f, 0.0f, 0.75f, 1.0f }, { 0.0f, 0.0f, 0.625f, 1.0f }, { 0.0f, 0.0f, 0.5f, 1.0f }, { 0.0f, 0.0f, 0.375f, 1.0f }, { 0.0f, 0.0f, 0.25f, 1.0f }, { 0.0f, 0.0f, 0.125f, 1.0f }, { 0.5f, 0.0f, 0.5f, 1.0f }, new float[0], new float[0], new float[0], { 0.0f, 0.875f, 1.0f, 0.125f }, { 0.875f, 0.0f, 0.125f, 1.0f }, { 0.0f, 0.0f, 0.5f, 0.5f }, { 0.5f, 0.0f, 0.5f, 0.5f }, { 0.0f, 0.5f, 0.5f, 0.5f }, { 0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 0.0f, 0.5f, 0.5f }, { 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f }, { 0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 0.5f }, { 0.5f, 0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 0.5f, 0.5f }, { 0.5f, 0.5f, 0.5f, 0.5f }, { 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f }, { 0.5f, 0.0f, 0.5f, 1.0f, 0.0f, 0.0f, 0.5f, 0.5f } };
    }
}