// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;

public interface ColorLookup
{
    public static final GdxColorLookup INSTANCE = GdxColorLookup.INSTANCE;
    public static final ColorLookup DESCRIPTIVE = new ColorLookup() {
        @Override
        public int getRgba(final String description) {
            return ColorUtils.describe(description);
        }
    };
    
    int getRgba(final String p0);
    
    public static class GdxColorLookup implements ColorLookup
    {
        public static final GdxColorLookup INSTANCE;
        
        private GdxColorLookup() {
        }
        
        @Override
        public int getRgba(final String key) {
            final Color c = Colors.get(key);
            return (c == null) ? 256 : Color.rgba8888(c);
        }
        
        static {
            INSTANCE = new GdxColorLookup();
        }
    }
}
