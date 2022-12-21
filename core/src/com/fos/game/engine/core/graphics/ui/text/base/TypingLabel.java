// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.fos.game.engine.core.graphics.ui.text.utils.ColorUtils;

import java.util.Arrays;
import java.util.Map;
import java.lang.StringBuilder;

public class TypingLabel extends TextraLabel
{
    private final ObjectMap<String, String> variables;
    protected final Array<TokenEntry> tokenEntries;
    private final Color clearColor;
    private TypingListener listener;
    private final StringBuilder originalText;
    private final StringBuilder intermediateText;
    protected final Layout workingLayout;
    public final FloatArray offsets;
    public final FloatArray sizing;
    public final FloatArray rotations;
    public boolean trackingInput;
    public boolean selectable;
    public int lastTouchedIndex;
    public int overIndex;
    public int selectionStart;
    public int selectionEnd;
    protected boolean dragging;
    protected final Array<Effect> activeEffects;
    private float textSpeed;
    private float charCooldown;
    private int rawCharIndex;
    private int glyphCharIndex;
    private int glyphCharCompensation;
    private boolean parsed;
    private boolean paused;
    private boolean ended;
    private boolean skipping;
    private boolean ignoringEvents;
    private boolean ignoringEffects;
    private String defaultToken;
    
    public TypingLabel() {
        this.variables = (ObjectMap<String, String>)new ObjectMap();
        this.tokenEntries = (Array<TokenEntry>)new Array();
        this.clearColor = new Color(TypingConfig.DEFAULT_CLEAR_COLOR);
        this.listener = null;
        this.originalText = new StringBuilder();
        this.intermediateText = new StringBuilder();
        this.workingLayout = (Layout)Layout.POOL.obtain();
        this.offsets = new FloatArray();
        this.sizing = new FloatArray();
        this.rotations = new FloatArray();
        this.trackingInput = false;
        this.selectable = false;
        this.lastTouchedIndex = -1;
        this.overIndex = -1;
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.dragging = false;
        this.activeEffects = (Array<Effect>)new Array((Class)Effect.class);
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.defaultToken = "";
        this.workingLayout.font(super.font);
        this.saveOriginalText("");
    }
    
    public TypingLabel(final String text, final Skin skin) {
        this(text, (Label.LabelStyle)skin.get((Class)Label.LabelStyle.class));
    }
    
    public TypingLabel(final String text, final Skin skin, final Font replacementFont) {
        this(text, (Label.LabelStyle)skin.get((Class)Label.LabelStyle.class), replacementFont);
    }
    
