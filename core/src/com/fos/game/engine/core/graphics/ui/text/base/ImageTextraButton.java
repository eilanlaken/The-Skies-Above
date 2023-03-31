// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

public class ImageTextraButton extends Button
{
    private final Image image;
    private TextraLabel label;
    private ImageTextButton.ImageTextButtonStyle style;
    
    public ImageTextraButton(@Null final String text, final Skin skin) {
        this(text, (ImageTextButton.ImageTextButtonStyle)skin.get((Class)ImageTextButton.ImageTextButtonStyle.class));
        this.setSkin(skin);
    }
    
    public ImageTextraButton(@Null final String text, final Skin skin, final String styleName) {
        this(text, (ImageTextButton.ImageTextButtonStyle)skin.get(styleName, (Class)ImageTextButton.ImageTextButtonStyle.class));
        this.setSkin(skin);
    }
    
    public ImageTextraButton(@Null final String text, final ImageTextButton.ImageTextButtonStyle style) {
        this(text, style, new Font(style.font));
    }
    
    public ImageTextraButton(@Null final String text, final Skin skin, final Font replacementFont) {
        this(text, (ImageTextButton.ImageTextButtonStyle)skin.get((Class)ImageTextButton.ImageTextButtonStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public ImageTextraButton(@Null final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, (ImageTextButton.ImageTextButtonStyle)skin.get(styleName, (Class)ImageTextButton.ImageTextButtonStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public ImageTextraButton(@Null final String text, final ImageTextButton.ImageTextButtonStyle style, final Font replacementFont) {
        super((Button.ButtonStyle)style);
        this.style = style;
        this.defaults().space(3.0f);
        this.image = this.newImage();
        (this.label = this.newLabel(text, replacementFont, style.fontColor)).setAlignment(1);
        this.add((Actor)this.image);
        this.add((Actor)this.label);
        this.setStyle((Button.ButtonStyle)style, replacementFont);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }
    
    protected Image newImage() {
        return new Image((Drawable)null, Scaling.fit);
    }
    
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TextraLabel(text, style);
    }
    
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return new TextraLabel(text, font, color);
    }
    
    public void setStyle(final Button.ButtonStyle style) {
        if (!(style instanceof ImageTextButton.ImageTextButtonStyle)) {
            throw new IllegalArgumentException("style must be a ImageTextButtonStyle.");
        }
        this.style = (ImageTextButton.ImageTextButtonStyle)style;
        super.setStyle(style);
        if (this.image != null) {
            this.updateImage();
        }
        if (this.label != null) {
            final ImageTextButton.ImageTextButtonStyle textButtonStyle = (ImageTextButton.ImageTextButtonStyle)style;
            this.label.setFont(new Font(textButtonStyle.font));
            final Color c = this.getFontColor();
            if (c != null) {
                this.label.setColor(c);
            }
        }
    }
    
    public void setStyle(final Button.ButtonStyle style, final boolean makeGridGlyphs) {
        if (!(style instanceof ImageTextButton.ImageTextButtonStyle)) {
            throw new IllegalArgumentException("style must be a ImageTextButtonStyle.");
        }
        this.style = (ImageTextButton.ImageTextButtonStyle)style;
        super.setStyle(style);
        if (this.image != null) {
            this.updateImage();
        }
        if (this.label != null) {
            final ImageTextButton.ImageTextButtonStyle textButtonStyle = (ImageTextButton.ImageTextButtonStyle)style;
            this.label.setFont(new Font(textButtonStyle.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs));
            final Color c = this.getFontColor();
            if (c != null) {
                this.label.setColor(c);
            }
        }
    }
    
    public void setStyle(final Button.ButtonStyle style, final Font font) {
        if (!(style instanceof ImageTextButton.ImageTextButtonStyle)) {
            throw new IllegalArgumentException("style must be a ImageTextButtonStyle.");
        }
        this.style = (ImageTextButton.ImageTextButtonStyle)style;
        super.setStyle(style);
        if (this.image != null) {
            this.updateImage();
        }
        if (this.label != null) {
            this.label.setFont(font);
            final Color c = this.getFontColor();
            if (c != null) {
                this.label.setColor(c);
            }
        }
    }
    
    public ImageTextButton.ImageTextButtonStyle getStyle() {
        return this.style;
    }
    
    @Null
    protected Drawable getImageDrawable() {
        if (this.isDisabled() && this.style.imageDisabled != null) {
            return this.style.imageDisabled;
        }
        if (this.isPressed()) {
            if (this.isChecked() && this.style.imageCheckedDown != null) {
                return this.style.imageCheckedDown;
            }
            if (this.style.imageDown != null) {
                return this.style.imageDown;
            }
        }
        if (this.isOver()) {
            if (this.isChecked()) {
                if (this.style.imageCheckedOver != null) {
                    return this.style.imageCheckedOver;
                }
            }
            else if (this.style.imageOver != null) {
                return this.style.imageOver;
            }
        }
        if (this.isChecked()) {
            if (this.style.imageChecked != null) {
                return this.style.imageChecked;
            }
            if (this.isOver() && this.style.imageOver != null) {
                return this.style.imageOver;
            }
        }
        return this.style.imageUp;
    }
    
    protected void updateImage() {
        this.image.setDrawable(this.getImageDrawable());
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
        this.updateImage();
        final Color c = this.getFontColor();
        if (c != null) {
            this.label.setColor(c);
        }
        super.draw(batch, parentAlpha);
    }
    
    public Image getImage() {
        return this.image;
    }
    
    public Cell<?> getImageCell() {
        return (Cell<?>)this.getCell((Actor)this.image);
    }
    
    public void setLabel(final TextraLabel label) {
        this.getLabelCell().setActor((Actor)label);
        this.label = label;
    }
    
    public TextraLabel getLabel() {
        return this.label;
    }
    
    public Cell<?> getLabelCell() {
        return (Cell<?>)this.getCell((Actor)this.label);
    }
    
    public void setText(final CharSequence text) {
        this.label.setText(text.toString());
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
        return ((className.indexOf(36) != -1) ? "ImageTextraButton " : "") + className + ": " + this.image.getDrawable() + " " + this.label.toString();
    }
    
    public void skipToTheEnd() {
        this.label.skipToTheEnd();
    }
}
