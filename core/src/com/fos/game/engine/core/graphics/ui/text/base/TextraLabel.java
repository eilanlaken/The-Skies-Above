// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class TextraLabel extends Widget
{
    public Layout layout;
    protected Font font;
    public int align;
    public boolean wrap;
    public String storedText;
    public Label.LabelStyle style;
    
    public TextraLabel() {
        this.align = 8;
        this.wrap = false;
        this.layout = (Layout)Layout.POOL.obtain();
        this.font = new Font(new BitmapFont(), Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, false);
    }
    
    public TextraLabel(final String text, final Skin skin) {
        this(text, (Label.LabelStyle)skin.get((Class)Label.LabelStyle.class));
    }
    
    public TextraLabel(final String text, final Skin skin, final boolean makeGridGlyphs) {
        this(text, (Label.LabelStyle)skin.get((Class)Label.LabelStyle.class), makeGridGlyphs);
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class));
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName, final boolean makeGridGlyphs) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class), makeGridGlyphs);
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName, final Color color) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class));
        if (color != null) {
            this.layout.setBaseColor(color);
        }
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName, final String colorName) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class));
        final Color color = (Color)skin.get(colorName, (Class)Color.class);
        if (color != null) {
            this.layout.setBaseColor(color);
        }
    }
    
    public TextraLabel(final String text, final Label.LabelStyle style) {
        this(text, style, false);
    }
    
    public TextraLabel(final String text, final Label.LabelStyle style, final boolean makeGridGlyphs) {
        this(text, style, new Font(style.font, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs));
    }
    
    public TextraLabel(final String text, final Skin skin, final Font replacementFont) {
        this(text, (Label.LabelStyle)skin.get((Class)Label.LabelStyle.class), replacementFont);
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class), replacementFont);
    }
    
    public TextraLabel(final String text, final Skin skin, final String styleName, final Font replacementFont, final Color color) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class), replacementFont);
        if (color != null) {
            this.layout.setBaseColor(color);
        }
    }
    
    public TextraLabel(final String text, final Label.LabelStyle style, final Font replacementFont) {
        this.align = 8;
        this.wrap = false;
        this.font = replacementFont;
        this.layout = (Layout)Layout.POOL.obtain();
        if (style.fontColor != null) {
            this.layout.setBaseColor(style.fontColor);
        }
        this.style = style;
        this.storedText = text;
        this.font.markup(text, this.layout);
    }
    
    public TextraLabel(final String text, final Font font) {
        this.align = 8;
        this.wrap = false;
        this.font = font;
        this.layout = (Layout)Layout.POOL.obtain();
        font.markup(this.storedText = text, this.layout);
    }
    
    public TextraLabel(final String text, final Font font, final Color color) {
        this.align = 8;
        this.wrap = false;
        this.font = font;
        this.layout = (Layout)Layout.POOL.obtain();
        if (color != null) {
            this.layout.setBaseColor(color);
        }
        font.markup(this.storedText = text, this.layout);
    }
    
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        final Color set = batch.getColor().set(this.getColor());
        set.a *= parentAlpha;
        batch.setColor(batch.getColor());
        float baseX = 0.0f;
        float baseY = 0.0f;
        final float rot = this.getRotation();
        final float sn = MathUtils.sinDeg(rot);
        final float cs = MathUtils.cosDeg(rot);
        float height = this.layout.getHeight();
        if (Align.isBottom(this.align)) {
            baseX -= sn * height;
            baseY += cs * height;
        }
        else if (Align.isCenterVertical(this.align)) {
            baseX -= sn * height * 0.5f;
            baseY += cs * height * 0.5f;
        }
        final float width = this.getWidth();
        height = this.getHeight();
        if (Align.isRight(this.align)) {
            baseX += cs * width;
            baseY += sn * width;
        }
        else if (Align.isCenterHorizontal(this.align)) {
            baseX += cs * width * 0.5f;
            baseY += sn * width * 0.5f;
        }
        if (Align.isTop(this.align)) {
            baseX -= sn * height;
            baseY += cs * height;
        }
        else if (Align.isCenterVertical(this.align)) {
            baseX -= sn * height * 0.5f;
            baseY += cs * height * 0.5f;
        }
        if (this.style != null && this.style.background != null) {
            final Drawable background = this.style.background;
            if (Align.isLeft(this.align)) {
                baseX += cs * background.getLeftWidth();
                baseY += sn * background.getLeftWidth();
            }
            else if (Align.isRight(this.align)) {
                baseX -= cs * background.getRightWidth();
                baseY -= sn * background.getRightWidth();
            }
            else {
                baseX += cs * (background.getLeftWidth() - background.getRightWidth()) * 0.5f;
                baseY += sn * (background.getLeftWidth() - background.getRightWidth()) * 0.5f;
            }
            if (Align.isBottom(this.align)) {
                baseX -= sn * background.getBottomHeight();
                baseY += cs * background.getBottomHeight();
            }
            else if (Align.isTop(this.align)) {
                baseX += sn * background.getTopHeight();
                baseY -= cs * background.getTopHeight();
            }
            else {
                baseX -= sn * (background.getBottomHeight() - background.getTopHeight()) * 0.5f;
                baseY += cs * (background.getBottomHeight() - background.getTopHeight()) * 0.5f;
            }
            ((TransformDrawable)background).draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), 1.0f, 1.0f, rot);
        }
        if (this.layout.lines.isEmpty()) {
            return;
        }
        final boolean resetShader = this.font.distanceField != Font.DistanceFieldType.STANDARD && batch.getShader() != this.font.shader;
        if (resetShader) {
            this.font.enableShader(batch);
        }
        baseX -= 0.5f * this.font.cellWidth;
        baseY -= 0.5f * this.font.cellHeight;
        this.font.drawGlyphs(batch, this.layout, this.getX() + baseX, this.getY() + baseY, this.align, rot, this.getOriginX(), this.getOriginY());
        if (resetShader) {
            batch.setShader((ShaderProgram)null);
        }
    }
    
    public float getPrefWidth() {
        if (this.wrap) {
            return 0.0f;
        }
        float width = this.layout.getWidth();
        if (this.style != null && this.style.background != null) {
            width = Math.max(width + this.style.background.getLeftWidth() + this.style.background.getRightWidth(), this.style.background.getMinWidth());
        }
        return width;
    }
    
    public float getPrefHeight() {
        float height = this.layout.getHeight();
        if (this.style != null && this.style.background != null) {
            height = Math.max(height + this.style.background.getBottomHeight() + this.style.background.getTopHeight(), this.style.background.getMinHeight());
        }
        return height;
    }
    
    public TextraLabel useIntegerPositions(final boolean integer) {
        this.font.integerPosition = integer;
        return this;
    }
    
    public boolean isWrap() {
        return this.wrap;
    }
    
    public TextraLabel setWrap(final boolean wrap) {
        final boolean wrap2 = this.wrap;
        this.wrap = wrap;
        if (wrap2 != wrap) {
            this.invalidateHierarchy();
        }
        return this;
    }
    
    public void setSize(final float width, final float height) {
        super.setSize(width, height);
    }
    
    public void layout() {
        float width = this.getWidth();
        if (this.style != null && this.style.background != null) {
            width -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
        }
        if (this.wrap && this.layout.getTargetWidth() != width) {
            this.layout.setTargetWidth(width);
            this.font.regenerateLayout(this.layout);
            this.invalidateHierarchy();
        }
    }
    
    public int getAlignment() {
        return this.align;
    }
    
    public void setAlignment(final int alignment) {
        this.align = alignment;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public void setFont(final Font font) {
        final Font font2 = this.font;
        this.font = font;
        if (!font2.equals(font)) {
            font.regenerateLayout(this.layout);
        }
    }
    
    public void setFont(final Font font, final boolean regenerate) {
        final Font font2 = this.font;
        this.font = font;
        if (!font2.equals(font) && regenerate) {
            font.regenerateLayout(this.layout);
        }
    }
    
    public void setText(final String markupText) {
        this.storedText = markupText;
        this.layout.setTargetWidth(this.getWidth());
        this.font.markup(markupText, this.layout.clear());
        this.setWidth(this.layout.getWidth() + ((this.style != null && this.style.background != null) ? (this.style.background.getLeftWidth() + this.style.background.getRightWidth()) : 0.0f));
    }
    
    public void skipToTheEnd() {
    }
}
