// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class SpiralEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 1.0f;
    private static final float DEFAULT_INTENSITY = 0.75f;
    private float distance;
    private float intensity;
    private float rotations;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public SpiralEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.intensity = 1.0f;
        this.rotations = 1.0f;
        this.timePassedByGlyphIndex = new IntFloatMap();
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.intensity = 1.0f / this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.rotations = this.paramAsFloat(params[2], 1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float realIntensity = this.intensity * 0.75f;
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / realIntensity, 0.0f, 1.0f);
        final float spin = 360.0f * this.rotations * progress;
        final float lineHeight = this.label.getLineHeight(globalIndex);
        final float x = lineHeight * this.distance * 1.0f * MathUtils.cosDeg(spin) * (1.0f - progress);
        final float y = lineHeight * this.distance * 1.0f * MathUtils.sinDeg(spin) * (1.0f - progress);
        this.label.offsets.incr(globalIndex << 1, x);
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
