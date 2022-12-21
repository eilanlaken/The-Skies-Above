// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntArray;

public class ColorUtils
{
    private static final IntArray mixing;
    
    public static int hsl2rgb(final float h, final float s, final float l, final float a) {
        final float x = Math.min(Math.max(Math.abs(h * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        float y = h + 0.6666667f;
        float z = h + 0.33333334f;
        y -= (int)y;
        z -= (int)z;
        y = Math.min(Math.max(Math.abs(y * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        z = Math.min(Math.max(Math.abs(z * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        final float v = l + s * Math.min(l, 1.0f - l);
        final float d = 2.0f * (1.0f - l / (v + 1.0E-10f));
        return Color.rgba8888(v * MathUtils.lerp(1.0f, x, d), v * MathUtils.lerp(1.0f, y, d), v * MathUtils.lerp(1.0f, z, d), a);
    }
    
    public static int rgb2hsl(final float r, final float g, final float b, final float a) {
        float x;
        float y;
        float z;
        float w;
        if (g < b) {
            x = b;
            y = g;
            z = -1.0f;
            w = 0.6666667f;
        }
        else {
            x = g;
            y = b;
            z = 0.0f;
            w = -0.33333334f;
        }
        if (r < x) {
            z = w;
            w = r;
        }
        else {
            w = x;
            x = r;
        }
        final float d = x - Math.min(w, y);
        final float l = x * (1.0f - 0.5f * d / (x + 1.0E-10f));
        return Color.rgba8888(Math.abs(z + (w - y) / (6.0f * d + 1.0E-10f)), (x - l) / (Math.min(l, 1.0f - l) + 1.0E-10f), l, a);
    }
    
    public static int hsb2rgb(final float h, final float s, final float b, final float a) {
        final float x = Math.min(Math.max(Math.abs(h * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        float y = h + 0.6666667f;
        float z = h + 0.33333334f;
        y -= (int)y;
        z -= (int)z;
        y = Math.min(Math.max(Math.abs(y * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        z = Math.min(Math.max(Math.abs(z * 6.0f - 3.0f) - 1.0f, 0.0f), 1.0f);
        return Color.rgba8888(b * MathUtils.lerp(1.0f, x, s), b * MathUtils.lerp(1.0f, y, s), b * MathUtils.lerp(1.0f, z, s), a);
    }
    
    public static int rgb2hsb(final float r, final float g, final float b, final float a) {
        final float v = Math.max(Math.max(r, g), b);
        final float n = Math.min(Math.min(r, g), b);
        final float c = v - n;
        float h;
        if (c == 0.0f) {
            h = 0.0f;
        }
        else if (v == r) {
            h = (g - b) / c / 6.0f;
        }
        else if (v == g) {
            h = ((b - r) / c + 2.0f) / 6.0f;
        }
        else {
            h = ((r - g) / c + 4.0f) / 6.0f;
        }
        return Color.rgba8888(h, (v == 0.0f) ? 0.0f : (c / v), v, a);
    }
    
    public static float channel(final int color, final int channel) {
        return (color >>> 24 - ((channel & 0x3) << 3) & 0xFF) / 255.0f;
    }
    
    public static int channelInt(final int color, final int channel) {
        return color >>> 24 - ((channel & 0x3) << 3) & 0xFF;
    }
    
    public static int lerpColors(final int s, final int e, final float change) {
        final int sA = s & 0xFE;
        final int sB = s >>> 8 & 0xFF;
        final int sG = s >>> 16 & 0xFF;
        final int sR = s >>> 24 & 0xFF;
        final int eA = e & 0xFE;
        final int eB = e >>> 8 & 0xFF;
        final int eG = e >>> 16 & 0xFF;
        final int eR = e >>> 24 & 0xFF;
        return ((int)(sR + change * (eR - sR)) & 0xFF) << 24 | ((int)(sG + change * (eG - sG)) & 0xFF) << 16 | ((int)(sB + change * (eB - sB)) & 0xFF) << 8 | ((int)(sA + change * (eA - sA)) & 0xFE);
    }
    
    public static int mix(final int[] colors, int offset, final int size) {
        final int end = offset + size;
        if (colors == null || colors.length < end || offset < 0 || size <= 0) {
            return 256;
        }
        int result = 256;
        while (colors[offset] == 256) {
            ++offset;
        }
        if (offset < end) {
            result = colors[offset];
        }
        for (int i = offset + 1, o = end, denom = 2; i < o; ++i, ++denom) {
            if (colors[i] != 256) {
                result = lerpColors(result, colors[i], 1.0f / denom);
            }
            else {
                --denom;
            }
        }
        return result;
    }
    
    public static int lighten(final int start, final float change) {
        final int r = start >>> 24;
        final int g = start >>> 16 & 0xFF;
        final int b = start >>> 8 & 0xFF;
        final int a = start & 0xFE;
        return ((int)(r + (255 - r) * change) & 0xFF) << 24 | ((int)(g + (255 - g) * change) & 0xFF) << 16 | ((int)(b + (255 - b) * change) & 0xFF) << 8 | a;
    }
    
    public static int darken(final int start, final float change) {
        final int r = start >>> 24;
        final int g = start >>> 16 & 0xFF;
        final int b = start >>> 8 & 0xFF;
        final int a = start & 0xFE;
        final float ch = 1.0f - change;
        return ((int)(r * ch) & 0xFF) << 24 | ((int)(g * ch) & 0xFF) << 16 | ((int)(b * ch) & 0xFF) << 8 | a;
    }
    
    public static int dullen(final int start, final float change) {
        final float rc = 0.32627f;
        final float gc = 0.3678f;
        final float bc = 0.30593002f;
        final int r = start >>> 24;
        final int g = start >>> 16 & 0xFF;
        final int b = start >>> 8 & 0xFF;
        final int a = start & 0xFE;
        final float ch = 1.0f - change;
        final float rw = change * 0.32627f;
        final float gw = change * 0.3678f;
        final float bw = change * 0.30593002f;
        return (int)Math.min(Math.max(r * (rw + ch) + g * rw + b * rw, 0.0f), 255.0f) << 24 | (int)Math.min(Math.max(r * gw + g * (gw + ch) + b * gw, 0.0f), 255.0f) << 16 | (int)Math.min(Math.max(r * bw + g * bw + b * (bw + ch), 0.0f), 255.0f) << 8 | a;
    }
    
    public static int enrich(final int start, final float change) {
        final float rc = -0.32627f;
        final float gc = -0.3678f;
        final float bc = -0.30593002f;
        final int r = start >>> 24;
        final int g = start >>> 16 & 0xFF;
        final int b = start >>> 8 & 0xFF;
        final int a = start & 0xFE;
        final float ch = 1.0f + change;
        final float rw = change * -0.32627f;
        final float gw = change * -0.3678f;
        final float bw = change * -0.30593002f;
        return (int)Math.min(Math.max(r * (rw + ch) + g * rw + b * rw, 0.0f), 255.0f) << 24 | (int)Math.min(Math.max(r * gw + g * (gw + ch) + b * gw, 0.0f), 255.0f) << 16 | (int)Math.min(Math.max(r * bw + g * bw + b * (bw + ch), 0.0f), 255.0f) << 8 | a;
    }
    
    public static int offset(final int color, final float power) {
        return lerpColors(color, color ^ 0x80808000, power);
    }
    
    public static int offsetLightness(final int color, final float power) {
        final int light = (color >>> 24) * 3 + (color >>> 14 & 0x3FC) + (color >>> 8 & 0xFF);
        if (light < 808) {
            return lighten(color, power);
        }
        return darken(color, power);
    }
    
    public static int multiplyAlpha(final int color, final float multiplier) {
        return (color & 0xFFFFFF00) | Math.min(Math.max((int)((color & 0xFF) * multiplier), 0), 255);
    }
    
    public static int[][] multiplyAllAlpha(final int[][] colors, final float multiplier) {
        for (int x = 0; x < colors.length; ++x) {
            for (int y = 0; y < colors[x].length; ++y) {
                colors[x][y] = multiplyAlpha(colors[x][y], multiplier);
            }
        }
        return colors;
    }
    
    public static int describe(final String description) {
        float lightness = 0.0f;
        float saturation = 0.0f;
        final String[] terms = description.split("[^a-zA-Z_]+");
        ColorUtils.mixing.clear();
        for (final String term : terms) {
            if (term != null) {
                if (!term.isEmpty()) {
                    final int len = term.length();
                    switch (term.charAt(0)) {
                        case 'L':
                        case 'l': {
                            if (len > 2 && (term.charAt(2) == 'g' || term.charAt(2) == 'G')) {
                                switch (len) {
                                    case 9: {
                                        lightness += 0.2f;
                                    }
                                    case 8: {
                                        lightness += 0.2f;
                                    }
                                    case 7: {
                                        lightness += 0.2f;
                                    }
                                    case 5: {
                                        lightness += 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        case 'B':
                        case 'b': {
                            if (len > 3 && (term.charAt(3) == 'g' || term.charAt(3) == 'G')) {
                                switch (len) {
                                    case 10: {
                                        lightness += 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 9: {
                                        lightness += 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 8: {
                                        lightness += 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 6: {
                                        lightness += 0.2f;
                                        saturation += 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        case 'P':
                        case 'p': {
                            if (len > 2 && (term.charAt(2) == 'l' || term.charAt(2) == 'L')) {
                                switch (len) {
                                    case 7:
                                    case 8: {
                                        lightness += 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 6: {
                                        lightness += 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 5: {
                                        lightness += 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 4: {
                                        lightness += 0.2f;
                                        saturation -= 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        case 'W':
                        case 'w': {
                            if (len > 3 && (term.charAt(3) == 'k' || term.charAt(3) == 'K')) {
                                switch (len) {
                                    case 8: {
                                        lightness -= 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 7: {
                                        lightness -= 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 6: {
                                        lightness -= 0.2f;
                                        saturation -= 0.2f;
                                    }
                                    case 4: {
                                        lightness -= 0.2f;
                                        saturation -= 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        case 'R':
                        case 'r': {
                            if (len > 1 && (term.charAt(1) == 'i' || term.charAt(1) == 'I')) {
                                switch (len) {
                                    case 8: {
                                        saturation += 0.2f;
                                    }
                                    case 7: {
                                        saturation += 0.2f;
                                    }
                                    case 6: {
                                        saturation += 0.2f;
                                    }
                                    case 4: {
                                        saturation += 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        case 'D':
                        case 'd': {
                            if (len > 1 && (term.charAt(1) == 'a' || term.charAt(1) == 'A')) {
                                switch (len) {
                                    case 8: {
                                        lightness -= 0.2f;
                                    }
                                    case 7: {
                                        lightness -= 0.2f;
                                    }
                                    case 6: {
                                        lightness -= 0.2f;
                                    }
                                    case 4: {
                                        lightness -= 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            if (len > 1 && (term.charAt(1) == 'u' || term.charAt(1) == 'U')) {
                                switch (len) {
                                    case 8: {
                                        saturation -= 0.2f;
                                    }
                                    case 7: {
                                        saturation -= 0.2f;
                                    }
                                    case 6: {
                                        saturation -= 0.2f;
                                    }
                                    case 4: {
                                        saturation -= 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            if (len > 3 && (term.charAt(3) == 'p' || term.charAt(3) == 'P')) {
                                switch (len) {
                                    case 8: {
                                        lightness -= 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 7: {
                                        lightness -= 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 6: {
                                        lightness -= 0.2f;
                                        saturation += 0.2f;
                                    }
                                    case 4: {
                                        lightness -= 0.2f;
                                        saturation += 0.2f;
                                        break;
                                    }
                                }
                                break;
                            }
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                        default: {
                            ColorUtils.mixing.add(Palette.NAMED.get(term, 256));
                            break;
                        }
                    }
                }
            }
        }
        int result = mix(ColorUtils.mixing.items, 0, ColorUtils.mixing.size);
        if (result == 256) {
            return result;
        }
        if (lightness > 0.0f) {
            result = lighten(result, lightness);
        }
        else if (lightness < 0.0f) {
            result = darken(result, -lightness);
        }
        if (saturation > 0.0f) {
            result = enrich(result, saturation);
        }
        else if (saturation < 0.0f) {
            result = dullen(result, -saturation);
        }
        return result;
    }
    
    static {
        mixing = new IntArray(4);
    }
}
