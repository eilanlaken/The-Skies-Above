// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.FloatArray;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class ShakeEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.12f;
    private static final float DEFAULT_INTENSITY = 0.5f;
    private final FloatArray lastOffsets;
    private float distance;
    private float intensity;
    
    public ShakeEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.lastOffsets = new FloatArray();
        this.distance = 1.0f;
        this.intensity = 1.0f;
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
        if (localIndex >= this.lastOffsets.size / 2) {
            this.lastOffsets.setSize(this.lastOffsets.size + 16);
        }
        final float lastX = this.lastOffsets.get(localIndex * 2);
        final float lastY = this.lastOffsets.get(localIndex * 2 + 1);
        float x = this.label.getLineHeight(globalIndex) * this.distance * MathUtils.random(-1.0f, 1.0f) * 0.12f;
        float y = this.label.getLineHeight(globalIndex) * this.distance * MathUtils.random(-1.0f, 1.0f) * 0.12f;
        final float normalIntensity = MathUtils.clamp(this.intensity * 0.5f, 0.0f, 1.0f);
        x = Interpolation.linear.apply(lastX, x, normalIntensity);
        y = Interpolation.linear.apply(lastY, y, normalIntensity);
        final float fadeout = this.calculateFadeout();
        x *= fadeout;
        y *= fadeout;
        x = (float)MathUtils.round(x);
        y = (float)MathUtils.round(y);
        this.lastOffsets.set(localIndex * 2, x);
        this.lastOffsets.set(localIndex * 2 + 1, y);
        this.label.offsets.incr(globalIndex << 1, x);
        this.label.offsets.incr(globalIndex << 1 | 0x1, y);
    }
}
