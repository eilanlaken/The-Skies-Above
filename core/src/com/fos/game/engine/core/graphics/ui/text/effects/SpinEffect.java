// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class SpinEffect extends Effect
{
    private static final float DEFAULT_INTENSITY = 1.0f;
    private float intensity;
    private float rotations;
    private boolean elastic;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public SpinEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.intensity = 1.0f;
        this.rotations = 1.0f;
        this.elastic = false;
        this.timePassedByGlyphIndex = new IntFloatMap();
        if (params.length > 0) {
            this.intensity = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.rotations = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.elastic = this.paramAsBoolean(params[2]);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float realIntensity = this.intensity * (this.elastic ? 3.0f : 1.0f) * 1.0f;
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / realIntensity, 0.0f, 1.0f);
        final Interpolation interpolation = (Interpolation)(this.elastic ? Interpolation.bounceOut : Interpolation.pow3Out);
        final float interpolatedValue = interpolation.apply(progress) * 360.0f * this.rotations;
        this.label.rotations.incr(globalIndex, interpolatedValue);
    }
}
