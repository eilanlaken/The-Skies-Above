// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.badlogic.gdx.Gdx;
import com.fos.game.engine.core.graphics.ui.text.utils.StringUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class LinkEffect extends Effect
{
    private String link;
    
    public LinkEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.link = "https://libgdx.com";
        label.trackingInput = true;
        if (params.length > 0) {
            this.link = StringUtils.join(";", (CharSequence[])params);
        }
    }
    
    @Override
    protected void onApply(final long glyph, final int localIndex, final int globalIndex, final float delta) {
        if (this.label.lastTouchedIndex == globalIndex) {
            this.label.lastTouchedIndex = -1;
            Gdx.net.openURI(this.link);
        }
    }
}
