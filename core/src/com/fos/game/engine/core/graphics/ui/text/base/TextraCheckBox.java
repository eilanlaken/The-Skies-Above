// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

public class TextraCheckBox extends TextraButton
{
    private final Image image;
    private final Cell<?> imageCell;
    private CheckBox.CheckBoxStyle style;
    
    public TextraCheckBox(@Null final String text, final Skin skin) {
        this(text, (CheckBox.CheckBoxStyle)skin.get((Class)CheckBox.CheckBoxStyle.class));
    }
    
    public TextraCheckBox(@Null final String text, final Skin skin, final String styleName) {
        this(text, (CheckBox.CheckBoxStyle)skin.get(styleName, (Class)CheckBox.CheckBoxStyle.class));
    }
    
    public TextraCheckBox(@Null final String text, final CheckBox.CheckBoxStyle style) {
        this(text, style, new Font(style.font));
    }
    
    public TextraCheckBox(@Null final String text, final Skin skin, final Font replacementFont) {
        this(text, (CheckBox.CheckBoxStyle)skin.get((Class)CheckBox.CheckBoxStyle.class), replacementFont);
    }
    
    public TextraCheckBox(@Null final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, (CheckBox.CheckBoxStyle)skin.get(styleName, (Class)CheckBox.CheckBoxStyle.class), replacementFont);
    }
    
    public TextraCheckBox(@Null final String text, final CheckBox.CheckBoxStyle style, final Font replacementFont) {
        super(text, (TextButton.TextButtonStyle)style, replacementFont);
        final TextraLabel label = this.getTextraLabel();
        label.setAlignment(8);
        (this.image = this.newImage()).setDrawable(style.checkboxOff);
        this.clearChildren();
        this.imageCell = (Cell<?>)this.add((Actor)this.image);
        this.add((Actor)label);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }
    
    protected Image newImage() {
        return new Image((Drawable)null, Scaling.none);
    }
    
    @Override
    public void setStyle(final Button.ButtonStyle style) {
        if (!(style instanceof CheckBox.CheckBoxStyle)) {
            throw new IllegalArgumentException("style must be a CheckBoxStyle.");
        }
        this.style = (CheckBox.CheckBoxStyle)style;
        super.setStyle(style);
    }
    
    @Override
    public void setStyle(final Button.ButtonStyle style, final boolean makeGridGlyphs) {
        if (!(style instanceof CheckBox.CheckBoxStyle)) {
            throw new IllegalArgumentException("style must be a CheckBoxStyle.");
        }
        this.style = (CheckBox.CheckBoxStyle)style;
        super.setStyle(style, makeGridGlyphs);
    }
    
    @Override
    public void setStyle(final Button.ButtonStyle style, final Font font) {
        if (!(style instanceof CheckBox.CheckBoxStyle)) {
            throw new IllegalArgumentException("style must be a CheckBoxStyle.");
        }
        this.style = (CheckBox.CheckBoxStyle)style;
        super.setStyle(style, font);
    }
    
    public CheckBox.CheckBoxStyle getStyle() {
        return this.style;
    }
    
    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Drawable checkbox = null;
        if (this.isDisabled()) {
            if (this.isChecked() && this.style.checkboxOnDisabled != null) {
                checkbox = this.style.checkboxOnDisabled;
            }
            else {
                checkbox = this.style.checkboxOffDisabled;
            }
        }
        if (checkbox == null) {
            final boolean over = this.isOver() && !this.isDisabled();
            if (this.isChecked() && this.style.checkboxOn != null) {
                checkbox = ((over && this.style.checkboxOnOver != null) ? this.style.checkboxOnOver : this.style.checkboxOn);
            }
            else if (over && this.style.checkboxOver != null) {
                checkbox = this.style.checkboxOver;
            }
            else {
                checkbox = this.style.checkboxOff;
            }
        }
        this.image.setDrawable(checkbox);
        super.draw(batch, parentAlpha);
    }
    
    public Image getImage() {
        return this.image;
    }
    
    public Cell<?> getImageCell() {
        return this.imageCell;
    }
}
