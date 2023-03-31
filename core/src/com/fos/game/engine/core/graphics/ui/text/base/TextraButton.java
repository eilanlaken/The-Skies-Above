// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Null;

public class TextraButton extends Button
{
    private TextraLabel label;
    private TextButton.TextButtonStyle style;
    
    public TextraButton(@Null final String text, final Skin skin) {
        this(text, (TextButton.TextButtonStyle)skin.get((Class)TextButton.TextButtonStyle.class));
        this.setSkin(skin);
    }
    
    public TextraButton(@Null final String text, final Skin skin, final String styleName) {
        this(text, (TextButton.TextButtonStyle)skin.get(styleName, (Class)TextButton.TextButtonStyle.class));
        this.setSkin(skin);
    }
    
    public TextraButton(@Null final String text, final TextButton.TextButtonStyle style) {
        this(text, style, new Font(style.font));
    }
    
    public TextraButton(@Null final String text, final Skin skin, final Font replacementFont) {
        this(text, (TextButton.TextButtonStyle)skin.get((Class)TextButton.TextButtonStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public TextraButton(@Null final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, (TextButton.TextButtonStyle)skin.get(styleName, (Class)TextButton.TextButtonStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public TextraButton(@Null final String text, final TextButton.TextButtonStyle style, final Font replacementFont) {
        this.setStyle((Button.ButtonStyle)style, replacementFont);
        (this.label = this.newLabel(text, replacementFont, style.fontColor)).setAlignment(1);
        this.add((Actor)this.label).expand().fill();
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }
    
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TextraLabel(text, style);
    }
    
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return new TextraLabel(text, font, color);
    }
    
    public void setStyle(final Button.ButtonStyle style) {
        this.setStyle(style, false);
    }
    
    public void setStyle(final Button.ButtonStyle style, final boolean makeGridGlyphs) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        if (!(style instanceof TextButton.TextButtonStyle)) {
            throw new IllegalArgumentException("style must be a TextButtonStyle.");
        }
        this.style = (TextButton.TextButtonStyle)style;
        super.setStyle(style);
        if (this.label != null) {
            final TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            this.label.setFont(new Font(textButtonStyle.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs));
            if (textButtonStyle.fontColor != null) {
                this.label.setColor(textButtonStyle.fontColor);
            }
        }
    }
    
    public void setStyle(final Button.ButtonStyle style, final Font font) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        if (!(style instanceof TextButton.TextButtonStyle)) {
            throw new IllegalArgumentException("style must be a TextButtonStyle.");
        }
        this.style = (TextButton.TextButtonStyle)style;
        super.setStyle(style);
        if (this.label != null) {
            final TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            this.label.setFont(font);
            if (textButtonStyle.fontColor != null) {
                this.label.setColor(textButtonStyle.fontColor);
            }
        }
    }
    
    public TextButton.TextButtonStyle getStyle() {
        return this.style;
    }
    
    @Null
    protected Color getFontColor() {
        if (this.isDisabled() && this.style.disabledFontColor != null) {
            return this.style.disabledFontColor;
        }
        if (this.isPressed()) {
            if (this.isChecked() && this.style.checkedDownFontColor != null) {
                return this.style.checkedDownFontColor;
            }
            if (this.style.downFontColor != null) {
                return this.style.downFontColor;
            }
        }
        if (this.isOver()) {
            if (this.isChecked()) {
                if (this.style.checkedOverFontColor != null) {
                    return this.style.checkedOverFontColor;
                }
            }
            else if (this.style.overFontColor != null) {
                return this.style.overFontColor;
            }
        }
        final boolean focused = this.hasKeyboardFocus();
        if (this.isChecked()) {
            if (focused && this.style.checkedFocusedFontColor != null) {
                return this.style.checkedFocusedFontColor;
            }
            if (this.style.checkedFontColor != null) {
                return this.style.checkedFontColor;
            }
            if (this.isOver() && this.style.overFontColor != null) {
                return this.style.overFontColor;
            }
        }
        if (focused && this.style.focusedFontColor != null) {
            return this.style.focusedFontColor;
        }
        return this.style.fontColor;
    }
    
    public void draw(final Batch batch, final float parentAlpha) {
        final Color c = this.getFontColor();
        if (c != null) {
            this.label.setColor(c);
        }
        super.draw(batch, parentAlpha);
    }
    
    public void setTextraLabel(final TextraLabel label) {
        if (label == null) {
            throw new IllegalArgumentException("label cannot be null.");
        }
        this.getTextraLabelCell().setActor((Actor)label);
        this.label = label;
    }
    
    public TextraLabel getTextraLabel() {
        return this.label;
    }
    
    public Cell<TextraLabel> getTextraLabelCell() {
        return (Cell<TextraLabel>)this.getCell(this.label);
    }
    
    public TextraButton useIntegerPositions(final boolean integer) {
        this.label.getFont().integerPosition = integer;
        return this;
    }
    
    public void setText(@Null final String text) {
        this.label.setText(text);
    }
    
    public String getText() {
        return this.label.toString();
    }
    
    public String toString() {
        final String name = this.getName();
        if (name != null) {
            return name;
        }
        String className = this.getClass().getName();
        final int dotIndex = className.lastIndexOf(46);
        if (dotIndex != -1) {
            className = className.substring(dotIndex + 1);
        }
        return ((className.indexOf(36) != -1) ? "TextraButton " : "") + className + ": " + this.label.toString();
    }
    
    public void skipToTheEnd() {
        this.label.skipToTheEnd();
    }
}
