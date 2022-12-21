// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TypingWindow extends TextraWindow
{
    public TypingWindow(final String title, final Skin skin) {
        super(title, skin);
    }
    
    public TypingWindow(final String title, final Skin skin, final String styleName) {
        super(title, skin, styleName);
    }
    
    public TypingWindow(final String title, final Window.WindowStyle style) {
        super(title, style);
    }
    
    public TypingWindow(final String title, final Window.WindowStyle style, final boolean makeGridGlyphs) {
        super(title, style, makeGridGlyphs);
    }
    
    public TypingWindow(final String title, final Skin skin, final Font replacementFont) {
        super(title, skin, replacementFont);
    }
    
    public TypingWindow(final String title, final Skin skin, final String styleName, final Font replacementFont) {
        super(title, skin, styleName, replacementFont);
    }
    
    public TypingWindow(final String title, final Window.WindowStyle style, final Font replacementFont) {
        super(title, style, replacementFont);
    }
    
    @Override
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TypingLabel(text, style);
    }
    
    @Override
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return new TypingLabel(text, font, color);
    }
}
