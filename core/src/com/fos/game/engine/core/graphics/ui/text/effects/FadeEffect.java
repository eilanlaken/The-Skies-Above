// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.effects;

import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fos.game.engine.core.graphics.ui.text.base.Effect;

public class FadeEffect extends Effect
{
    private int color1;
    private int color2;
    private float alpha1;
    private float alpha2;
    private float fadeDuration;
    private final IntFloatMap timePassedByGlyphIndex;
    
    public FadeEffect(final TypingLabel label, final String[] params) {
        super(label);
        this.color1 = 256;
        this.color2 = 256;
        this.alpha1 = 0.0f;
        this.alpha2 = 1.0f;
        this.fadeDuration = 1.0f;
        this.timePassedByGlyphIndex = new IntFloatMap();
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
            this.fadeDuration = this.paramAsFloat(params[2], 1.0f);
        }
    }
    
    @Override
    protected void onApply(long glyph, final int localIndex, final int globalIndex, final float delta) {
        final float timePassed = this.timePassedByGlyphIndex.getAndIncrement(localIndex, 0.0f, delta);
        final float progress = MathUtils.clamp(timePassed / this.fadeDuration, 0.0f, 1.0f);
        if (this.color1 == 256) {
            this.label.setInWorkingLayout(globalIndex, glyph = ((glyph & 0xFFFFFF00FFFFFFFFL) | (long)MathUtils.lerp((float)(glyph >>> 32 & 0xFFL), this.alpha1 * 255.0f, 1.0f - progress) << 32));
        }
        else {
            this.label.setInWorkingLayout(globalIndex, glyph = ((glyph & 0xFFFFFFFFL) | (long) ColorUtils.lerpColors((int)(glyph >>> 32), this.color1, 1.0f - progress) << 32));
        }
        if (this.color2 == 256) {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFF00FFFFFFFFL) | (long)MathUtils.lerp((float)(glyph >>> 32 & 0xFFL), this.alpha2 * 255.0f, progress) << 32);
        }
        else {
            this.label.setInWorkingLayout(globalIndex, (glyph & 0xFFFFFFFFL) | (long)ColorUtils.lerpColors((int)(glyph >>> 32), this.color2, progress) << 32);
        }
    }
}
