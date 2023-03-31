// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class WaveEffect extends Effect
{
    private static final float DEFAULT_FREQUENCY = 15.0f;
    private static final float DEFAULT_DISTANCE = 0.33f;
    private static final float DEFAULT_INTENSITY = 0.5f;
    private float distance;
    private float frequency;
    private float intensity;
    
    public WaveEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.frequency = 1.0f;
        this.intensity = 1.0f;
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.frequency = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.intensity = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.duration = this.paramAsFloat(params[3], -1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float progressModifier = 1.0f / this.intensity * 0.5f;
        final float normalFrequency = 1.0f / this.frequency * 15.0f;
        final float progressOffset = localIndex / normalFrequency;
        final float progress = this.calculateProgress(progressModifier, progressOffset);
        float y = this.label.getLineHeight(globalIndex) * this.distance * Interpolation.sine.apply(-1.0f, 1.0f, progress) * 0.33f;
        final float fadeout = this.calculateFadeout();
        y *= fadeout;
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
