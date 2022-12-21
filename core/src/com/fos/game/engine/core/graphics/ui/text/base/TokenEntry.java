// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

class TokenEntry implements Comparable<TokenEntry>
{
    String token;
    TokenCategory category;
    int index;
    int endIndex;
    float floatValue;
    String stringValue;
    Effect effect;
    
    TokenEntry(final String token, final TokenCategory category, final int index, final int endIndex, final float floatValue, final String stringValue) {
        this.token = token;
        this.category = category;
        this.index = index;
        this.endIndex = endIndex;
        this.floatValue = floatValue;
        this.stringValue = stringValue;
    }
    
    @Override
    public int compareTo(final TokenEntry o) {
        return Integer.compare(o.index, this.index);
    }
}
