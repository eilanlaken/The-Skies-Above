// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Null;

public class TypingTooltip extends Tooltip<TypingLabel>
{
    public TypingTooltip(@Null final String text, final Skin skin) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TypingTooltip(@Null final String text, final Skin skin, final String styleName) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TypingTooltip(@Null final String text, final TextTooltip.TextTooltipStyle style) {
        this(text, TooltipManager.getInstance(), style);
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final Skin skin) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final String styleName) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class));
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final TextTooltip.TextTooltipStyle style) {
        super(null, manager);
        final TypingLabel label = this.newLabel(text, style.label);
        label.setAlignment(1);
        label.setWrap(true);
        label.layout.setTargetWidth(style.wrapWidth);
        this.getContainer().setActor(label);
        this.getContainer().width(style.wrapWidth);
        this.setStyle(style);
        label.setText(text);
    }
    
    public TypingTooltip(@Null final String text, final Skin skin, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TypingTooltip(@Null final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TypingTooltip(@Null final String text, final TextTooltip.TextTooltipStyle style, final Font replacementFont) {
        this(text, TooltipManager.getInstance(), style, replacementFont);
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final Font replacementFont) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get((Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, manager, (TextTooltip.TextTooltipStyle)skin.get(styleName, (Class)TextTooltip.TextTooltipStyle.class), replacementFont);
    }
    
    public TypingTooltip(@Null final String text, final TooltipManager manager, final TextTooltip.TextTooltipStyle style, final Font replacementFont) {
        super(null, manager);
        final TypingLabel label = this.newLabel(text, replacementFont, style.label.fontColor);
        label.setAlignment(1);
        label.setWrap(true);
        label.layout.setTargetWidth(style.wrapWidth);
        this.getContainer().setActor(label);
        this.getContainer().width(style.wrapWidth);
        this.setStyle(style, replacementFont);
        label.setText(text);
    }
    
    protected TypingLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TypingLabel(text, style);
    }
    
    protected TypingLabel newLabel(final String text, final Font font) {
        return new TypingLabel(text, font);
    }
    
    protected TypingLabel newLabel(final String text, final Font font, final Color color) {
        return new TypingLabel(text, font, color);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style) {
        this.setStyle(style, false);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style, final boolean makeGridGlyphs) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        final Container<TypingLabel> container = (Container<TypingLabel>)this.getContainer();
        ((TypingLabel)container.getActor()).setFont(new Font(style.label.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs), false);
        ((TypingLabel)container.getActor()).layout.targetWidth = style.wrapWidth;
        if (style.label.fontColor != null) {
            ((TypingLabel)container.getActor()).setColor(style.label.fontColor);
        }
        ((TypingLabel)container.getActor()).getFont().regenerateLayout(((TypingLabel)container.getActor()).layout);
        ((TypingLabel)container.getActor()).setWidth(((TypingLabel)container.getActor()).layout.getWidth());
        container.setBackground(style.background);
        container.maxWidth(style.wrapWidth);
    }
    
    public void setStyle(final TextTooltip.TextTooltipStyle style, final Font font) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        }
        final Container<TypingLabel> container = (Container<TypingLabel>)this.getContainer();
        ((TypingLabel)container.getActor()).setFont(font, false);
        ((TypingLabel)container.getActor()).layout.targetWidth = style.wrapWidth;
        if (style.label.fontColor != null) {
            ((TypingLabel)container.getActor()).setColor(style.label.fontColor);
        }
        font.regenerateLayout(((TypingLabel)container.getActor()).layout);
        ((TypingLabel)container.getActor()).setWidth(((TypingLabel)container.getActor()).layout.getWidth());
        container.setBackground(style.background);
        container.maxWidth(style.wrapWidth);
    }
    
    public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        ((TypingLabel)this.getContainer().getActor()).restart();
    }
}
