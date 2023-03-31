// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class ScaleEffect extends Effect
{
    private float sizeX;
    private float sizeY;
    
    public ScaleEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.sizeX = 1.0f;
        this.sizeY = 2.0f;
        if (params.length > 0) {
            this.sizeX = this.paramAsFloat(params[0], 100.0f) * 0.01f;
            this.sizeY = this.paramAsFloat(params[0], 100.0f) * 0.01f;
        }
        if (params.length > 1) {
            this.sizeY = this.paramAsFloat(params[1], 100.0f) * 0.01f;
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        this.label.sizing.incr(globalIndex << 1, this.sizeX - 1.0f);
        this.label.sizing.incr(globalIndex << 1 | 0x1, this.sizeY - 1.0f);
    }
}
