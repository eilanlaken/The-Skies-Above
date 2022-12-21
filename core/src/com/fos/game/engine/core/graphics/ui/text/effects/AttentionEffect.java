// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class AttentionEffect extends Effect
{
    private float spread;
    private float sizeY;
    
    public AttentionEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.spread = 5.0f;
        this.sizeY = 2.0f;
        if (params.length > 0) {
            this.spread = this.paramAsFloat(params[0], 100.0f) * 0.01f;
        }
        if (params.length > 1) {
            this.sizeY = this.paramAsFloat(params[1], 100.0f) * 0.01f;
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        final int distance = Math.abs(globalIndex - this.label.overIndex);
        if (distance <= this.spread) {
            this.label.sizing.incr(globalIndex << 1 | 0x1, (this.sizeY - 1.0f) * MathUtils.cosDeg(90.0f * distance / this.spread));
        }
    }
}