    public TypingLabel(final String text, final Skin skin, final String styleName) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class));
    }
    
    public TypingLabel(final String text, final Skin skin, final String styleName, final Font replacementFont) {
        this(text, (Label.LabelStyle)skin.get(styleName, (Class)Label.LabelStyle.class), replacementFont);
    }
    
    public TypingLabel(String text, final Label.LabelStyle style) {
        super(text = Parser.preprocess(text), style);
        this.variables = (ObjectMap<String, String>)new ObjectMap();
        this.tokenEntries = (Array<TokenEntry>)new Array();
        this.clearColor = new Color(TypingConfig.DEFAULT_CLEAR_COLOR);
        this.listener = null;
        this.originalText = new StringBuilder();
        this.intermediateText = new StringBuilder();
        this.workingLayout = (Layout)Layout.POOL.obtain();
        this.offsets = new FloatArray();
        this.sizing = new FloatArray();
        this.rotations = new FloatArray();
        this.trackingInput = false;
        this.selectable = false;
        this.lastTouchedIndex = -1;
        this.overIndex = -1;
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.dragging = false;
        this.activeEffects = (Array<Effect>)new Array((Class)Effect.class);
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.defaultToken = "";
        this.workingLayout.font(super.font);
        this.workingLayout.setBaseColor(this.layout.baseColor);
        this.saveOriginalText(text);
    }
    
    public TypingLabel(String text, final Label.LabelStyle style, final Font replacementFont) {
        super(text = Parser.preprocess(text), style, replacementFont);
        this.variables = (ObjectMap<String, String>)new ObjectMap();
        this.tokenEntries = (Array<TokenEntry>)new Array();
        this.clearColor = new Color(TypingConfig.DEFAULT_CLEAR_COLOR);
        this.listener = null;
        this.originalText = new StringBuilder();
        this.intermediateText = new StringBuilder();
        this.workingLayout = (Layout)Layout.POOL.obtain();
        this.offsets = new FloatArray();
        this.sizing = new FloatArray();
        this.rotations = new FloatArray();
        this.trackingInput = false;
        this.selectable = false;
        this.lastTouchedIndex = -1;
        this.overIndex = -1;
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.dragging = false;
        this.activeEffects = (Array<Effect>)new Array((Class)Effect.class);
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.defaultToken = "";
        this.workingLayout.font(super.font);
        this.workingLayout.setBaseColor(this.layout.baseColor);
        this.saveOriginalText(text);
    }
    
    public TypingLabel(String text, final Font font) {
        super(text = Parser.preprocess(text), font);
        this.variables = (ObjectMap<String, String>)new ObjectMap();
        this.tokenEntries = (Array<TokenEntry>)new Array();
        this.clearColor = new Color(TypingConfig.DEFAULT_CLEAR_COLOR);
        this.listener = null;
        this.originalText = new StringBuilder();
        this.intermediateText = new StringBuilder();
        this.workingLayout = (Layout)Layout.POOL.obtain();
        this.offsets = new FloatArray();
        this.sizing = new FloatArray();
        this.rotations = new FloatArray();
        this.trackingInput = false;
        this.selectable = false;
        this.lastTouchedIndex = -1;
        this.overIndex = -1;
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.dragging = false;
        this.activeEffects = (Array<Effect>)new Array((Class)Effect.class);
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.defaultToken = "";
        this.workingLayout.font(font);
        this.saveOriginalText(text);
    }
    
    public TypingLabel(String text, final Font font, final Color color) {
        super(text = Parser.preprocess(text), font, color);
        this.variables = (ObjectMap<String, String>)new ObjectMap();
        this.tokenEntries = (Array<TokenEntry>)new Array();
        this.clearColor = new Color(TypingConfig.DEFAULT_CLEAR_COLOR);
        this.listener = null;
        this.originalText = new StringBuilder();
        this.intermediateText = new StringBuilder();
        this.workingLayout = (Layout)Layout.POOL.obtain();
        this.offsets = new FloatArray();
        this.sizing = new FloatArray();
        this.rotations = new FloatArray();
        this.trackingInput = false;
        this.selectable = false;
        this.lastTouchedIndex = -1;
        this.overIndex = -1;
        this.selectionStart = -1;
        this.selectionEnd = -1;
        this.dragging = false;
        this.activeEffects = (Array<Effect>)new Array((Class)Effect.class);
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.defaultToken = "";
        this.workingLayout.font(font);
        this.workingLayout.setBaseColor(this.layout.baseColor);
        this.saveOriginalText(text);
    }
    
    @Override
    public void setText(final String newText) {
        this.setText(newText, true);
    }
    
    protected void setText(String newText, final boolean modifyOriginalText) {
        if (modifyOriginalText) {
            newText = Parser.preprocess(newText);
        }
        this.setText(newText, modifyOriginalText, true);
    }
    
    protected void setText(final String newText, final boolean modifyOriginalText, final boolean restart) {
        final boolean hasEnded = this.hasEnded();
        this.font.markup(newText, this.layout.clear());
        final float actualWidth = this.getWidth();
        this.workingLayout.setTargetWidth(actualWidth);
        this.font.markup(newText, this.workingLayout.clear());
        this.setWidth(actualWidth + ((this.style != null && this.style.background != null) ? (this.style.background.getLeftWidth() + this.style.background.getRightWidth()) : 0.0f));
        if (modifyOriginalText) {
            this.saveOriginalText(newText);
        }
        if (restart) {
            this.restart();
        }
        if (hasEnded) {
            this.skipToTheEnd(true, false);
        }
    }
    
    public StringBuilder getOriginalText() {
        return this.originalText;
    }
    
    protected void saveOriginalText(final CharSequence text) {
        if (text != this.originalText) {
            this.originalText.setLength(0);
            this.originalText.append(text);
        }
        this.originalText.trimToSize();
    }
    
    protected void restoreOriginalText() {
        super.setText(this.originalText.toString());
        this.parsed = false;
    }
    
    public TypingListener getTypingListener() {
        return this.listener;
    }
    
    public void setTypingListener(final TypingListener listener) {
        this.listener = listener;
    }
    
    public Color getClearColor() {
        return this.clearColor;
    }
    
    public String getDefaultToken() {
        return this.defaultToken;
    }
    
    public void setDefaultToken(final String defaultToken) {
        this.defaultToken = ((defaultToken == null) ? "" : defaultToken);
        this.parsed = false;
    }
    
    public void parseTokens() {
        this.setText(Parser.preprocess("{NORMAL}" + this.getDefaultToken() + (Object)this.originalText), false, false);
        Parser.parseTokens(this);
        this.parsed = true;
    }
    
    @Override
    public void skipToTheEnd() {
        this.skipToTheEnd(true);
    }
    
    public void skipToTheEnd(final boolean ignoreEvents) {
        this.skipToTheEnd(ignoreEvents, false);
    }
    
    public void skipToTheEnd(final boolean ignoreEvents, final boolean ignoreEffects) {
        this.skipping = true;
        this.ignoringEvents = ignoreEvents;
        this.ignoringEffects = ignoreEffects;
    }
    
    public void cancelSkipping() {
        if (this.skipping) {
            this.skipping = false;
            this.ignoringEvents = false;
            this.ignoringEffects = false;
        }
    }
    
    public boolean isSkipping() {
        return this.skipping;
    }
    
    public boolean isPaused() {
        return this.paused;
    }
    
    public void pause() {
        this.paused = true;
    }
    
    public void resume() {
        this.paused = false;
    }
    
    public boolean hasEnded() {
        return this.ended;
    }
    
    public void restart() {
        this.restart(this.getOriginalText().toString());
    }
    
    public void restart(final String newText) {
        this.workingLayout.baseColor = Color.WHITE_FLOAT_BITS;
        this.workingLayout.maxLines = Integer.MAX_VALUE;
        this.workingLayout.atLimit = false;
        this.workingLayout.ellipsis = null;
        Line.POOL.freeAll((Array)this.workingLayout.lines);
        this.workingLayout.lines.clear();
        this.workingLayout.lines.add(Line.POOL.obtain());
        this.offsets.clear();
        this.sizing.clear();
        this.rotations.clear();
        this.activeEffects.clear();
        this.textSpeed = TypingConfig.DEFAULT_SPEED_PER_CHAR;
        this.charCooldown = this.textSpeed;
        this.rawCharIndex = -2;
        this.glyphCharIndex = -1;
        this.glyphCharCompensation = 0;
        this.parsed = false;
        this.paused = false;
        this.ended = false;
        this.skipping = false;
        this.ignoringEvents = false;
        this.ignoringEffects = false;
        this.invalidate();
        this.saveOriginalText(newText);
        this.tokenEntries.clear();
        this.parseTokens();
    }
    
    public ObjectMap<String, String> getVariables() {
        return this.variables;
    }
    
    public void setVariable(final String var, final String value) {
        this.variables.put(var.toUpperCase(), value);
    }
    
    public void setVariables(final ObjectMap<String, String> variableMap) {
        this.variables.clear();
        for (final ObjectMap.Entry<String, String> entry : variableMap.entries()) {
            this.variables.put(((String)entry.key).toUpperCase(), entry.value);
        }
    }
    
    public void setVariables(final Map<String, String> variableMap) {
        this.variables.clear();
        for (final Map.Entry<String, String> entry : variableMap.entrySet()) {
            this.variables.put(entry.getKey().toUpperCase(), entry.getValue());
        }
    }
    
    public void clearVariables() {
        this.variables.clear();
    }
    
    public void act(final float delta) {
        super.act(delta);
        if (!this.parsed) {
            this.parseTokens();
        }
        Label_0064: {
            if (this.skipping || (!this.ended && !this.paused)) {
                if (!this.skipping) {
                    final float charCooldown = this.charCooldown - delta;
                    this.charCooldown = charCooldown;
                    if (charCooldown >= 0.0f) {
                        break Label_0064;
                    }
                }
                this.processCharProgression();
            }
        }
        this.font.calculateSize(this.workingLayout);
        final int glyphCount = this.getLayoutSize(this.layout);
        this.offsets.setSize(glyphCount + glyphCount);
        Arrays.fill(this.offsets.items, 0, glyphCount + glyphCount, 0.0f);
        this.sizing.setSize(glyphCount + glyphCount);
        Arrays.fill(this.sizing.items, 0, glyphCount + glyphCount, 1.0f);
        this.rotations.setSize(glyphCount);
        Arrays.fill(this.rotations.items, 0, glyphCount, 0.0f);
        if (!this.ignoringEffects) {
            final int workingLayoutSize = this.getLayoutSize(this.workingLayout);
            for (int i = this.activeEffects.size - 1; i >= 0; --i) {
                final Effect effect = (Effect)this.activeEffects.get(i);
                effect.update(delta);
                final int start = effect.indexStart;
                final int end = (effect.indexEnd >= 0) ? effect.indexEnd : this.glyphCharIndex;
                if (effect.isFinished()) {
                    this.activeEffects.removeIndex(i);
                }
                else {
                    for (int j = Math.max(0, start); j <= this.glyphCharIndex && j <= end && j < workingLayoutSize; ++j) {
                        final long glyph = this.getInLayout(this.workingLayout, j);
                        if (glyph == 16777215L) {
                            break;
                        }
                        effect.apply(glyph, j, delta);
                    }
                }
            }
        }
    }
    
    private float randomize(final int seed) {
        return NumberUtils.intBitsToFloat((int)(((long)seed ^ 0x9E3779B97F4A7C15L) * -3335678366873096957L >>> 41) | 0x40000000) - 4.4f;
    }
    
    private void processCharProgression() {
        int charCounter = 0;
    Label_0722:
        while (this.skipping || this.charCooldown < 0.0f) {
            if (this.glyphCharCompensation != 0) {
                if (this.glyphCharCompensation > 0) {
                    ++this.glyphCharIndex;
                    --this.glyphCharCompensation;
                }
                else {
                    --this.glyphCharIndex;
                    ++this.glyphCharCompensation;
                }
                if (this.textSpeed < 0.0f) {
                    this.charCooldown += this.textSpeed * this.randomize(this.glyphCharIndex);
                }
                else {
                    this.charCooldown += this.textSpeed;
                }
            }
            else {
                ++this.rawCharIndex;
                final int layoutSize = this.getLayoutSize(this.layout);
                if (layoutSize == 0 || this.glyphCharIndex >= layoutSize) {
                    if (this.ended) {
                        break;
                    }
                    this.ended = true;
                    this.skipping = false;
                    if (this.listener != null) {
                        this.listener.end();
                        break;
                    }
                    break;
                }
                else if (this.tokenEntries.size > 0 && ((TokenEntry)this.tokenEntries.peek()).index == this.rawCharIndex) {
                    final TokenEntry entry = (TokenEntry)this.tokenEntries.pop();
                    final String token = entry.token;
                    final TokenCategory category = entry.category;
                    this.rawCharIndex = entry.endIndex - 1;
                    switch (category) {
                        case SPEED: {
                            this.textSpeed = entry.floatValue;
                            continue;
                        }
                        case WAIT: {
                            this.charCooldown += entry.floatValue;
                            continue;
                        }
                        case EVENT: {
                            this.triggerEvent(entry.stringValue, false);
                            continue;
                        }
                        case EFFECT_START:
                        case EFFECT_END: {
                            final boolean isStart = category == TokenCategory.EFFECT_START;
                            final Class<? extends Effect> effectClass = (Class<? extends Effect>)(isStart ? TypingConfig.EFFECT_START_TOKENS.get((String) token) : ((Class)TypingConfig.EFFECT_END_TOKENS.get((String) token)));
                            for (int i = 0; i < this.activeEffects.size; ++i) {
                                final Effect effect = (Effect)this.activeEffects.get(i);
                                if (effect.indexEnd < 0 && ClassReflection.isAssignableFrom((Class)effectClass, (Class)effect.getClass())) {
                                    effect.indexEnd = this.glyphCharIndex;
                                }
                            }
                            if (isStart) {
                                entry.effect.indexStart = this.glyphCharIndex + 1;
                                this.activeEffects.add(entry.effect);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            break Label_0722;
                        }
                    }
                }
                else {
                    final int safeIndex = MathUtils.clamp(this.glyphCharIndex + 1, 0, layoutSize - 1);
                    if (layoutSize > 0) {
                        final long baseChar = this.getInLayout(this.layout, safeIndex);
                        final float intervalMultiplier = TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.get((int)(char)baseChar, 1.0f);
                        if (this.textSpeed < 0.0f) {
                            this.charCooldown += this.textSpeed * this.randomize(this.glyphCharIndex) * intervalMultiplier;
                        }
                        else {
                            this.charCooldown += this.textSpeed * intervalMultiplier;
                        }
                    }
                    ++charCounter;
                    if (this.rawCharIndex > 0) {
                        ++this.glyphCharIndex;
                    }
                    if (this.glyphCharIndex >= 0 && this.glyphCharIndex < layoutSize && this.rawCharIndex >= 0 && this.listener != null) {
                        this.listener.onChar(this.getInLayout(this.layout, this.glyphCharIndex));
                    }
                    if (this.glyphCharIndex == 0) {
                        this.charCooldown = Math.abs(this.textSpeed);
                        break;
                    }
                    ++charCounter;
                    final int charLimit = TypingConfig.CHAR_LIMIT_PER_FRAME;
                    if (!this.skipping && charLimit > 0 && charCounter > charLimit) {
                        this.charCooldown = Math.max(this.charCooldown, Math.abs(this.textSpeed));
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.wrap) {
            final float actualWidth = this.getWidth();
            this.workingLayout.setTargetWidth(actualWidth);
            this.font.regenerateLayout(this.workingLayout);
        }
        else {
            this.font.calculateSize(this.workingLayout);
        }
        this.invalidateHierarchy();
    }
    
    private int getLayoutSize(final Layout layout) {
        int layoutSize = 0;
        for (int i = 0, n = layout.lines(); i < n; ++i) {
            layoutSize += layout.getLine(i).glyphs.size;
        }
        return layoutSize;
    }
    
    public boolean remove() {
        Layout.POOL.free(this.workingLayout);
        Layout.POOL.free(this.layout);
        return super.remove();
    }
    
    @Override
    public void setSize(final float width, final float height) {
        super.setSize(width, height);
        if (this.wrap) {
            this.workingLayout.setTargetWidth(width);
        }
    }
    
    @Override
    public void layout() {
        super.layout();
        if (this.wrap && this.workingLayout.getTargetWidth() != this.getWidth()) {
            this.font.regenerateLayout(this.workingLayout);
        }
    }
    
    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.validate();
        final float rot = this.getRotation();
        final float originX = this.getOriginX();
        final float originY = this.getOriginY();
        final float sn = MathUtils.sinDeg(rot);
        final float cs = MathUtils.cosDeg(rot);
        final Color set = batch.getColor().set(this.getColor());
        set.a *= parentAlpha;
        batch.setColor(batch.getColor());
        final int lines = this.workingLayout.lines();
        float baseX = this.getX();
        float baseY = this.getY();
        float height = this.workingLayout.getHeight();
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
            ((TransformDrawable)background).draw(batch, this.getX(), this.getY(), originX, originY, this.getWidth(), this.getHeight(), 1.0f, 1.0f, rot);
        }
        if (this.layout.lines.isEmpty()) {
            return;
        }
        int o = 0;
        int s = 0;
        int r = 0;
        int gi = 0;
        final boolean resetShader = this.font.distanceField != Font.DistanceFieldType.STANDARD && batch.getShader() != this.font.shader;
        if (resetShader) {
            this.font.enableShader(batch);
        }
        baseX -= 0.5f * this.font.cellWidth;
        baseY -= 0.5f * this.font.cellHeight;
        baseX += cs * 0.5f * this.font.cellWidth;
        baseY += sn * 0.5f * this.font.cellWidth;
        baseX -= sn * 0.5f * this.font.cellHeight;
        baseY += cs * 0.5f * this.font.cellHeight;
        int globalIndex = -1;
        int inX = 0;
        int inY = 0;
        if (this.trackingInput) {
            inX = Gdx.input.getX();
            inY = Gdx.graphics.getBackBufferHeight() - Gdx.input.getY();
            if (!Gdx.input.isTouched()) {
                this.lastTouchedIndex = ((inY < this.getY()) ? -2 : ((inY > this.getY() + this.getHeight()) ? -1 : ((inX < this.getX()) ? -1 : ((inX > this.getX() + this.getWidth()) ? -2 : -1))));
            }
            this.overIndex = -1;
        }
        float single = 0.0f;
    Label_1937:
        for (int ln = 0; ln < lines; ++ln) {
            final Line glyphs = this.workingLayout.getLine(ln);
            baseX += sn * glyphs.height;
            baseY -= cs * glyphs.height;
            if (glyphs.glyphs.size != 0) {
                float x = baseX;
                float y = baseY;
                final float worldOriginX = x + originX;
                final float worldOriginY = y + originY;
                final float fx = -originX;
                final float fy = -originY;
                x = cs * fx - sn * fy + worldOriginX;
                y = sn * fx + cs * fy + worldOriginY;
                float xChange = 0.0f;
                float yChange = 0.0f;
                if (Align.isCenterHorizontal(this.align)) {
                    x -= cs * (glyphs.width * 0.5f);
                    y -= sn * (glyphs.width * 0.5f);
                }
                else if (Align.isRight(this.align)) {
                    x -= cs * glyphs.width;
                    y -= sn * glyphs.width;
                }
                Font f = null;
                int kern = -1;
                int i = 0;
                final int n = glyphs.glyphs.size;
                final int end = this.glyphCharIndex;
                for (int lim = Math.min(Math.min(this.rotations.size, this.offsets.size >> 1), this.sizing.size >> 1); i < n && r < lim; ++i, ++gi) {
                    if (gi > end) {
                        break Label_1937;
                    }
                    long glyph = glyphs.glyphs.get(i);
                    if (this.font.family != null) {
                        f = this.font.family.connected[(int)(glyph >>> 16 & 0xFL)];
                    }
                    if (f == null) {
                        f = this.font;
                    }
                    if (f.kerning != null) {
                        kern = (kern << 16 | (int)((glyph = glyphs.glyphs.get(i)) & 0xFFFFL));
                        final float amt = f.kerning.get(kern, 0) * f.scaleX * ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                        xChange += cs * amt;
                        yChange += sn * amt;
                    }
                    else {
                        kern = -1;
                    }
                    if (i == 0) {
                        final Font.GlyphRegion reg = (Font.GlyphRegion)this.font.mapping.get((int)(char)glyph);
                        if (reg != null && reg.offsetX < 0.0f) {
                            final float ox = reg.offsetX * f.scaleX * ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                            xChange -= cs * ox;
                            yChange -= sn * ox;
                        }
                    }
                    ++globalIndex;
                    int bgc;
                    if (this.selectable && this.selectionStart <= globalIndex && this.selectionEnd > globalIndex) {
                        bgc = ColorUtils.offsetLightness((int)(glyph >>> 32), 0.5f);
                    }
                    else {
                        bgc = 0;
                    }
                    final float xx = x + xChange + this.offsets.get(o++);
                    final float yy = y + yChange + this.offsets.get(o++);
                    final float oldSingle = single;
                    single = f.drawGlyph(batch, glyph, xx, yy, this.rotations.get(r++) + rot, this.sizing.get(s++), this.sizing.get(s++), bgc);
                    if (this.trackingInput && xx <= inX + oldSingle * 0.5f && inX <= xx + single * 0.5f && yy <= inY && inY <= yy + glyphs.height) {
                        this.overIndex = globalIndex;
                        if (this.isTouchable()) {
                            if (Gdx.input.justTouched()) {
                                this.lastTouchedIndex = globalIndex;
                                this.selectionStart = -1;
                                this.selectionEnd = -1;
                            }
                            else if (this.selectable) {
                                if (Gdx.input.isTouched()) {
                                    final int adjustedIndex = (this.lastTouchedIndex == -2) ? this.getLayoutSize(this.workingLayout) : this.lastTouchedIndex;
                                    this.selectionStart = Math.min(adjustedIndex, globalIndex);
                                    this.selectionEnd = Math.max(adjustedIndex, globalIndex);
                                    this.dragging = true;
                                }
                                else if (this.dragging) {
                                    this.dragging = false;
                                    if (this.selectionStart != this.selectionEnd) {
                                        this.triggerEvent("*SELECTED", true);
                                    }
                                    else {
                                        final int n2 = -1;
                                        this.selectionEnd = n2;
                                        this.selectionStart = n2;
                                    }
                                }
                            }
                        }
                    }
                    xChange += cs * single;
                    yChange += sn * single;
                }
            }
        }
        this.invalidateHierarchy();
        if (resetShader) {
            batch.setShader((ShaderProgram)null);
        }
    }
    
    public String toString() {
        return this.substring(0, Integer.MAX_VALUE);
    }
    
    public String getSelectedText() {
        if (!this.selectable || (this.selectionStart == this.selectionEnd && this.selectionStart < 0)) {
            return "";
        }
        return this.substring(this.selectionStart, this.selectionEnd);
    }
    
    public boolean copySelectedText() {
        if (!this.selectable || (this.selectionStart == this.selectionEnd && this.selectionStart < 0)) {
            return false;
        }
        Gdx.app.getClipboard().setContents(this.substring(this.selectionStart, this.selectionEnd));
        return true;
    }
    
    public void setIntermediateText(final CharSequence text, final boolean modifyOriginalText, final boolean restart) {
        final boolean hasEnded = this.hasEnded();
        if (text != this.intermediateText) {
            this.intermediateText.setLength(0);
            this.intermediateText.append(text);
        }
        this.intermediateText.trimToSize();
        if (modifyOriginalText) {
            this.saveOriginalText(text);
        }
        if (restart) {
            this.restart();
        }
        if (hasEnded) {
            this.skipToTheEnd(true, false);
        }
    }
    
    public StringBuilder getIntermediateText() {
        return this.intermediateText;
    }
    
    public long getInLayout(final Layout layout, int index) {
        LongArray glyphs;
        for (int i = 0, n = layout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = layout.getLine(i).glyphs;
            if (index < glyphs.size) {
                return glyphs.get(index);
            }
        }
        return 16777215L;
    }
    
    public long getInWorkingLayout(int index) {
        LongArray glyphs;
        for (int i = 0, n = this.workingLayout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = this.workingLayout.getLine(i).glyphs;
            if (index < glyphs.size) {
                return glyphs.get(index);
            }
        }
        return 16777215L;
    }
    
    public String substring(int start, int end) {
        start = Math.max(0, start);
        end = Math.min(this.getLayoutSize(this.workingLayout), end);
        int index = start;
        final StringBuilder sb = new StringBuilder(end - start);
        int glyphCount = 0;
        for (int i = 0, n = this.workingLayout.lines(); i < n && index >= 0; ++i) {
            final LongArray glyphs = this.workingLayout.getLine(i).glyphs;
            if (index < glyphs.size) {
                for (int fin = index - start - glyphCount + end; index < fin && index < glyphs.size; ++index) {
                    final char c = (char)glyphs.get(index);
                    if (c >= '\ue000' && c <= '\uf800') {
                        final String name = (String)this.font.namesByCharCode.get((int)c);
                        if (name != null) {
                            sb.append(name);
                        }
                        else {
                            sb.append(c);
                        }
                    }
                    else if (c == '\u0002') {
                        sb.append('[');
                    }
                    else {
                        sb.append(c);
                    }
                    ++glyphCount;
                }
                if (glyphCount == end - start) {
                    return sb.toString();
                }
                index = 0;
            }
            else {
                index -= glyphs.size;
            }
        }
        return "";
    }
    
    public Line getLineInLayout(final Layout layout, int index) {
        LongArray glyphs;
        for (int i = 0, n = layout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = layout.getLine(i).glyphs;
            if (index < glyphs.size) {
                return layout.getLine(i);
            }
        }
        return null;
    }
    
    public float getLineHeight(int index) {
        LongArray glyphs;
        for (int i = 0, n = this.workingLayout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = this.workingLayout.getLine(i).glyphs;
            if (index < glyphs.size) {
                return this.workingLayout.getLine(i).height;
            }
        }
        return this.font.cellHeight;
    }
    
    public long getFromIntermediate(final int index) {
        if (index >= 0 && this.intermediateText.length() > index) {
            return this.intermediateText.charAt(index);
        }
        return 16777215L;
    }
    
    public void setInLayout(final Layout layout, int index, final long newGlyph) {
        LongArray glyphs;
        for (int i = 0, n = layout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = layout.getLine(i).glyphs;
            if (index < glyphs.size) {
                glyphs.set(index, newGlyph);
                return;
            }
        }
    }
    
    public void insertInLayout(final Layout layout, int index, final long newGlyph) {
        LongArray glyphs;
        for (int i = 0, n = layout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = layout.getLine(i).glyphs;
            if (index <= glyphs.size) {
                glyphs.insert(index, newGlyph);
                return;
            }
        }
    }
    
    public void insertInLayout(final Layout layout, int index, final CharSequence text) {
        long current = -8589934592L;
        LongArray glyphs;
        for (int i = 0, n = layout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = layout.getLine(i).glyphs;
            if (index < glyphs.size) {
                current = (glyphs.get(index) & 0xFFFFFFFFFFFF0000L);
                for (int j = 0; j < text.length(); ++j) {
                    glyphs.insert(index + j, current | (long)text.charAt(j));
                }
                return;
            }
            if (index == glyphs.size) {
                if (index != 0) {
                    current = (glyphs.get(index - 1) & 0xFFFFFFFFFFFF0000L);
                }
                for (int j = 0; j < text.length(); ++j) {
                    glyphs.insert(index + j, current | (long)text.charAt(j));
                }
                return;
            }
        }
    }
    
    public void setInWorkingLayout(int index, final long newGlyph) {
        LongArray glyphs;
        for (int i = 0, n = this.workingLayout.lines(); i < n && index >= 0; index -= glyphs.size, ++i) {
            glyphs = this.workingLayout.getLine(i).glyphs;
            if (i < this.workingLayout.lines() && index < glyphs.size) {
                glyphs.set(index, newGlyph);
                return;
            }
        }
    }
    
    public int length() {
        int len = 0;
        for (int i = 0, n = this.workingLayout.lines(); i < n; ++i) {
            len += this.workingLayout.getLine(i).glyphs.size;
        }
        return len;
    }
    
    public void triggerEvent(final String event, final boolean always) {
        if (this.listener != null && (always || !this.ignoringEvents)) {
            this.listener.event(event);
        }
    }
    
    public boolean isSelectable() {
        return this.selectable && this.trackingInput;
    }
    
    public TypingLabel setSelectable(final boolean selectable) {
        this.selectable = selectable;
        this.trackingInput |= selectable;
        return this;
    }
}
