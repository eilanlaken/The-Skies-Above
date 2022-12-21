// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class HeartbeatEffect extends Effect
{
    private static final float DEFAULT_FREQUENCY = 1.0f;
    private static final float DEFAULT_DISTANCE = 0.5f;
    private float distance;
    private float frequency;
    
    public HeartbeatEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.frequency = 1.0f;
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.frequency = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.duration = this.paramAsFloat(params[2], -1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float progress = this.totalTime * this.frequency * 360.0f * 1.0f;
        final float c = MathUtils.cosDeg(progress);
        final float s = MathUtils.sinDeg(progress);
        float x = this.distance * Math.max(-0.125f, Math.max(c * c * c, s * s * s)) * 0.5f;
        final float fadeout = this.calculateFadeout();
        x *= fadeout;
        this.label.sizing.incr(globalIndex << 1, x);
        this.label.sizing.incr(globalIndex << 1 | 0x1, x);
    }
}
