// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class BlinkEffect extends Effect
{
    private static final float DEFAULT_FREQUENCY = 1.0f;
    private int color1;
    private int color2;
    private float alpha1;
    private float alpha2;
    private float frequency;
    private float threshold;
    
    public BlinkEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.color1 = 256;
        this.color2 = 256;
        this.alpha1 = 1.0f;
        this.alpha2 = 0.0f;
        this.frequency = 1.0f;
        this.threshold = 0.5f;
        if (params.length > 0) {
            this.color1 = this.paramAsColor(params[0]);
            if (this.color1 == 256) {
                this.alpha1 = this.paramAsFloat(params[0], 0.0f);
            }
        }
        if (params.length > 1) {
            this.color2 = this.paramAsColor(params[1]);
            if (this.color2 == 256) {
                this.alpha2 = this.paramAsFloat(params[1], 1.0f);
            }
        }
        if (params.length > 2) {
            this.frequency = this.paramAsFloat(params[2], 1.0f);
        }
        if (params.length > 3) {
            this.threshold = this.paramAsFloat(params[3], 0.5f);
        }
        this.threshold = MathUtils.clamp(this.threshold, 0.0f, 1.0f);
        this.alpha1 = MathUtils.clamp(this.alpha1, 0.0f, 1.0f);
        this.alpha2 = MathUtils.clamp(this.alpha2, 0.0f, 1.0f);
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float frequencyMod = 1.0f / this.frequency * 1.0f;
        final float progress = this.calculateProgress(frequencyMod);
        if (progress <= this.threshold) {
            if (this.color1 == 256) {
                this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFF00FFFFFFFFL) | (long)(this.alpha1 * 255.0f) << 32);
            }
            else {
                this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.color1 << 32);
            }
        }
        else if (this.color1 == 256) {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFF00FFFFFFFFL) | (long)(this.alpha2 * 255.0f) << 32);
        }
        else {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)this.color2 << 32);
        }
    }
}
