// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class EaseEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.15f;
    private static final float DEFAULT_INTENSITY = 0.075f;
    private float distance;
    private float intensity;
    private boolean elastic;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public EaseEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = -2.0f;
        this.intensity = 1.0f;
        this.elastic = false;
        this.timePassedByGlyphIndex = new IntFloatMap();
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], -2.0f);
        }
        if (params.length > 1) {
            this.intensity = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.elastic = this.paramAsBoolean(params[2]);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float realIntensity = this.intensity * (this.elastic ? 3.0f : 1.0f) * 0.075f;
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / realIntensity, 0.0f, 1.0f);
        final Interpolation interpolation = (Interpolation)(this.elastic ? Interpolation.swingOut : Interpolation.sine);
        final float interpolatedValue = interpolation.apply(1.0f, 0.0f, progress);
        final float y = this.label.getLineHeight(globalIndex) * this.distance * interpolatedValue * 0.15f;
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
