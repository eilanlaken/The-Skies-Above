// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.utils.NoiseUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class CrowdEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 1.0f;
    private static final float DEFAULT_INTENSITY = 0.001f;
    private float distance;
    private float intensity;
    
    public CrowdEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 15.0f;
        this.intensity = 1.0f;
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 15.0f);
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
        float rot = NoiseUtils.octaveNoise1D((TimeUtils.millis() & 0xFFFFFFL) * this.intensity * 0.001f + globalIndex * 0.42f, globalIndex) * this.distance * 1.0f;
        final float fadeout = this.calculateFadeout();
        rot *= fadeout;
        this.label.rotations.incr(globalIndex, rot);
    }
}
