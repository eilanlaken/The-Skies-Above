// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class GradientEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.975f;
    private static final float DEFAULT_FREQUENCY = 2.0f;
    private int color1;
    private int color2;
    private float distance;
    private float frequency;
    
    public GradientEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.color1 = -1;
        this.color2 = -2004317953;
        this.distance = 1.0f;
        this.frequency = 1.0f;
        if (params.length > 0) {
            final int c = this.paramAsColor(params[0]);
            if (c != 256) {
                this.color1 = c;
            }
        }
        if (params.length > 1) {
            final int c = this.paramAsColor(params[1]);
            if (c != 256) {
                this.color2 = c;
            }
        }
        if (params.length > 2) {
            this.distance = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.frequency = this.paramAsFloat(params[3], 1.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float distanceMod = 1.0f / this.distance * 0.024999976f;
        final float frequencyMod = 1.0f / this.frequency * 2.0f;
        final float progress = this.calculateProgress(frequencyMod, distanceMod * localIndex, true);
        this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long) ColorUtils.lerpColors(this.color1, this.color2, progress) << 32);
    }
}
