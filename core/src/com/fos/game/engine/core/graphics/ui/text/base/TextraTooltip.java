// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Null;

public class TextraTooltip extends Tooltip<TextraLabel>
{
    public TextraTooltip(@Null final String text, final Skin skin) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TextraTooltip(@Null final String text, final Skin skin, final String styleName) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TextraTooltip(@Null final String text, final TextTooltip.TextTooltipStyle style) {
        this(text, TooltipManager.getInstance(), style);
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final Skin skin) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final String styleName) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final TextTooltip.TextTooltipStyle style) {
        this(text, manager, style, new Font(style.label.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, false));
    }
    
    public TextraTooltip(@Null final String text, final Skin skin, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TextraTooltip(@Null final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TextraTooltip(@Null final String text, final TextTooltip.TextTooltipStyle style, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), style, replacementFont);
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final Font replacementFont) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TextraTooltip(@Null final String text, final TooltipManager manager, final TextTooltip.TextTooltipStyle style, final Font replacementFont) {
        super(null, manager);
        final TextraLabel label = this.newLabel(text, replacementFont, style.label.fontColor);
        label.setAlignment(1);
        label.setWrap(true);
        label.layout.setTargetWidth(style.wrapWidth);
        this.getContainer().setActor(label);
        this.getContainer().width(style.wrapWidth);
        this.setStyle(style, replacementFont);
        label.setText(text);
    }
    
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TextraLabel(text, style);
    }
    
    protected TextraLabel newLabel(final String text, final Font font) {
        return new TextraLabel(text, font);
    }
    
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return (color == null) ? new TextraLabel(text, font) : new TextraLabel(text, font, color);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style) {
        this.setStyle(style, false);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style, final boolean makeGridGlyphs) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        final Container<TextraLabel> container = (Container<TextraLabel>)this.getContainer();
        ((TextraLabel)container.getActor()).setFont(new Font(style.label.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs), false);
        ((TextraLabel)container.getActor()).layout.targetWidth = style.wrapWidth;
        if (style.label.fontColor != null) {
            ((TextraLabel)container.getActor()).setColor(style.label.fontColor);
        }
        ((TextraLabel)container.getActor()).getFont().regenerateLayout(((TextraLabel)container.getActor()).layout);
        ((TextraLabel)container.getActor()).setWidth(((TextraLabel)container.getActor()).layout.getWidth());
        container.setBackground(style.background);
        container.maxWidth(style.wrapWidth);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style, final Font font) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        final Container<TextraLabel> container = (Container<TextraLabel>)this.getContainer();
        ((TextraLabel)container.getActor()).setFont(font, false);
        ((TextraLabel)container.getActor()).layout.targetWidth = style.wrapWidth;
        if (style.label.fontColor != null) {
            ((TextraLabel)container.getActor()).setColor(style.label.fontColor);
        }
        font.regenerateLayout(((TextraLabel)container.getActor()).layout);
        font.calculateSize(((TextraLabel)container.getActor()).layout);
        ((TextraLabel)container.getActor()).setWidth(((TextraLabel)container.getActor()).layout.getWidth());
        container.setBackground(style.background);
        container.maxWidth(style.wrapWidth);
    }
    
    public void skipToTheEnd() {
        ((TextraLabel)this.getContainer().getActor()).skipToTheEnd();
    }
}
