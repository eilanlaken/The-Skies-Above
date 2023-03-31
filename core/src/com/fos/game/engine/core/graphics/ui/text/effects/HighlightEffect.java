// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;

public class HighlightEffect extends Effect
{
    private static final float DEFAULT_DISTANCE = 0.975f;
    private static final float DEFAULT_FREQUENCY = 2.0f;
    private static final int DEFAULT_COLOR = -2;
    private int baseColor;
    private float distance;
    private float frequency;
    private float saturation;
    private float brightness;
    private boolean all;
    
    public HighlightEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.baseColor = -2;
        this.distance = 1.0f;
        this.frequency = 1.0f;
        this.saturation = 1.0f;
        this.brightness = 0.5f;
        this.all = false;
        label.trackingInput = true;
        if (params.length > 0) {
            this.baseColor = this.paramAsColor(params[0]);
            if (this.baseColor == 256) {
                this.baseColor = -2;
            }
        }
        if (params.length > 1) {
            this.distance = this.paramAsFloat(params[1], 1.0f);
        }
        if (params.length > 2) {
            this.frequency = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.saturation = this.paramAsFloat(params[3], 1.0f);
        }
        if (params.length > 4) {
            this.brightness = this.paramAsFloat(params[4], 0.5f);
        }
        if (params.length > 5) {
            this.all = this.paramAsBoolean(params[5]);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        if (this.all) {
            if (this.label.overIndex < this.indexStart || this.label.overIndex > this.indexEnd) {
                this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.baseColor << 32);
                return;
            }
        }
        else if (this.label.overIndex != globalIndex) {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.baseColor << 32);
            return;
        }
        final float distanceMod = 1.0f / this.distance * 0.024999976f;
        final float frequencyMod = 1.0f / this.frequency * 2.0f;
        final float progress = this.calculateProgress(frequencyMod, distanceMod * localIndex, false);
        this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long) ColorUtils.hsl2rgb(progress, this.saturation, this.brightness, 1.0f) << 32);
    }
}
