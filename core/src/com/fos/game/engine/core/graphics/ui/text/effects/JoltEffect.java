// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.FloatArray;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class JoltEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.12f;
    private static final float DEFAULT_INTENSITY = 0.5f;
    private static final float DEFAULT_LIKELIHOOD = 0.05f;
    private final FloatArray lastOffsets;
    private float distance;
    private float intensity;
    private float likelihood;
    private int baseColor;
    private int joltColor;
    
    public JoltEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.lastOffsets = new FloatArray();
        this.distance = 1.0f;
        this.intensity = 1.0f;
        this.likelihood = 0.05f;
        this.baseColor = -1;
        this.joltColor = -30465;
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.intensity = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.duration = this.paramAsFloat(params[2], Float.POSITIVE_INFINITY);
        }
        if (params.length > 3) {
            this.likelihood = this.paramAsFloat(params[3], 0.05f);
        }
        if (params.length > 4) {
            final int c = this.paramAsColor(params[4]);
            if (c != 256) {
                this.baseColor = c;
            }
        }
        if (params.length > 5) {
            final int c = this.paramAsColor(params[5]);
            if (c != 256) {
                this.joltColor = c;
            }
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        if (localIndex >= this.lastOffsets.size / 2) {
            this.lastOffsets.setSize(this.lastOffsets.size + 16);
        }
        final float lastX = this.lastOffsets.get(localIndex * 2);
        final float lastY = this.lastOffsets.get(localIndex * 2 + 1);
        float x = 0.0f;
        float y = 0.0f;
        if (this.likelihood > determineFloat((TimeUtils.millis() >>> 10) * globalIndex + localIndex)) {
            x = this.label.getLineHeight(globalIndex) * this.distance * MathUtils.random(-1.0f, 1.0f) * 0.12f;
            y = this.label.getLineHeight(globalIndex) * this.distance * MathUtils.random(-1.0f, 1.0f) * 0.12f;
            final float normalIntensity = MathUtils.clamp(this.intensity * 0.5f, 0.0f, 1.0f);
            x = Interpolation.linear.apply(lastX, x, normalIntensity);
            y = Interpolation.linear.apply(lastY, y, normalIntensity);
            final float fadeout = this.calculateFadeout();
            x *= fadeout;
            y *= fadeout;
            x = (float)MathUtils.round(x);
            y = (float)MathUtils.round(y);
            if (fadeout > 0.0f) {
                this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.joltColor << 32);
            }
        }
        else {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.baseColor << 32);
        }
        this.lastOffsets.set(localIndex * 2, x);
        this.lastOffsets.set(localIndex * 2 + 1, y);
        this.label.offsets.incr(globalIndex << 1, x);
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
    
    private static float determineFloat(long state) {
        return (((state = (state * 7146057691288625177L ^ 0x9E3779B97F4A7C15L) * -4126379630918251389L) ^ state >>> 27) * -5840758589994634535L >>> 40) * 5.9604645E-8f;
    }
}
