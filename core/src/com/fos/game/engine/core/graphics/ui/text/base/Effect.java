// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

public abstract class Effect
{
    private static final float FADEOUT_SPLIT = 0.25f;
    protected final TypingLabel label;
    public int indexStart;
    public int indexEnd;
    public float duration;
    protected float totalTime;
    
    public Effect(final TypingLabel label) {
        this.indexStart = -1;
        this.indexEnd = -1;
        this.duration = Float.POSITIVE_INFINITY;
        this.label = label;
    }
    
    public void update(final float delta) {
        this.totalTime += delta;
    }
    
    public final void apply(final long glyph, final int glyphIndex, final float delta) {
        final int localIndex = glyphIndex - this.indexStart;
        this.onApply(glyph, localIndex, glyphIndex, delta);
    }
    
    protected abstract void onApply(final long p0, final int p1, final int p2, final float p3);
    
    public boolean isFinished() {
        return this.totalTime > this.duration;
    }
    
    protected float calculateFadeout() {
        if (this.duration == Float.POSITIVE_INFINITY) {
            return 1.0f;
        }
        final float progress = MathUtils.clamp(this.totalTime / this.duration, 0.0f, 1.0f);
        if (progress < 0.25f) {
            return 1.0f;
        }
        return Interpolation.smooth.apply(1.0f, 0.0f, (progress - 0.25f) / 0.75f);
    }
    
    protected float calculateProgress(final float modifier) {
        return this.calculateProgress(modifier, 0.0f, true);
    }
    
    protected float calculateProgress(final float modifier, final float offset) {
        return this.calculateProgress(modifier, offset, true);
    }
    
    protected float calculateProgress(final float modifier, final float offset, final boolean pingpong) {
        float progress;
        for (progress = this.totalTime / modifier + offset; progress < 0.0f; progress += 2.0f) {}
        if (pingpong) {
            progress %= 2.0f;
            if (progress > 1.0f) {
                progress = 1.0f - (progress - 1.0f);
            }
        }
        else {
            progress %= 1.0f;
        }
        progress = MathUtils.clamp(progress, 0.0f, 1.0f);
        return progress;
    }
    
    protected float paramAsFloat(final String str, final float defaultValue) {
        return Parser.stringToFloat(str, defaultValue);
    }
    
    protected boolean paramAsBoolean(final String str) {
        return Parser.stringToBoolean(str);
    }
    
    protected int paramAsColor(final String str) {
        return Parser.stringToColor(this.label, str);
    }
}
