// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.utils.LongArray;
import com.badlogic.gdx.utils.Pool;

public class Line implements Pool.Poolable
{
    public static final Pool<Line> POOL;
    public final LongArray glyphs;
    public float width;
    public float height;
    
    public Line() {
        this.glyphs = new LongArray(16);
    }
    
    public Line(final int capacity) {
        this.glyphs = new LongArray(capacity);
    }
    
    public Line size(final float width, final float height) {
        this.width = width;
        this.height = height;
        return this;
    }
    
    public void reset() {
        this.glyphs.clear();
        this.width = 0.0f;
        this.height = 0.0f;
    }
    
    public StringBuilder appendTo(final StringBuilder sb) {
        sb.append("(\"");
        for (int i = 0, n = this.glyphs.size; i < n; ++i) {
            sb.append((char)this.glyphs.get(i));
        }
        sb.append("\" w=").append(this.width).append(" h=").append(this.height).append(')');
        return sb;
    }
    
    @Override
    public String toString() {
        return this.appendTo(new StringBuilder(this.glyphs.size + 20)).toString();
    }
    
    static {
        POOL = new Pool<Line>() {
            protected Line newObject() {
                return new Line();
            }
        };
    }
}
