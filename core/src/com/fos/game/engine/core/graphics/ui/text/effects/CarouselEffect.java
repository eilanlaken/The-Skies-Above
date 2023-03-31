// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.Font;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class CarouselEffect extends Effect
{
    private static final float DEFAULT_FREQUENCY = 0.5f;
    private float frequency;
    
    public CarouselEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.frequency = 1.0f;
        if (params.length > 0) {
            this.frequency = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.duration = this.paramAsFloat(params[1], -1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float progress = this.totalTime * this.frequency * 360.0f * 0.5f;
        float s = MathUtils.sinDeg(progress);
        final float fadeout = this.calculateFadeout();
        s *= fadeout;
        final Font font = this.label.getFont();
        this.label.sizing.incr(globalIndex << 1, s - 1.0f);
        this.label.offsets.incr(globalIndex << 1, ((Font.GlyphRegion)font.mapping.get((int)(char)glyph, font.defaultValue)).xAdvance * (0.125f * s));
    }
}
