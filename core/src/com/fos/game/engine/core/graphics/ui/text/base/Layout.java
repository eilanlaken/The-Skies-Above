// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Layout implements Pool.Poolable
{
    public static final Pool<Layout> POOL;
    protected Font font;
    protected final Array<Line> lines;
    protected int maxLines;
    protected boolean atLimit;
    protected String ellipsis;
    protected float targetWidth;
    protected float baseColor;
    
    public Layout() {
        this.lines = (Array<Line>)new Array(true, 8);
        this.maxLines = Integer.MAX_VALUE;
        this.atLimit = false;
        this.ellipsis = null;
        this.targetWidth = 0.0f;
        this.baseColor = Color.WHITE_FLOAT_BITS;
        this.lines.add(Line.POOL.obtain());
    }
    
    public Layout(final Font font) {
        this.lines = (Array<Line>)new Array(true, 8);
        this.maxLines = Integer.MAX_VALUE;
        this.atLimit = false;
        this.ellipsis = null;
        this.targetWidth = 0.0f;
        this.baseColor = Color.WHITE_FLOAT_BITS;
        this.font = font;
        this.lines.add(Line.POOL.obtain());
    }
    
    public Layout(final Layout other) {
        this.lines = (Array<Line>)new Array(true, 8);
        this.maxLines = Integer.MAX_VALUE;
        this.atLimit = false;
        this.ellipsis = null;
        this.targetWidth = 0.0f;
        this.baseColor = Color.WHITE_FLOAT_BITS;
        this.font = other.font;
        this.maxLines = other.maxLines;
        this.atLimit = other.atLimit;
        this.ellipsis = other.ellipsis;
        this.targetWidth = other.targetWidth;
        this.baseColor = other.baseColor;
        for (int i = 0; i < other.lines(); ++i) {
            final Line ln = (Line)Line.POOL.obtain();
            final Line o = (Line)other.lines.get(i);
            ln.glyphs.addAll(o.glyphs);
            this.lines.add(ln.size(o.width, o.height));
        }
    }
    
    public Layout font(final Font font) {
        if (this.font == null || !this.font.equals(font)) {
            this.font = font;
            Line.POOL.freeAll((Array)this.lines);
            this.lines.clear();
            this.lines.add(Line.POOL.obtain());
        }
        return this;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public void setFont(final Font font) {
        this.font(font);
    }
    
    public Layout add(final long glyph) {
        if (!this.atLimit) {
            if ((glyph & 0xFFFFL) == 0xAL) {
                this.pushLine();
            }
            else {
                ((Line)this.lines.peek()).glyphs.add(glyph);
            }
        }
        return this;
    }
    
    public Layout clear() {
        Line.POOL.freeAll((Array)this.lines);
        this.lines.clear();
        this.lines.add(Line.POOL.obtain());
        return this;
    }
    
    public float getWidth() {
        float w = 0.0f;
        for (int i = 0, n = this.lines.size; i < n; ++i) {
            w = Math.max(w, this.lines.get(i).width);
        }
        return w;
    }
    
    public float getHeight() {
        float h = 0.0f;
        for (int i = 0, n = this.lines.size; i < n; ++i) {
            h += this.lines.get(i).height;
        }
        return h;
    }
    
    public int lines() {
        return this.lines.size;
    }
    
    public Line getLine(final int i) {
        if (i >= this.lines.size) {
            return null;
        }
        return (Line)this.lines.get(i);
    }
    
    public Line peekLine() {
        return (Line)this.lines.peek();
    }
    
    public Line pushLine() {
        if (this.lines.size >= this.maxLines) {
            this.atLimit = true;
            return null;
        }
        final Line line = (Line)Line.POOL.obtain();
        final Line prev = (Line)this.lines.peek();
        prev.glyphs.add(10L);
        line.height = 0.0f;
        this.lines.add(line);
        return line;
    }
    
    public Line insertLine(final int index) {
        if (this.lines.size >= this.maxLines) {
            this.atLimit = true;
            return null;
        }
        if (index < 0 || index >= this.maxLines) {
            return null;
        }
        final Line line = (Line)Line.POOL.obtain();
        final Line prev = (Line)this.lines.get(index);
        prev.glyphs.add(10L);
        line.height = prev.height;
        this.lines.insert(index + 1, line);
        return line;
    }
    
    public float getTargetWidth() {
        return this.targetWidth;
    }
    
    public Layout setTargetWidth(final float targetWidth) {
        this.targetWidth = targetWidth;
        return this;
    }
    
    public float getBaseColor() {
        return this.baseColor;
    }
    
    public void setBaseColor(final float baseColor) {
        this.baseColor = baseColor;
    }
    
    public void setBaseColor(final Color baseColor) {
        this.baseColor = ((baseColor == null) ? Color.WHITE_FLOAT_BITS : baseColor.toFloatBits());
    }
    
    public int getMaxLines() {
        return this.maxLines;
    }
    
    public void setMaxLines(final int maxLines) {
        this.maxLines = Math.max(1, maxLines);
    }
    
    public String getEllipsis() {
        return this.ellipsis;
    }
    
    public void setEllipsis(final String ellipsis) {
        this.ellipsis = ellipsis;
    }
    
    public void reset() {
        this.targetWidth = 0.0f;
        this.baseColor = Color.WHITE_FLOAT_BITS;
        this.maxLines = Integer.MAX_VALUE;
        this.atLimit = false;
        this.ellipsis = null;
        this.font = null;
        Line.POOL.freeAll((Array)this.lines);
        this.lines.clear();
        this.lines.add(Line.POOL.obtain());
    }
    
    public StringBuilder appendIntoDirect(final StringBuilder sb) {
        for (int i = 0, n = this.lines.size; i < n; ++i) {
            final Line line = this.lines.get(i);
            for (int j = 0, ln = line.glyphs.size; j < ln; ++j) {
                final long gl = line.glyphs.get(j);
                sb.append((char)gl);
            }
        }
        return sb;
    }
    
    public StringBuilder appendInto(final StringBuilder sb) {
        for (int i = 0, n = this.lines.size; i < n; ++i) {
            final Line line = this.lines.get(i);
            for (int j = 0, ln = line.glyphs.size; j < ln; ++j) {
                final char gl = (char)line.glyphs.get(j);
                sb.append((gl == '\u0002') ? '[' : gl);
            }
        }
        return sb;
    }
    
    @Override
    public String toString() {
        return this.appendInto(new StringBuilder()).toString();
    }
    
    static {
        POOL = new Pool<Layout>() {
            protected Layout newObject() {
                return new Layout();
            }
        };
    }
}
