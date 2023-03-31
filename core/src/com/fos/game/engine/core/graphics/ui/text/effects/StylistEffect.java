// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class StylistEffect extends Effect
{
    private long effects;
    private boolean all;
    
    public StylistEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.effects = 0L;
        this.all = false;
        label.trackingInput = true;
        if (params.length > 0 && this.paramAsBoolean(params[0])) {
            this.effects |= 0x40000000L;
        }
        if (params.length > 1 && this.paramAsBoolean(params[1])) {
            this.effects |= 0x20000000L;
        }
        if (params.length > 2 && this.paramAsBoolean(params[2])) {
            this.effects |= 0x10000000L;
        }
        if (params.length > 3 && this.paramAsBoolean(params[3])) {
            this.effects |= 0x8000000L;
        }
        if (params.length > 4) {
            this.effects |= ((long)this.paramAsFloat(params[4], 0.0f) & 0x3L) << 25;
        }
        if (params.length > 5) {
            this.all = this.paramAsBoolean(params[5]);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        if (this.all) {
            if (this.label.overIndex < this.indexStart || this.label.overIndex > this.indexEnd) {
                this.label.setInWorkingLayout(globalIndex, glyph & 0xFFFFFFFF01FFFFFFL);
                return;
            }
        }
        else if (this.label.overIndex != globalIndex) {
            this.label.setInWorkingLayout(globalIndex, glyph & 0xFFFFFFFF01FFFFFFL);
            return;
        }
        this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFF01FFFFFFL) | this.effects);
    }
}
