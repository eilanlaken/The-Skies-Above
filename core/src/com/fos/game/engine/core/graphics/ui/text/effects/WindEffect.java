// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.utils.NoiseUtils;

public class WindEffect extends Effect
{
    private static final float DEFAULT_SPACING = 10.0f;
    private static final float DEFAULT_DISTANCE = 0.33f;
    private static final float DEFAULT_INTENSITY = 0.375f;
    private static final float DISTANCE_X_RATIO = 1.5f;
    private static final float DISTANCE_Y_RATIO = 1.0f;
    private static final float IDEAL_DELTA = 60.0f;
    private float noiseCursorX;
    private float noiseCursorY;
    private float distanceX;
    private float distanceY;
    private float spacing;
    private float intensity;
    
    public WindEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.noiseCursorX = 0.0f;
        this.noiseCursorY = 0.0f;
        this.distanceX = 1.0f;
        this.distanceY = 1.0f;
        this.spacing = 1.0f;
        this.intensity = 1.0f;
        if (params.length > 0) {
            this.distanceX = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.distanceY = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.spacing = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.intensity = this.paramAsFloat(params[3], 1.0f);
        }
        if (params.length > 4) {
            this.duration = this.paramAsFloat(params[4], -1.0f);
        }
    }
    
    @Override
    public void update(final float delta) {
        super.update(delta);
        final float changeAmount = 0.15f * this.intensity * 0.375f * delta * 60.0f;
        this.noiseCursorX += changeAmount;
        this.noiseCursorY += changeAmount;
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float progressModifier = 0.375f / this.intensity;
        final float normalSpacing = 10.0f / this.spacing;
        final float progressOffset = localIndex / normalSpacing;
        final float progress = this.calculateProgress(progressModifier, progressOffset);
        final float indexOffset = localIndex * 0.05f * this.spacing;
        final float noiseX = NoiseUtils.octaveNoise1D(this.noiseCursorX + indexOffset, 123);
        final float noiseY = NoiseUtils.octaveNoise1D(this.noiseCursorY + indexOffset, -4321);
        final float lineHeight = this.label.getLineHeight(globalIndex);
        float x = lineHeight * noiseX * progress * this.distanceX * 1.5f * 0.33f;
        float y = lineHeight * noiseY * progress * this.distanceY * 1.0f * 0.33f;
        final float fadeout = this.calculateFadeout();
        x *= fadeout;
        y *= fadeout;
        x = Math.abs(x) * -Math.signum(this.distanceX);
        this.label.offsets.incr(globalIndex << 1, x);
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
