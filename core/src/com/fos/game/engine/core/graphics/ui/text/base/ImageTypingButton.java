// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ImageTypingButton extends ImageTextraButton
{
    public ImageTypingButton(final String text, final Skin skin) {
        super(text, skin);
    }
    
    public ImageTypingButton(final String text, final Skin skin, final String styleName) {
        super(text, skin, styleName);
    }
    
    public ImageTypingButton(final String text, final ImageTextButton.ImageTextButtonStyle style) {
        super(text, style);
    }
    
    public ImageTypingButton(final String text, final Skin skin, final Font replacementFont) {
        super(text, skin, replacementFont);
    }
    
    public ImageTypingButton(final String text, final Skin skin, final String styleName, final Font replacementFont) {
        super(text, skin, styleName, replacementFont);
    }
    
    public ImageTypingButton(final String text, final ImageTextButton.ImageTextButtonStyle style, final Font replacementFont) {
        super(text, style, replacementFont);
    }
    
    @Override
    protected TypingLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TypingLabel(text, style);
    }
    
    @Override
    protected TypingLabel newLabel(final String text, final Font font, final Color color) {
        return new TypingLabel(text, font, color);
    }
}
