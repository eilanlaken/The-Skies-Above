// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;

public class RainbowEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.975f;
    private static final float DEFAULT_FREQUENCY = 2.0f;
    private float distance;
    private float frequency;
    private float saturation;
    private float brightness;
    
    public RainbowEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.distance = 1.0f;
        this.frequency = 1.0f;
        this.saturation = 1.0f;
        this.brightness = 0.5f;
        if (params.length > 0) {
            this.distance = this.paramAsFloat(params[0], 1.0f);
        }
        if (params.length > 1) {
            this.frequency = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.saturation = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.brightness = this.paramAsFloat(params[3], 0.5f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float distanceMod = 1.0f / this.distance * 0.024999976f;
        final float frequencyMod = 1.0f / this.frequency * 2.0f;
        final float progress = this.calculateProgress(frequencyMod, distanceMod * localIndex, false);
        this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long) ColorUtils.hsl2rgb(progress, this.saturation, this.brightness, 1.0f) << 32);
    }
}
