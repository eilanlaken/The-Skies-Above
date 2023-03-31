// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.base.Effect;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.utils.StringUtils;

public class TriggerEffect extends Effect
{
    private String event;
    
    public TriggerEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.event = "start";
        label.trackingInput = true;
        if (params.length > 0) {
            this.event = StringUtils.join(";", (CharSequence[])params);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        if (this.label.lastTouchedIndex == globalIndex) {
            this.label.lastTouchedIndex = -1;
            this.label.triggerEvent(this.event, true);
        }
    }
}
