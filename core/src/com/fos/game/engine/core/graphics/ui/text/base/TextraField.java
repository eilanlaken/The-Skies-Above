// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.LongArray;
import java.io.PrintStream;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.math.MathUtils;
import regexodus.Category;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class TextraField extends Widget implements Disableable
{
    protected static final char BACKSPACE = '\b';
    protected static final char CARRIAGE_RETURN = '\r';
    protected static final char NEWLINE = '\n';
    protected static final char TAB = '\t';
    protected static final char DELETE = '\u007f';
    public static final char BULLET = '\u2022';
    private static final Vector2 tmp1;
    private static final Vector2 tmp2;
    private static final Vector2 tmp3;
    public static float keyRepeatInitialTime;
    public static float keyRepeatTime;
    protected String text;
    protected int cursor;
    protected int selectionStart;
    protected boolean hasSelection;
    protected boolean writeEnters;
    protected final TypingLabel label;
    protected final FloatArray glyphPositions;
    TextField.TextFieldStyle style;
    private String messageText;
    protected String displayText;
    Clipboard clipboard;
    InputListener inputListener;
    @Null
    TextFieldListener listener;
    @Null
    TextFieldFilter filter;
    OnscreenKeyboard keyboard;
    boolean focusTraversal;
    boolean onlyFontChars;
    boolean disabled;
    private int textHAlign;
    private float selectionX;
    private float selectionWidth;
    String undoText;
    long lastChangeTime;
    boolean passwordMode;
    private char passwordCharacter;
    protected float fontOffset;
    protected float textOffset;
    float renderOffset;
    protected int visibleTextStart;
    protected int visibleTextEnd;
    private int maxLength;
    boolean focused;
    boolean cursorOn;
    float blinkTime;
    final Timer.Task blinkTask;
    final KeyRepeatTask keyRepeatTask;
    boolean programmaticChangeEvents;
    
    public TextraField(@Null final String text, final Skin skin) {
        this(text, (TextField.TextFieldStyle)skin.get((Class)TextField.TextFieldStyle.class));
    }
    
    public TextraField(@Null final String text, final Skin skin, final String styleName) {
        this(text, (TextField.TextFieldStyle)skin.get(styleName, (Class)TextField.TextFieldStyle.class));
    }
    
    public TextraField(@Null final String text, final TextField.TextFieldStyle style) {
        this.glyphPositions = new FloatArray();
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.textHAlign = 8;
        this.undoText = "";
        this.passwordCharacter = '\u2022';
        this.blinkTime = 0.32f;
        this.blinkTask = new Timer.Task() {
            public void run() {
                if (TextraField.this.getStage() == null) {
                    this.cancel();
                    return;
                }
                TextraField.this.cursorOn = !TextraField.this.cursorOn;
                Gdx.graphics.requestRendering();
            }
        };
        this.keyRepeatTask = new KeyRepeatTask();
        this.setStyle(style);
        this.label = new TypingLabel("", new Label.LabelStyle(style.font, style.fontColor));
        this.label.layout.setEllipsis("");
        this.label.layout.setMaxLines(1);
        this.label.wrap = false;
        this.label.trackingInput = true;
        this.clipboard = Gdx.app.getClipboard();
        this.initialize();
        this.setText(text);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.label.skipToTheEnd(true, true);
        this.updateDisplayText();
    }
    
    public TextraField(@Null final String text, final TextField.TextFieldStyle style, final Font replacementFont) {
        this.glyphPositions = new FloatArray();
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.textHAlign = 8;
        this.undoText = "";
        this.passwordCharacter = '\u2022';
        this.blinkTime = 0.32f;
        this.blinkTask = new Timer.Task() {
            public void run() {
                if (TextraField.this.getStage() == null) {
                    this.cancel();
                    return;
                }
                TextraField.this.cursorOn = !TextraField.this.cursorOn;
                Gdx.graphics.requestRendering();
            }
        };
        this.keyRepeatTask = new KeyRepeatTask();
        this.setStyle(style);
        this.label = new TypingLabel("", new Label.LabelStyle(style.font, style.fontColor), replacementFont);
        this.label.layout.setEllipsis("");
        this.label.layout.setMaxLines(1);
        this.label.wrap = false;
        this.label.trackingInput = true;
        this.clipboard = Gdx.app.getClipboard();
        this.initialize();
        this.setText(text);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.label.skipToTheEnd(true, true);
        this.updateDisplayText();
    }
    
    protected void initialize() {
        this.addListener((EventListener)(this.inputListener = this.createInputListener()));
    }
    
    protected InputListener createInputListener() {
        return (InputListener)new TextFieldClickListener();
    }
    
    protected boolean isWordCharacter(final char c) {
        return Category.Word.contains(c);
    }
    
    protected boolean isWordCharacter(final long glyph) {
        return Category.Word.contains((char)glyph);
    }
    
    protected long wordUnderCursor() {
        final TypingLabel lb = this.label;
        final int start = Math.max(0, this.label.overIndex);
        int right = lb.length();
        int left = 0;
        int index = start;
        if (start >= lb.length()) {
            left = lb.length();
            right = 0;
        }
        else {
            while (index < right) {
                if (!this.isWordCharacter(lb.getInWorkingLayout(index))) {
                    right = index;
                    break;
                }
                ++index;
            }
            for (index = start - 1; index > -1; --index) {
                if (!this.isWordCharacter(lb.getInWorkingLayout(index))) {
                    left = index + 1;
                    break;
                }
            }
        }
        return (long)left << 32 | ((long)right & 0xFFFFFFFFL);
    }
    
    boolean withinMaxLength(final int size) {
        return this.maxLength <= 0 || size < this.maxLength;
    }
    
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }
    
    public int getMaxLength() {
        return this.maxLength;
    }
    
    public void setOnlyFontChars(final boolean onlyFontChars) {
        this.onlyFontChars = onlyFontChars;
    }
    
    public void setStyle(final TextField.TextFieldStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        if (this.text != null) {
            this.updateDisplayText();
        }
        this.invalidateHierarchy();
    }
    
    public TextField.TextFieldStyle getStyle() {
        return this.style;
    }
    
    protected void calculateOffsets() {
        float visibleWidth = this.getWidth();
        final Drawable background = this.getBackgroundDrawable();
        if (background != null) {
            visibleWidth -= background.getLeftWidth() + background.getRightWidth();
        }
        final int glyphCount = this.glyphPositions.size;
        final float[] glyphPositions = this.glyphPositions.items;
        this.cursor = MathUtils.clamp(this.cursor, 0, glyphCount - 1);
        final float distance = glyphPositions[Math.max(0, this.cursor - 1)] + this.renderOffset;
        if (distance <= 0.0f) {
            this.renderOffset -= distance;
        }
        else {
            final int index = Math.min(glyphCount - 1, this.cursor + 1);
            final float minX = glyphPositions[index] - visibleWidth;
            if (-this.renderOffset < minX) {
                this.renderOffset = -minX;
            }
        }
        float maxOffset = 0.0f;
        final float width = glyphPositions[glyphCount - 1];
        for (int i = glyphCount - 2; i >= 0; --i) {
            final float x = glyphPositions[i];
            if (width - x > visibleWidth) {
                break;
            }
            maxOffset = x;
        }
        if (-this.renderOffset > maxOffset) {
            this.renderOffset = -maxOffset;
        }
        this.visibleTextStart = 0;
        float startX = 0.0f;
        for (int j = 0; j < glyphCount; ++j) {
            if (glyphPositions[j] >= -this.renderOffset) {
                this.visibleTextStart = j;
                startX = glyphPositions[j];
                break;
            }
        }
        int end = this.visibleTextStart + 1;
        final float endX = visibleWidth - this.renderOffset;
        for (int n = Math.min(this.label.length(), glyphCount); end <= n && glyphPositions[end] <= endX; ++end) {}
        this.visibleTextEnd = Math.max(0, end - 1);
        if ((this.textHAlign & 0x8) == 0x0) {
            this.textOffset = visibleWidth - glyphPositions[this.visibleTextEnd] - this.fontOffset + startX;
            if ((this.textHAlign & 0x1) != 0x0) {
                this.textOffset = (float)Math.round(this.textOffset * 0.5f);
            }
        }
        else {
            this.textOffset = startX + this.renderOffset;
        }
        if (this.hasSelection) {
            final int minIndex = Math.min(this.cursor, this.selectionStart);
            final int maxIndex = Math.max(this.cursor, this.selectionStart);
            final float minX2 = Math.max(glyphPositions[minIndex] - glyphPositions[this.visibleTextStart], -this.textOffset);
            final float maxX = Math.min(glyphPositions[maxIndex] - glyphPositions[this.visibleTextStart], visibleWidth - this.textOffset);
            this.selectionX = minX2;
            this.selectionWidth = maxX - minX2;
        }
    }
    
    @Null
    protected Drawable getBackgroundDrawable() {
        if (this.disabled && this.style.disabledBackground != null) {
            return this.style.disabledBackground;
        }
        if (this.style.focusedBackground != null && this.hasKeyboardFocus()) {
            return this.style.focusedBackground;
        }
        return this.style.background;
    }
    
    public void draw(final Batch batch, final float parentAlpha) {
        final boolean focused = this.hasKeyboardFocus();
        if (focused != this.focused || (focused && !this.blinkTask.isScheduled())) {
            this.focused = focused;
            this.blinkTask.cancel();
            this.cursorOn = focused;
            if (focused) {
                Timer.schedule(this.blinkTask, this.blinkTime, this.blinkTime);
            }
            else {
                this.keyRepeatTask.cancel();
            }
        }
        else if (!focused) {
            this.cursorOn = false;
        }
        final Font font = this.label.font;
        final Color fontColor = (this.disabled && this.style.disabledFontColor != null) ? this.style.disabledFontColor : ((focused && this.style.focusedFontColor != null) ? this.style.focusedFontColor : this.style.fontColor);
        final Drawable selection = this.style.selection;
        final Drawable cursorPatch = this.style.cursor;
        final Drawable background = this.getBackgroundDrawable();
        final Color color = this.getColor();
        final float x = this.getX();
        final float y = this.getY();
        final float width = this.getWidth();
        final float height = this.getHeight();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        float bgLeftWidth = 0.0f;
        float bgRightWidth = 0.0f;
        if (background != null) {
            background.draw(batch, x, y, width, height);
            bgLeftWidth = background.getLeftWidth();
            bgRightWidth = background.getRightWidth();
        }
        final float textY = this.getTextY(font, background);
        this.calculateOffsets();
        if (focused && this.hasSelection && selection != null) {
            this.drawSelection(selection, batch, font, x + bgLeftWidth, y + textY);
        }
        final float yOffset = 0.0f;
        if (this.label.length() == 0) {
            if ((!focused || this.disabled) && this.messageText != null) {
                if (this.style.messageFontColor != null) {
                    this.label.setColor(this.style.messageFontColor.r, this.style.messageFontColor.g, this.style.messageFontColor.b, this.style.messageFontColor.a * color.a);
                }
                else {
                    this.label.setColor(0.7f, 0.7f, 0.7f, color.a);
                }
                this.label.setText(this.messageText, false, false);
                this.label.setBounds(x + bgLeftWidth, y + textY + yOffset, width - bgLeftWidth - bgRightWidth, font.cellHeight);
                this.label.draw(batch, parentAlpha);
            }
        }
        else {
            if (fontColor != null) {
                this.label.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * color.a);
            }
            this.label.setPosition(x + bgLeftWidth, y + textY + yOffset);
            this.label.draw(batch, parentAlpha);
        }
        if (!this.disabled && this.cursorOn && cursorPatch != null) {
            this.drawCursor(cursorPatch, batch, font, x + bgLeftWidth, y + textY);
        }
    }
    
    protected float getTextY(final Font font, @Null final Drawable background) {
        final float height = this.getHeight();
        float textY = 0.0f;
        if (background != null) {
            final float bottom = background.getBottomHeight();
            textY = textY + (height - background.getTopHeight() - bottom) * 0.5f + bottom;
        }
        else {
            textY += height * 0.5f;
        }
        if (font.integerPosition) {
            textY = (float)(int)textY;
        }
        return textY;
    }
    
    protected void drawSelection(final Drawable selection, final Batch batch, final Font font, final float x, final float y) {
        selection.draw(batch, x + this.textOffset + this.selectionX + this.fontOffset, y - font.cellHeight * 0.5f, this.selectionWidth, font.cellHeight);
    }
    
    protected void drawCursor(final Drawable cursorPatch, final Batch batch, final Font font, final float x, final float y) {
        cursorPatch.draw(batch, x + this.textOffset + this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.visibleTextStart) + this.fontOffset, y - font.cellHeight * 0.5f, cursorPatch.getMinWidth(), font.cellHeight);
    }
    
    void updateDisplayText() {
        final Font font = this.label.font;
        this.label.font.defaultValue = (Font.GlyphRegion)this.label.font.mapping.get(32);
        this.label.restart(this.text.replace('\r', ' ').replace('\n', ' '));
        if (this.passwordMode && font.mapping.containsKey((int)this.passwordCharacter)) {
            for (int ln = 0; ln < this.label.workingLayout.lines(); ++ln) {
                final Line line = this.label.workingLayout.getLine(ln);
                for (int g = 0; g < line.glyphs.size; ++g) {
                    line.glyphs.set(g, 0xFFFFFFFE00000000L | (long)this.passwordCharacter);
                }
            }
        }
        this.displayText = this.label.toString();
        this.label.skipToTheEnd(true, true);
        float end = 0.0f;
        if (this.label.workingLayout.lines.notEmpty()) {
            end = font.calculateXAdvances((Line)this.label.workingLayout.lines.first(), this.glyphPositions);
        }
        else {
            this.fontOffset = 0.0f;
        }
        this.glyphPositions.add(end);
        this.visibleTextStart = Math.min(this.visibleTextStart, this.glyphPositions.size - 1);
        this.visibleTextEnd = MathUtils.clamp(this.visibleTextEnd, this.visibleTextStart, this.glyphPositions.size - 1);
        this.selectionStart = Math.min(this.selectionStart, this.label.length());
    }
    
    public void copy() {
        if (this.hasSelection && !this.passwordMode) {
            final String toCopy = this.label.substring(Math.min(this.cursor, this.selectionStart), Math.max(this.cursor, this.selectionStart));
            System.out.println("Copying: " + toCopy);
            this.clipboard.setContents(toCopy);
        }
    }
    
    public void cut() {
        this.cut(this.programmaticChangeEvents);
    }
    
    void cut(final boolean fireChangeEvent) {
        if (this.hasSelection && !this.passwordMode) {
            this.copy();
            this.cursor = this.delete(fireChangeEvent);
            this.updateDisplayText();
        }
    }
    
    void paste(@Null final String content, final boolean fireChangeEvent) {
        if (content == null) {
            return;
        }
        final StringBuilder buffer = new StringBuilder();
        int textLength = this.label.length();
        if (this.hasSelection) {
            textLength -= Math.abs(this.cursor - this.selectionStart);
        }
        final IntMap<Font.GlyphRegion> mapping = this.label.font.mapping;
        for (int i = 0, n = content.length(); i < n && this.withinMaxLength(textLength + buffer.length()); ++i) {
            final char c = content.charAt(i);
            if (!this.writeEnters || (c != '\n' && c != '\r')) {
                if (c == '\r') {
                    continue;
                }
                if (c == '\n') {
                    continue;
                }
                if (this.onlyFontChars && !mapping.containsKey((int)c)) {
                    continue;
                }
                if (this.filter != null && !this.filter.acceptChar(this, c)) {
                    continue;
                }
            }
            buffer.append(c);
        }
        if (this.hasSelection) {
            System.out.println("cursor before: " + this.cursor);
            final PrintStream out = System.out;
            final StringBuilder append = new StringBuilder().append("cursor after: ");
            final int delete = this.delete(fireChangeEvent);
            this.cursor = delete;
            out.println(append.append(delete).toString());
        }
        if (fireChangeEvent) {
            this.changeText(this.cursor, buffer);
        }
        else {
            this.insert(this.cursor, buffer);
        }
        this.text = this.label.layout.toString();
        this.updateDisplayText();
        this.cursor += buffer.length();
        System.out.println("End of paste(): " + this.label.layout + "\n com.fos.game.engine.core.graphics.ui.text: " + this.text);
    }
    
    boolean insert(final int position, final CharSequence inserting) {
        if (inserting.length() == 0) {
            return false;
        }
        this.label.insertInLayout(this.label.layout, position, inserting);
        return true;
    }
    
    String insert(final int position, final CharSequence text, final String to) {
        if (to.length() == 0) {
            return text.toString();
        }
        return to.substring(0, position) + (Object)text + to.substring(position);
    }
    
    int delete(final boolean fireChangeEvent) {
        final int from = this.selectionStart;
        final int to = this.cursor;
        final int minIndex = Math.min(from, to);
        final int maxIndex = Math.max(from, to) - 1;
        final LongArray glyphs = this.label.layout.getLine(0).glyphs;
        if (glyphs.size > 0 && minIndex <= maxIndex) {
            glyphs.removeRange(minIndex, Math.max(Math.min(glyphs.size - 1, maxIndex), 0));
        }
        if (fireChangeEvent) {
            this.changeText(this.text, this.label.layout.toString());
        }
        else {
            this.text = this.label.layout.toString();
        }
        this.clearSelection();
        return minIndex;
    }
    
    public void next(final boolean up) {
        final Stage stage = this.getStage();
        if (stage == null) {
            return;
        }
        TextraField current = this;
        final Vector2 currentCoords = current.getParent().localToStageCoordinates(TextraField.tmp2.set(current.getX(), current.getY()));
        final Vector2 bestCoords = TextraField.tmp1;
        while (true) {
            TextraField textField = current.findNextTextField((Array<Actor>)stage.getActors(), null, bestCoords, currentCoords, up);
            if (textField == null) {
                if (up) {
                    currentCoords.set(-3.4028235E38f, -3.4028235E38f);
                }
                else {
                    currentCoords.set(Float.MAX_VALUE, Float.MAX_VALUE);
                }
                textField = current.findNextTextField((Array<Actor>)stage.getActors(), null, bestCoords, currentCoords, up);
            }
            if (textField == null) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                break;
            }
            if (stage.setKeyboardFocus((Actor)textField)) {
                textField.selectAll();
                break;
            }
            current = textField;
            currentCoords.set(bestCoords);
        }
    }
    
    @Null
    private TextraField findNextTextField(final Array<Actor> actors, @Null TextraField best, final Vector2 bestCoords, final Vector2 currentCoords, final boolean up) {
        for (int i = 0, n = actors.size; i < n; ++i) {
            final Actor actor = (Actor)actors.get(i);
            if (actor instanceof TextraField) {
                if (actor != this) {
                    final TextraField textField = (TextraField)actor;
                    if (!textField.isDisabled() && textField.focusTraversal) {
                        if (textField.ascendantsVisible()) {
                            final Vector2 actorCoords = actor.getParent().localToStageCoordinates(TextraField.tmp3.set(actor.getX(), actor.getY()));
                            final boolean below = actorCoords.y != currentCoords.y && (actorCoords.y < currentCoords.y ^ up);
                            final boolean right = actorCoords.y == currentCoords.y && (actorCoords.x > currentCoords.x ^ up);
                            if (below || right) {
                                boolean better = best == null || (actorCoords.y != bestCoords.y && (actorCoords.y > bestCoords.y ^ up));
                                if (!better) {
                                    better = (actorCoords.y == bestCoords.y && (actorCoords.x < bestCoords.x ^ up));
                                }
                                if (better) {
                                    best = (TextraField)actor;
                                    bestCoords.set(actorCoords);
                                }
                            }
                        }
                    }
                }
            }
            else if (actor instanceof Group) {
                best = this.findNextTextField((Array<Actor>)((Group)actor).getChildren(), best, bestCoords, currentCoords, up);
            }
        }
        return best;
    }
    
    public InputListener getDefaultInputListener() {
        return this.inputListener;
    }
    
    public void setTextFieldListener(@Null final TextFieldListener listener) {
        this.listener = listener;
    }
    
    public void setTextFieldFilter(@Null final TextFieldFilter filter) {
        this.filter = filter;
    }
    
    @Null
    public TextFieldFilter getTextFieldFilter() {
        return this.filter;
    }
    
    public void setFocusTraversal(final boolean focusTraversal) {
        this.focusTraversal = focusTraversal;
    }
    
    @Null
    public String getMessageText() {
        return this.messageText;
    }
    
    public void setMessageText(@Null final String messageText) {
        this.messageText = messageText;
    }
    
    public void appendText(@Null String str) {
        if (str == null) {
            str = "";
        }
        this.clearSelection();
        this.cursor = this.text.length();
        this.paste(str, this.programmaticChangeEvents);
    }
    
    public void setText(@Null String str) {
        if (str == null) {
            str = "";
        }
        if (str.equals(this.text)) {
            return;
        }
        this.clearSelection();
        final String oldText = this.text;
        this.text = "";
        this.label.layout.getLine(0).glyphs.clear();
        this.cursor = 0;
        this.paste(str, this.hasSelection = false);
        if (this.programmaticChangeEvents) {
            this.changeText(oldText, this.text);
        }
        this.cursor = 0;
    }
    
    public String getText() {
        return this.text;
    }
    
    boolean changeText(final String oldText, final String newText) {
        if (newText.equals(oldText)) {
            return false;
        }
        this.text = newText;
        final ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)Pools.obtain((Class)ChangeListener.ChangeEvent.class);
        final boolean cancelled = this.fire((Event)changeEvent);
        if (cancelled) {
            this.text = oldText;
        }
        Pools.free((Object)changeEvent);
        return !cancelled;
    }
    
    boolean changeText(final int position, final CharSequence inserting) {
        final Layout oldText = new Layout(this.label.layout);
        if (this.insert(position, inserting)) {
            return false;
        }
        final ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)Pools.obtain((Class)ChangeListener.ChangeEvent.class);
        final boolean cancelled = this.fire((Event)changeEvent);
        if (cancelled) {
            this.label.layout = oldText;
        }
        Pools.free((Object)changeEvent);
        return !cancelled;
    }
    
    public void setProgrammaticChangeEvents(final boolean programmaticChangeEvents) {
        this.programmaticChangeEvents = programmaticChangeEvents;
    }
    
    public boolean getProgrammaticChangeEvents() {
        return this.programmaticChangeEvents;
    }
    
    public int getSelectionStart() {
        return this.selectionStart;
    }
    
    public String getSelection() {
        return this.hasSelection ? this.label.substring(Math.min(this.selectionStart, this.cursor), Math.max(this.selectionStart, this.cursor)) : "";
    }
    
    public void setSelection(int selectionStart, int selectionEnd) {
        if (selectionStart < 0) {
            throw new IllegalArgumentException("selectionStart must be >= 0");
        }
        if (selectionEnd < 0) {
            throw new IllegalArgumentException("selectionEnd must be >= 0");
        }
        selectionStart = Math.min(this.text.length(), selectionStart);
        selectionEnd = Math.min(this.text.length(), selectionEnd);
        if (selectionEnd == selectionStart) {
            this.clearSelection();
            return;
        }
        if (selectionEnd < selectionStart) {
            final int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }
        this.hasSelection = true;
        this.selectionStart = selectionStart;
        this.cursor = selectionEnd;
    }
    
    public void selectAll() {
        this.setSelection(0, this.text.length());
    }
    
    public void clearSelection() {
        this.hasSelection = false;
    }
    
    public void setCursorPosition(final int cursorPosition) {
        if (cursorPosition < 0) {
            throw new IllegalArgumentException("cursorPosition must be >= 0");
        }
        this.clearSelection();
        this.cursor = Math.min(cursorPosition, this.text.length());
    }
    
    public int getCursorPosition() {
        return this.cursor;
    }
    
    public OnscreenKeyboard getOnscreenKeyboard() {
        return this.keyboard;
    }
    
    public void setOnscreenKeyboard(final OnscreenKeyboard keyboard) {
        this.keyboard = keyboard;
    }
    
    public void setClipboard(final Clipboard clipboard) {
        this.clipboard = clipboard;
    }
    
    public float getPrefWidth() {
        return 150.0f;
    }
    
    public float getPrefHeight() {
        float topAndBottom = 0.0f;
        float minHeight = 0.0f;
        if (this.style.background != null) {
            topAndBottom = Math.max(topAndBottom, this.style.background.getBottomHeight() + this.style.background.getTopHeight());
            minHeight = Math.max(minHeight, this.style.background.getMinHeight());
        }
        if (this.style.focusedBackground != null) {
            topAndBottom = Math.max(topAndBottom, this.style.focusedBackground.getBottomHeight() + this.style.focusedBackground.getTopHeight());
            minHeight = Math.max(minHeight, this.style.focusedBackground.getMinHeight());
        }
        if (this.style.disabledBackground != null) {
            topAndBottom = Math.max(topAndBottom, this.style.disabledBackground.getBottomHeight() + this.style.disabledBackground.getTopHeight());
            minHeight = Math.max(minHeight, this.style.disabledBackground.getMinHeight());
        }
        return Math.max(topAndBottom + this.label.font.cellHeight, minHeight);
    }
    
    public void setAlignment(final int alignment) {
        this.textHAlign = alignment;
    }
    
    public int getAlignment() {
        return this.textHAlign;
    }
    
    protected void positionChanged() {
        super.positionChanged();
        this.label.setPosition(this.getX(), this.getY());
    }
    
    protected void sizeChanged() {
        super.sizeChanged();
        this.label.layout.setTargetWidth(this.getWidth());
    }
    
    public void act(final float delta) {
        super.act(delta);
        this.label.act(delta);
    }
    
    public void setPasswordMode(final boolean passwordMode) {
        final boolean passwordMode2 = this.passwordMode;
        this.passwordMode = passwordMode;
        if (passwordMode2 != passwordMode) {
            this.updateDisplayText();
        }
    }
    
    public boolean isPasswordMode() {
        return this.passwordMode;
    }
    
    public void setPasswordCharacter(final char passwordCharacter) {
        if (this.label.font.mapping.containsKey((int)passwordCharacter) && this.passwordCharacter != (this.passwordCharacter = passwordCharacter) && this.passwordMode) {
            this.updateDisplayText();
        }
    }
    
    public void setBlinkTime(final float blinkTime) {
        this.blinkTime = blinkTime;
    }
    
    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }
    
    public boolean isDisabled() {
        return this.disabled;
    }
    
    protected void moveCursor(final boolean forward, final boolean jump) {
        final int limit = forward ? this.text.length() : 0;
        final int charOffset = forward ? 0 : -1;
        do {
            if (forward) {
                if (++this.cursor < limit) {
                    continue;
                }
                break;
            }
            else {
                if (--this.cursor > limit) {
                    continue;
                }
                break;
            }
        } while (jump && this.continueCursor(this.cursor, charOffset));
    }
    
    protected boolean continueCursor(final int index, final int offset) {
        final char c = this.text.charAt(index + offset);
        return this.isWordCharacter(c);
    }
    
    static {
        tmp1 = new Vector2();
        tmp2 = new Vector2();
        tmp3 = new Vector2();
        TextraField.keyRepeatInitialTime = 0.4f;
        TextraField.keyRepeatTime = 0.1f;
    }
    
    class KeyRepeatTask extends Timer.Task
    {
        int keycode;
        
        public void run() {
            if (TextraField.this.getStage() == null) {
                this.cancel();
                return;
            }
            TextraField.this.inputListener.keyDown((InputEvent)null, this.keycode);
        }
    }
    
    public static class DefaultOnscreenKeyboard implements OnscreenKeyboard
    {
        @Override
        public void show(final boolean visible) {
            Gdx.input.setOnscreenKeyboardVisible(visible);
        }
    }
    
    public class TextFieldClickListener extends ClickListener
    {
        public void clicked(final InputEvent event, final float x, final float y) {
            final int count = this.getTapCount() % 4;
            if (count == 0) {
                TextraField.this.clearSelection();
            }
            if (count == 2) {
                final long pair = TextraField.this.wordUnderCursor();
                TextraField.this.setSelection((int)(pair >> 32), (int)pair);
            }
            if (count == 3) {
                TextraField.this.selectAll();
            }
        }
        
        public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
            if (!super.touchDown(event, x, y, pointer, button)) {
                return false;
            }
            if (pointer == 0 && button != 0) {
                return false;
            }
            if (TextraField.this.disabled) {
                return true;
            }
            this.setCursorPosition(x, y);
            TextraField.this.selectionStart = TextraField.this.cursor;
            final Stage stage = TextraField.this.getStage();
            if (stage != null) {
                stage.setKeyboardFocus((Actor)TextraField.this);
            }
            TextraField.this.keyboard.show(true);
            return TextraField.this.hasSelection = true;
        }
        
        public void touchDragged(final InputEvent event, final float x, final float y, final int pointer) {
            super.touchDragged(event, x, y, pointer);
            this.setCursorPosition(x, y);
        }
        
        public void touchUp(final InputEvent event, final float x, final float y, final int pointer, final int button) {
            if (TextraField.this.selectionStart == TextraField.this.cursor) {
                TextraField.this.hasSelection = false;
            }
            super.touchUp(event, x, y, pointer, button);
        }
        
        protected void setCursorPosition(final float x, final float y) {
            TextraField.this.cursor = Math.max(TextraField.this.label.overIndex, 0);
            TextraField.this.cursorOn = TextraField.this.focused;
            TextraField.this.blinkTask.cancel();
            if (TextraField.this.focused) {
                Timer.schedule(TextraField.this.blinkTask, TextraField.this.blinkTime, TextraField.this.blinkTime);
            }
        }
        
        protected void goHome(final boolean jump) {
            TextraField.this.cursor = 0;
        }
        
        protected void goEnd(final boolean jump) {
            TextraField.this.cursor = TextraField.this.text.length();
        }
        
        public boolean keyDown(final InputEvent event, final int keycode) {
            if (TextraField.this.disabled) {
                return false;
            }
            TextraField.this.cursorOn = TextraField.this.focused;
            TextraField.this.blinkTask.cancel();
            if (TextraField.this.focused) {
                Timer.schedule(TextraField.this.blinkTask, TextraField.this.blinkTime, TextraField.this.blinkTime);
            }
            if (!TextraField.this.hasKeyboardFocus()) {
                return false;
            }
            boolean repeat = false;
            final boolean ctrl = UIUtils.ctrl();
            final boolean jump = ctrl && !TextraField.this.passwordMode;
            boolean handled = true;
            if (ctrl) {
                switch (keycode) {
                    case 50: {
                        TextraField.this.paste(TextraField.this.clipboard.getContents(), true);
                        repeat = true;
                        break;
                    }
                    case 31:
                    case 124: {
                        TextraField.this.copy();
                        return true;
                    }
                    case 52: {
                        TextraField.this.cut(true);
                        return true;
                    }
                    case 29: {
                        TextraField.this.selectAll();
                        return true;
                    }
                    case 54: {
                        final String oldText = TextraField.this.text;
                        TextraField.this.setText(TextraField.this.undoText);
                        TextraField.this.undoText = oldText;
                        TextraField.this.updateDisplayText();
                        return true;
                    }
                    default: {
                        handled = false;
                        break;
                    }
                }
            }
            Label_0617: {
                if (UIUtils.shift()) {
                    switch (keycode) {
                        case 124: {
                            TextraField.this.paste(TextraField.this.clipboard.getContents(), true);
                            break;
                        }
                        case 112: {
                            TextraField.this.cut(true);
                            break;
                        }
                    }
                    final int temp = TextraField.this.cursor;
                    switch (keycode) {
                        case 21: {
                            TextraField.this.moveCursor(false, jump);
                            repeat = true;
                            handled = true;
                            break;
                        }
                        case 22: {
                            TextraField.this.moveCursor(true, jump);
                            repeat = true;
                            handled = true;
                            break;
                        }
                        case 3: {
                            this.goHome(jump);
                            handled = true;
                            break;
                        }
                        case 123: {
                            this.goEnd(jump);
                            handled = true;
                            break;
                        }
                        default: {
                            break Label_0617;
                        }
                    }
                    if (!TextraField.this.hasSelection) {
                        TextraField.this.selectionStart = temp;
                        TextraField.this.hasSelection = true;
                    }
                }
                else {
                    switch (keycode) {
                        case 21: {
                            TextraField.this.moveCursor(false, jump);
                            TextraField.this.clearSelection();
                            repeat = true;
                            handled = true;
                            break;
                        }
                        case 22: {
                            TextraField.this.moveCursor(true, jump);
                            TextraField.this.clearSelection();
                            repeat = true;
                            handled = true;
                            break;
                        }
                        case 3: {
                            this.goHome(jump);
                            TextraField.this.clearSelection();
                            handled = true;
                            break;
                        }
                        case 123: {
                            this.goEnd(jump);
                            TextraField.this.clearSelection();
                            handled = true;
                            break;
                        }
                    }
                }
            }
            TextraField.this.cursor = MathUtils.clamp(TextraField.this.cursor, 0, TextraField.this.text.length());
            if (repeat) {
                this.scheduleKeyRepeatTask(keycode);
            }
            return handled;
        }
        
        protected void scheduleKeyRepeatTask(final int keycode) {
            if (!TextraField.this.keyRepeatTask.isScheduled() || TextraField.this.keyRepeatTask.keycode != keycode) {
                TextraField.this.keyRepeatTask.keycode = keycode;
                TextraField.this.keyRepeatTask.cancel();
                Timer.schedule((Timer.Task)TextraField.this.keyRepeatTask, TextraField.keyRepeatInitialTime, TextraField.keyRepeatTime);
            }
        }
        
        public boolean keyUp(final InputEvent event, final int keycode) {
            if (TextraField.this.disabled) {
                return false;
            }
            TextraField.this.keyRepeatTask.cancel();
            return true;
        }
        
        protected boolean checkFocusTraversal(final char character) {
            return TextraField.this.focusTraversal && (character == '\t' || ((character == '\r' || character == '\n') && (UIUtils.isAndroid || UIUtils.isIos)));
        }
        
        public boolean keyTyped(final InputEvent event, char character) {
            if (TextraField.this.disabled) {
                return false;
            }
            switch (character) {
                case '\b':
                case '\t':
                case '\n':
                case '\r': {
                    break;
                }
                default: {
                    if (character < ' ') {
                        return false;
                    }
                    break;
                }
            }
            if (!TextraField.this.hasKeyboardFocus()) {
                return false;
            }
            if (UIUtils.isMac && Gdx.input.isKeyPressed(63)) {
                return true;
            }
            if (this.checkFocusTraversal(character)) {
                TextraField.this.next(UIUtils.shift());
            }
            else {
                final boolean enter = character == '\r' || character == '\n';
                final boolean delete = character == '\u007f';
                final boolean backspace = character == '\b';
                if (character == '[') {
                    character = (UIUtils.shift() ? '{' : '\u0002');
                }
                final boolean add = enter ? TextraField.this.writeEnters : (!TextraField.this.onlyFontChars || TextraField.this.label.font.mapping.containsKey((int)character));
                final boolean remove = backspace || delete;
                if (add || remove) {
                    final String oldText = TextraField.this.text;
                    final int oldCursor = TextraField.this.cursor;
                    if (remove) {
                        if (TextraField.this.hasSelection) {
                            TextraField.this.cursor = TextraField.this.delete(false);
                        }
                        else {
                            if (backspace && TextraField.this.cursor > 0) {
                                TextraField.this.text = TextraField.this.text.substring(0, TextraField.this.cursor - 1) + TextraField.this.text.substring(TextraField.this.cursor--);
                                TextraField.this.renderOffset = 0.0f;
                            }
                            if (delete && TextraField.this.cursor < TextraField.this.text.length()) {
                                TextraField.this.text = TextraField.this.text.substring(0, TextraField.this.cursor) + TextraField.this.text.substring(TextraField.this.cursor + 1);
                            }
                        }
                    }
                    if (add && !remove) {
                        if (!enter && TextraField.this.filter != null && !TextraField.this.filter.acceptChar(TextraField.this, character)) {
                            return true;
                        }
                        if (!TextraField.this.withinMaxLength(TextraField.this.text.length() - (TextraField.this.hasSelection ? Math.abs(TextraField.this.cursor - TextraField.this.selectionStart) : 0))) {
                            return true;
                        }
                        if (TextraField.this.hasSelection) {
                            TextraField.this.cursor = TextraField.this.delete(false);
                        }
                        final String insertion = enter ? "\n" : String.valueOf(character);
                        TextraField.this.insert(TextraField.this.cursor++, insertion);
                        TextraField.this.text = TextraField.this.label.layout.toString();
                    }
                    if (TextraField.this.changeText(oldText, TextraField.this.text)) {
                        final long time = System.currentTimeMillis();
                        if (time - 750L > TextraField.this.lastChangeTime) {
                            TextraField.this.undoText = oldText;
                        }
                        TextraField.this.lastChangeTime = time;
                        TextraField.this.updateDisplayText();
                    }
                    else if (!TextraField.this.text.equals(oldText)) {
                        TextraField.this.cursor = oldCursor;
                    }
                }
            }
            if (TextraField.this.listener != null) {
                TextraField.this.listener.keyTyped(TextraField.this, character);
            }
            return true;
        }
    }
    
    public interface OnscreenKeyboard
    {
        void show(final boolean p0);
    }
    
    public interface TextFieldFilter
    {
        boolean acceptChar(final TextraField p0, final char p1);
        
        public static class DigitsOnlyFilter implements TextFieldFilter
        {
            @Override
            public boolean acceptChar(final TextraField textField, final char c) {
                return Character.isDigit(c);
            }
        }
    }
    
    public interface TextFieldListener
    {
        void keyTyped(final TextraField p0, final char p1);
    }
}
