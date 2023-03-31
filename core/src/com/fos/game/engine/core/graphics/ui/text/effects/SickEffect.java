// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.IntArray;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class SickEffect extends Effect
{
    private static final float DEFAULT_FREQUENCY = 50.0f;
    private static final float DEFAULT_DISTANCE = 0.125f;
    private static final float DEFAULT_INTENSITY = 1.0f;
    public float distance;
    public float intensity;
    private final IntArray indices;
    
    public SickEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.intensity = 1.0f;
        this.indices = new IntArray();
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.intensity = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.duration = this.paramAsFloat(params[2], -1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float progressModifier = 1.0f / this.intensity * 1.0f;
        final float progressOffset = localIndex / 50.0f;
        final float progress = this.calculateProgress(progressModifier, -progressOffset, false);
        if (progress < 0.01f && Math.random() > 0.25 && !this.indices.contains(localIndex)) {
            this.indices.add(localIndex);
        }
        if (progress > 0.95f) {
            this.indices.removeValue(localIndex);
        }
        if (!this.indices.contains(localIndex) && !this.indices.contains(localIndex - 1) && !this.indices.contains(localIndex - 2) && !this.indices.contains(localIndex + 2) && !this.indices.contains(localIndex + 1)) {
            return;
        }
        final float split = 0.5f;
        float interpolation;
        if (progress < split) {
            interpolation = Interpolation.pow2Out.apply(0.0f, 1.0f, progress / split);
        }
        else {
            interpolation = Interpolation.pow2In.apply(1.0f, 0.0f, (progress - split) / (1.0f - split));
        }
        float y = this.label.getLineHeight(globalIndex) * this.distance * interpolation * 0.125f;
        if (this.indices.contains(localIndex)) {
            y *= 2.15f;
        }
        if (this.indices.contains(localIndex - 1) || this.indices.contains(localIndex + 1)) {
            y *= 1.35f;
        }
        final float fadeout = this.calculateFadeout();
        y *= fadeout;
        this.label.offsets.incr(globalIndex << 1 | 0x1, -y);
    }
}
