// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

import com.badlogic.gdx.utils.NumberUtils;

public class NoiseUtils
{
    public static float noise1D(float x, final int seed) {
        x += seed * 5.9604645E-8f;
        final int xFloor = (x >= 0.0f) ? ((int)x) : ((int)x - 1);
        final int rise = 1 - (((x >= 0.0f) ? ((int)(x + x)) : ((int)(x + x) - 1)) & 0x2);
        x -= xFloor;
        final float h = NumberUtils.intBitsToFloat((int)(((long)(seed + xFloor) ^ 0x9E3779B97F4A7C15L) * -3335678366873096957L >>> 41) | 0x42000000) - 48.0f;
        x *= x - 1.0f;
        return rise * x * x * h;
    }
    
    public static float octaveNoise1D(final float x, final int seed) {
        return noise1D(x, seed) * 0.6666667f + noise1D(x * 1.9f, ~seed) * 0.33333334f;
    }
}
