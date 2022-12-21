// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Font;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class SquashEffect extends Effect
{
    private static final float DEFAULT_INTENSITY = 0.125f;
    private float intensity;
    private boolean elastic;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public SquashEffect(final TypingLabel label, final String[] params) {
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
        final Font font = this.label.getFont();
        if (progress < 0.4f) {
            final float interpolatedValue = 1.0f - Interpolation.sine.apply(progress * 2.5f) * 0.5f;
            this.label.offsets.incr(globalIndex << 1, ((Font.GlyphRegion)font.mapping.get((int)(char)glyph, font.defaultValue)).xAdvance * (0.125f - 0.125f * interpolatedValue));
            this.label.sizing.incr(globalIndex << 1, 1.0f - interpolatedValue);
            this.label.sizing.incr(globalIndex << 1 | 0x1, interpolatedValue - 1.0f);
        }
        else {
            final Interpolation interpolation = (Interpolation)(this.elastic ? Interpolation.swingOut : Interpolation.sine);
            final float interpolatedValue2 = interpolation.apply((progress - 0.4f) * 1.666f) * 0.5f + 0.5f;
            this.label.offsets.incr(globalIndex << 1, ((Font.GlyphRegion)font.mapping.get((int)(char)glyph, font.defaultValue)).xAdvance * (0.125f - 0.125f * interpolatedValue2));
            this.label.sizing.incr(globalIndex << 1, 1.0f - interpolatedValue2);
            this.label.sizing.incr(globalIndex << 1 | 0x1, interpolatedValue2 - 1.0f);
        }
    }
}
