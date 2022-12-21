// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

public final class StringUtils
{
    private StringUtils() {
    }
    
    public static String join(final CharSequence delimiter, final CharSequence... items) {
        if (items == null || items.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(items[0]);
        for (int i = 1; i < items.length; ++i) {
            sb.append(delimiter).append(items[i]);
        }
        return sb.toString();
    }
}
