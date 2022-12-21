// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextraWindow extends Table
{
    private static final Vector2 tmpPosition;
    private static final Vector2 tmpSize;
    private static final int MOVE = 32;
    private Window.WindowStyle style;
    boolean isMovable;
    boolean isModal;
    boolean isResizable;
    int resizeBorder;
    boolean keepWithinStage;
    TextraLabel titleLabel;
    Table titleTable;
    boolean drawTitleTable;
    protected int edge;
    protected boolean dragging;
    protected Font font;
    
    public TextraWindow(final String title, final Skin skin) {
        this(title, (Window.WindowStyle)skin.get((Class)Window.WindowStyle.class));
        this.setSkin(skin);
    }
    
    public TextraWindow(final String title, final Skin skin, final String styleName) {
        this(title, (Window.WindowStyle)skin.get(styleName, (Class)Window.WindowStyle.class));
        this.setSkin(skin);
    }
    
    public TextraWindow(final String title, final Window.WindowStyle style) {
        this(title, style, false);
    }
    
    public TextraWindow(final String title, final Window.WindowStyle style, final boolean makeGridGlyphs) {
        this(title, style, new Font(style.titleFont, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs));
    }
    
    public TextraWindow(final String title, final Skin skin, final Font replacementFont) {
        this(title, (Window.WindowStyle)skin.get((Class)Window.WindowStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public TextraWindow(final String title, final Skin skin, final String styleName, final Font replacementFont) {
        this(title, (Window.WindowStyle)skin.get(styleName, (Class)Window.WindowStyle.class), replacementFont);
        this.setSkin(skin);
    }
    
    public TextraWindow(final String title, final Window.WindowStyle style, final Font replacementFont) {
        this.isMovable = true;
        this.resizeBorder = 8;
        this.keepWithinStage = true;
        this.font = null;
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }
        this.setTouchable(Touchable.enabled);
        this.setClip(true);
        this.titleLabel = this.newLabel(title, replacementFont, style.titleFontColor);
        this.font = this.titleLabel.getFont();
        this.titleLabel.layout.ellipsis = "...";
        this.titleTable = new Table() {
            public void draw(final Batch batch, final float parentAlpha) {
                if (TextraWindow.this.drawTitleTable) {
                    super.draw(batch, parentAlpha);
                }
            }
        };
        this.titleTable.add((Actor)this.titleLabel).expandX().fillX().minWidth(0.0f);
        this.addActor((Actor)this.titleTable);
        this.setStyle(style, replacementFont);
        this.setWidth(150.0f);
        this.setHeight(150.0f);
        this.addCaptureListener((EventListener)new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                TextraWindow.this.toFront();
                return false;
            }
        });
        this.addListener((EventListener)new InternalListener());
    }
    
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TextraLabel(text, style);
    }
    
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return (color == null) ? new TextraLabel(text, font) : new TextraLabel(text, font, color);
    }
    
    public void setStyle(final Window.WindowStyle style) {
        this.setStyle(style, false);
    }
    
    public void setStyle(final Window.WindowStyle style, final boolean makeGridGlyphs) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        this.setBackground(style.background);
        this.titleLabel.setFont(this.font = new Font(style.titleFont, Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, makeGridGlyphs));
        if (style.titleFontColor != null) {
            this.titleLabel.setColor(style.titleFontColor);
        }
        this.invalidateHierarchy();
    }
    
    public void setStyle(final Window.WindowStyle style, final Font font) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        this.setBackground(style.background);
        this.titleLabel.setFont(this.font = font);
        if (style.titleFontColor != null) {
            this.titleLabel.setColor(style.titleFontColor);
        }
        this.invalidateHierarchy();
    }
    
    public Window.WindowStyle getStyle() {
        return this.style;
    }
    
    public void keepWithinStage() {
        if (!this.keepWithinStage) {
            return;
        }
        final Stage stage = this.getStage();
        if (stage == null) {
            return;
        }
        final Camera camera = stage.getCamera();
        if (camera instanceof OrthographicCamera) {
            final OrthographicCamera orthographicCamera = (OrthographicCamera)camera;
            final float parentWidth = stage.getWidth();
            final float parentHeight = stage.getHeight();
            if (this.getX(16) - camera.position.x > parentWidth / 2.0f / orthographicCamera.zoom) {
                this.setPosition(camera.position.x + parentWidth / 2.0f / orthographicCamera.zoom, this.getY(16), 16);
            }
            if (this.getX(8) - camera.position.x < -parentWidth / 2.0f / orthographicCamera.zoom) {
                this.setPosition(camera.position.x - parentWidth / 2.0f / orthographicCamera.zoom, this.getY(8), 8);
            }
            if (this.getY(2) - camera.position.y > parentHeight / 2.0f / orthographicCamera.zoom) {
                this.setPosition(this.getX(2), camera.position.y + parentHeight / 2.0f / orthographicCamera.zoom, 2);
            }
            if (this.getY(4) - camera.position.y < -parentHeight / 2.0f / orthographicCamera.zoom) {
                this.setPosition(this.getX(4), camera.position.y - parentHeight / 2.0f / orthographicCamera.zoom, 4);
            }
        }
        else if (this.getParent() == stage.getRoot()) {
            final float parentWidth2 = stage.getWidth();
            final float parentHeight2 = stage.getHeight();
            if (this.getX() < 0.0f) {
                this.setX(0.0f);
            }
            if (this.getRight() > parentWidth2) {
                this.setX(parentWidth2 - this.getWidth());
            }
            if (this.getY() < 0.0f) {
                this.setY(0.0f);
            }
            if (this.getTop() > parentHeight2) {
                this.setY(parentHeight2 - this.getHeight());
            }
        }
    }
    
    public void draw(final Batch batch, final float parentAlpha) {
        final Stage stage = this.getStage();
        if (stage != null) {
            if (stage.getKeyboardFocus() == null) {
                stage.setKeyboardFocus((Actor)this);
            }
            this.keepWithinStage();
            if (this.style.stageBackground != null) {
                this.stageToLocalCoordinates(TextraWindow.tmpPosition.set(0.0f, 0.0f));
                this.stageToLocalCoordinates(TextraWindow.tmpSize.set(stage.getWidth(), stage.getHeight()));
                this.drawStageBackground(batch, parentAlpha, this.getX() + TextraWindow.tmpPosition.x, this.getY() + TextraWindow.tmpPosition.y, this.getX() + TextraWindow.tmpSize.x, this.getY() + TextraWindow.tmpSize.y);
            }
        }
        super.draw(batch, parentAlpha);
    }
    
    protected void drawStageBackground(final Batch batch, final float parentAlpha, final float x, final float y, final float width, final float height) {
        final Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        this.style.stageBackground.draw(batch, x, y, width, height);
    }
    
    protected void drawBackground(final Batch batch, final float parentAlpha, final float x, final float y) {
        super.drawBackground(batch, parentAlpha, x, y);
        this.titleTable.getColor().a = this.getColor().a;
        final float padTop = this.getPadTop();
        final float padLeft = this.getPadLeft();
        this.titleTable.setSize(this.getWidth() - padLeft - this.getPadRight(), padTop);
        this.titleTable.setPosition(padLeft, this.getHeight() - padTop);
        this.drawTitleTable = true;
        this.titleTable.draw(batch, parentAlpha);
        this.drawTitleTable = false;
    }
    
    @Null
    public Actor hit(final float x, final float y, final boolean touchable) {
        if (!this.isVisible()) {
            return null;
        }
        final Actor hit = super.hit(x, y, touchable);
        if (hit == null && this.isModal && (!touchable || this.getTouchable() == Touchable.enabled)) {
            return (Actor)this;
        }
        final float height = this.getHeight();
        if (hit == null || hit == this) {
            return hit;
        }
        if (y <= height && y >= height - this.getPadTop() && x >= 0.0f && x <= this.getWidth()) {
            Actor current;
            for (current = hit; current.getParent() != this; current = (Actor)current.getParent()) {}
            if (this.getCell(current) != null) {
                return (Actor)this;
            }
        }
        return hit;
    }
    
    public boolean isMovable() {
        return this.isMovable;
    }
    
    public void setMovable(final boolean isMovable) {
        this.isMovable = isMovable;
    }
    
    public boolean isModal() {
        return this.isModal;
    }
    
    public void setModal(final boolean isModal) {
        this.isModal = isModal;
    }
    
    public void setKeepWithinStage(final boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }
    
    public boolean isResizable() {
        return this.isResizable;
    }
    
    public void setResizable(final boolean isResizable) {
        this.isResizable = isResizable;
    }
    
    public void setResizeBorder(final int resizeBorder) {
        this.resizeBorder = resizeBorder;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public float getPrefWidth() {
        return Math.max(super.getPrefWidth(), this.titleTable.getPrefWidth() + this.getPadLeft() + this.getPadRight());
    }
    
    public Table getTitleTable() {
        return this.titleTable;
    }
    
    public TextraLabel getTitleLabel() {
        return this.titleLabel;
    }
    
    public void skipToTheEnd() {
        this.titleLabel.skipToTheEnd();
    }
    
    static {
        tmpPosition = new Vector2();
        tmpSize = new Vector2();
    }
    
    class InternalListener extends InputListener
    {
        float startX;
        float startY;
        float lastX;
        float lastY;
        
        private void updateEdge(final float x, final float y) {
            float border = TextraWindow.this.resizeBorder / 2.0f;
            final float width = TextraWindow.this.getWidth();
            final float height = TextraWindow.this.getHeight();
            final float padTop = TextraWindow.this.getPadTop();
            final float padLeft = TextraWindow.this.getPadLeft();
            final float padBottom = TextraWindow.this.getPadBottom();
            final float padRight = TextraWindow.this.getPadRight();
            final float left = padLeft;
            final float right = width - padRight;
            final float bottom = padBottom;
            TextraWindow.this.edge = 0;
            if (TextraWindow.this.isResizable && x >= left - border && x <= right + border && y >= bottom - border) {
                if (x < left + border) {
                    final TextraWindow this$0 = TextraWindow.this;
                    this$0.edge |= 0x8;
                }
                if (x > right - border) {
                    final TextraWindow this$2 = TextraWindow.this;
                    this$2.edge |= 0x10;
                }
                if (y < bottom + border) {
                    final TextraWindow this$3 = TextraWindow.this;
                    this$3.edge |= 0x4;
                }
                if (TextraWindow.this.edge != 0) {
                    border += 25.0f;
                }
                if (x < left + border) {
                    final TextraWindow this$4 = TextraWindow.this;
                    this$4.edge |= 0x8;
                }
                if (x > right - border) {
                    final TextraWindow this$5 = TextraWindow.this;
                    this$5.edge |= 0x10;
                }
                if (y < bottom + border) {
                    final TextraWindow this$6 = TextraWindow.this;
                    this$6.edge |= 0x4;
                }
            }
            if (TextraWindow.this.isMovable && TextraWindow.this.edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right) {
                TextraWindow.this.edge = 32;
            }
        }
        
        public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
            if (button == 0) {
                this.updateEdge(x, y);
                TextraWindow.this.dragging = (TextraWindow.this.edge != 0);
                this.startX = x;
                this.startY = y;
                this.lastX = x - TextraWindow.this.getWidth();
                this.lastY = y - TextraWindow.this.getHeight();
            }
            return TextraWindow.this.edge != 0 || TextraWindow.this.isModal;
        }
        
        public void touchUp(final InputEvent event, final float x, final float y, final int pointer, final int button) {
            TextraWindow.this.dragging = false;
        }
        
        public void touchDragged(final InputEvent event, final float x, final float y, final int pointer) {
            if (!TextraWindow.this.dragging) {
                return;
            }
            float width = TextraWindow.this.getWidth();
            float height = TextraWindow.this.getHeight();
            float windowX = TextraWindow.this.getX();
            float windowY = TextraWindow.this.getY();
            final float minWidth = TextraWindow.this.getMinWidth();
            final float maxWidth = TextraWindow.this.getMaxWidth();
            final float minHeight = TextraWindow.this.getMinHeight();
            final float maxHeight = TextraWindow.this.getMaxHeight();
            final Stage stage = TextraWindow.this.getStage();
            final boolean clampPosition = TextraWindow.this.keepWithinStage && stage != null && TextraWindow.this.getParent() == stage.getRoot();
            if ((TextraWindow.this.edge & 0x20) != 0x0) {
                final float amountX = x - this.startX;
                final float amountY = y - this.startY;
                windowX += amountX;
                windowY += amountY;
            }
            if ((TextraWindow.this.edge & 0x8) != 0x0) {
                float amountX = x - this.startX;
                if (width - amountX < minWidth) {
                    amountX = -(minWidth - width);
                }
                if (clampPosition && windowX + amountX < 0.0f) {
                    amountX = -windowX;
                }
                width -= amountX;
                windowX += amountX;
            }
            if ((TextraWindow.this.edge & 0x4) != 0x0) {
                float amountY2 = y - this.startY;
                if (height - amountY2 < minHeight) {
                    amountY2 = -(minHeight - height);
                }
                if (clampPosition && windowY + amountY2 < 0.0f) {
                    amountY2 = -windowY;
                }
                height -= amountY2;
                windowY += amountY2;
            }
            if ((TextraWindow.this.edge & 0x10) != 0x0) {
                float amountX = x - this.lastX - width;
                if (width + amountX < minWidth) {
                    amountX = minWidth - width;
                }
                if (clampPosition && windowX + width + amountX > stage.getWidth()) {
                    amountX = stage.getWidth() - windowX - width;
                }
                width += amountX;
            }
            if ((TextraWindow.this.edge & 0x2) != 0x0) {
                float amountY2 = y - this.lastY - height;
                if (height + amountY2 < minHeight) {
                    amountY2 = minHeight - height;
                }
                if (clampPosition && windowY + height + amountY2 > stage.getHeight()) {
                    amountY2 = stage.getHeight() - windowY - height;
                }
                height += amountY2;
            }
            TextraWindow.this.setBounds((float)Math.round(windowX), (float)Math.round(windowY), (float)Math.round(width), (float)Math.round(height));
        }
        
        public boolean mouseMoved(final InputEvent event, final float x, final float y) {
            this.updateEdge(x, y);
            return TextraWindow.this.isModal;
        }
        
        public boolean scrolled(final InputEvent event, final float x, final float y, final int amount) {
            return TextraWindow.this.isModal;
        }
        
        public boolean keyDown(final InputEvent event, final int keycode) {
            return TextraWindow.this.isModal;
        }
        
        public boolean keyUp(final InputEvent event, final int keycode) {
            return TextraWindow.this.isModal;
        }
        
        public boolean keyTyped(final InputEvent event, final char character) {
            return TextraWindow.this.isModal;
        }
    }
}
