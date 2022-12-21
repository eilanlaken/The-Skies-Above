// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class HangEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.7f;
    private static final float DEFAULT_INTENSITY = 1.5f;
    private float distance;
    private float intensity;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public HangEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.intensity = 1.0f;
        this.timePassedByGlyphIndex = new IntFloatMap();
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.intensity = this.paramAsFloat(params[1], 1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float realIntensity = this.intensity * 1.0f * 1.5f;
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / realIntensity, 0.0f, 1.0f);
        final float split = 0.7f;
        float interpolation;
        if (progress < split) {
            interpolation = Interpolation.pow3Out.apply(0.0f, 1.0f, progress / split);
        }
        else {
            interpolation = Interpolation.swing.apply(1.0f, 0.0f, (progress - split) / (1.0f - split));
        }
        final float distanceFactor = Interpolation.linear.apply(1.0f, 1.5f, progress);
        final float height = this.label.getLineHeight(globalIndex);
        float y = height * this.distance * distanceFactor * interpolation * 0.7f;
        final float fadeout = this.calculateFadeout();
        y *= fadeout;
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
