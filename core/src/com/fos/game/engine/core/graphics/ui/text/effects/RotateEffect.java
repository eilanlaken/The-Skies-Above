// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;

public class RotateEffect extends Effect
{
    private float rotation;
    
    public RotateEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.rotation = 90.0f;
        if (params.length > 0) {
            this.rotation = this.paramAsFloat(params[0], 90.0f);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        this.label.rotations.incr(globalIndex, this.rotation);
    }
}
