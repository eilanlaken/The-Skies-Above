// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class EmergeEffect extends Effect
{
    private static final float DEFAULT_INTENSITY = 0.125f;
    private float intensity;
    private boolean elastic;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public EmergeEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.intensity = 4.0f;
        this.elastic = false;
        this.timePassedByGlyphIndex = new IntFloatMap();
        if (params.length > 0) {
            this.intensity = 1.0f / this.paramAsFloat(params[0], 0.25f);
        }
        if (params.length > 1) {
            this.elastic = this.paramAsBoolean(params[1]);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float realIntensity = this.intensity * (this.elastic ? 3.0f : 1.0f) * 0.125f;
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / realIntensity, 0.0f, 1.0f);
        final Interpolation interpolation = (Interpolation)(this.elastic ? Interpolation.swingOut : Interpolation.sine);
        final float interpolatedValue = interpolation.apply(progress);
        this.label.sizing.incr(globalIndex << 1 | 0x1, interpolatedValue - 1.0f);
    }
}
