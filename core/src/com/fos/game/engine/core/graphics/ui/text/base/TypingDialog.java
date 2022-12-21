// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TypingDialog extends TypingWindow
{
    Table contentTable;
    Table buttonTable;
    @Null
    private Skin skin;
    ObjectMap<Actor, Object> values;
    boolean cancelHide;
    Actor previousKeyboardFocus;
    Actor previousScrollFocus;
    FocusListener focusListener;
    protected InputListener ignoreTouchDown;
    
    public TypingDialog(final String title, final Skin skin) {
        super(title, (Window.WindowStyle)skin.get((Class)Window.WindowStyle.class));
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }
    
    public TypingDialog(final String title, final Skin skin, final String windowStyleName) {
        super(title, (Window.WindowStyle)skin.get(windowStyleName, (Class)Window.WindowStyle.class));
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }
    
    public TypingDialog(final String title, final Window.WindowStyle windowStyle) {
        super(title, windowStyle);
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.initialize();
    }
    
    public TypingDialog(final String title, final Skin skin, final Font replacementFont) {
        super(title, (Window.WindowStyle)skin.get((Class)Window.WindowStyle.class), replacementFont);
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }
    
    public TypingDialog(final String title, final Skin skin, final String windowStyleName, final Font replacementFont) {
        super(title, (Window.WindowStyle)skin.get(windowStyleName, (Class)Window.WindowStyle.class), replacementFont);
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }
    
    public TypingDialog(final String title, final Window.WindowStyle windowStyle, final Font replacementFont) {
        super(title, windowStyle, replacementFont);
        this.values = (ObjectMap<Actor, Object>)new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
                event.cancel();
                return false;
            }
        };
        this.initialize();
    }
    
    @Override
    protected TextraLabel newLabel(final String text, final Label.LabelStyle style) {
        return new TextraLabel(text, style);
    }
    
    @Override
    protected TextraLabel newLabel(final String text, final Font font, final Color color) {
        return new TextraLabel(text, font, color);
    }
    
    protected TypingLabel newTypingLabel(final String text, final Label.LabelStyle style) {
        return new TypingLabel(text, style);
    }
    
    protected TypingLabel newTypingLabel(final String text, final Font font, final Color color) {
        return new TypingLabel(text, font, color);
    }
    
    private void initialize() {
        this.setModal(true);
        this.defaults().space(6.0f);
        final Table contentTable = new Table(this.skin);
        this.contentTable = contentTable;
        this.add((Actor)contentTable).expand().fill();
        this.row();
        final Table buttonTable = new Table(this.skin);
        this.buttonTable = buttonTable;
        this.add((Actor)buttonTable).fillX();
        this.contentTable.defaults().space(6.0f);
        this.buttonTable.defaults().space(6.0f);
        this.buttonTable.addListener((EventListener)new ChangeListener() {
            public void changed(final ChangeListener.ChangeEvent event, Actor actor) {
                if (!TypingDialog.this.values.containsKey(actor)) {
                    return;
                }
                while (actor.getParent() != TypingDialog.this.buttonTable) {
                    actor = (Actor)actor.getParent();
                }
                TypingDialog.this.result(TypingDialog.this.values.get(actor));
                if (!TypingDialog.this.cancelHide) {
                    TypingDialog.this.hide();
                }
                TypingDialog.this.cancelHide = false;
            }
        });
        this.focusListener = new FocusListener() {
            public void keyboardFocusChanged(final FocusListener.FocusEvent event, final Actor actor, final boolean focused) {
                if (!focused) {
                    this.focusChanged(event);
                }
            }
            
            public void scrollFocusChanged(final FocusListener.FocusEvent event, final Actor actor, final boolean focused) {
                if (!focused) {
                    this.focusChanged(event);
                }
            }
            
            private void focusChanged(final FocusListener.FocusEvent event) {
                final Stage stage = TypingDialog.this.getStage();
                if (TypingDialog.this.isModal && stage != null && stage.getRoot().getChildren().size > 0 && stage.getRoot().getChildren().peek() == TypingDialog.this) {
                    final Actor newFocusedActor = event.getRelatedActor();
                    if (newFocusedActor != null && !newFocusedActor.isDescendantOf((Actor)TypingDialog.this) && !newFocusedActor.equals(TypingDialog.this.previousKeyboardFocus) && !newFocusedActor.equals(TypingDialog.this.previousScrollFocus)) {
                        event.cancel();
                    }
                }
            }
        };
    }
    
    protected void setStage(final Stage stage) {
        if (stage == null) {
            this.addListener((EventListener)this.focusListener);
        }
        else {
            this.removeListener((EventListener)this.focusListener);
        }
        super.setStage(stage);
    }
    
    public Table getContentTable() {
        return this.contentTable;
    }
    
    public Table getButtonTable() {
        return this.buttonTable;
    }
    
    public TypingDialog text(@Null final String text) {
        if (this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }
        return this.text(text, (Label.LabelStyle)this.skin.get((Class)Label.LabelStyle.class));
    }
    
    public TypingDialog text(@Null final String text, final Label.LabelStyle labelStyle) {
        return this.text(this.newLabel(text, labelStyle));
    }
    
    public TypingDialog text(@Null final String text, final Font font) {
        return this.text(this.newLabel(text, font, Color.WHITE));
    }
    
    public TypingDialog text(@Null final String text, final Font font, final Color color) {
        return this.text(this.newLabel(text, font, color));
    }
    
    public TypingDialog text(final TextraLabel label) {
        this.contentTable.add((Actor)label);
        return this;
    }
    
    public TypingDialog typing(@Null final String text) {
        if (this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }
        return this.typing(text, (Label.LabelStyle)this.skin.get((Class)Label.LabelStyle.class));
    }
    
    public TypingDialog typing(@Null final String text, final Label.LabelStyle labelStyle) {
        return this.typing(this.newTypingLabel(text, labelStyle));
    }
    
    public TypingDialog typing(@Null final String text, final Font font) {
        return this.typing(this.newTypingLabel(text, font, Color.WHITE));
    }
    
    public TypingDialog typing(@Null final String text, final Font font, final Color color) {
        return this.typing(this.newTypingLabel(text, font, color));
    }
    
    public TypingDialog typing(final TypingLabel label) {
        this.contentTable.add((Actor)label);
        return this;
    }
    
    public TypingDialog button(@Null final String text) {
        return this.button(text, null);
    }
    
    public TypingDialog button(@Null final String text, @Null final Object object) {
        if (this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }
        return this.button(text, object, (TextButton.TextButtonStyle)this.skin.get((Class)TextButton.TextButtonStyle.class));
    }
    
    public TypingDialog button(@Null final String text, @Null final Object object, final TextButton.TextButtonStyle buttonStyle) {
        return this.button((this.font == null) ? new TextraButton(text, buttonStyle) : new TextraButton(text, buttonStyle, this.font), object);
    }
    
    public TypingDialog button(final Button button) {
        return this.button(button, null);
    }
    
    public TypingDialog button(final Button button, @Null final Object object) {
        this.buttonTable.add((Actor)button);
        this.setObject((Actor)button, object);
        return this;
    }
    
    public TypingDialog show(final Stage stage, @Null final Action action) {
        this.clearActions();
        this.removeCaptureListener((EventListener)this.ignoreTouchDown);
        this.previousKeyboardFocus = null;
        Actor actor = stage.getKeyboardFocus();
        if (actor != null && !actor.isDescendantOf((Actor)this)) {
            this.previousKeyboardFocus = actor;
        }
        this.previousScrollFocus = null;
        actor = stage.getScrollFocus();
        if (actor != null && !actor.isDescendantOf((Actor)this)) {
            this.previousScrollFocus = actor;
        }
        stage.addActor((Actor)this);
        this.pack();
        stage.cancelTouchFocus();
        stage.setKeyboardFocus((Actor)this);
        stage.setScrollFocus((Actor)this);
        if (action != null) {
            this.addAction(action);
        }
        return this;
    }
    
    public TypingDialog show(final Stage stage) {
        this.show(stage, (Action)Actions.sequence((Action)Actions.alpha(0.0f), (Action)Actions.fadeIn(0.4f, Interpolation.fade)));
        this.setPosition((float)Math.round((stage.getWidth() - this.getWidth()) / 2.0f), (float)Math.round((stage.getHeight() - this.getHeight()) / 2.0f));
        return this;
    }
    
    public void hide(@Null final Action action) {
        final Stage stage = this.getStage();
        if (stage != null) {
            this.removeListener((EventListener)this.focusListener);
            if (this.previousKeyboardFocus != null && this.previousKeyboardFocus.getStage() == null) {
                this.previousKeyboardFocus = null;
            }
            Actor actor = stage.getKeyboardFocus();
            if (actor == null || actor.isDescendantOf((Actor)this)) {
                stage.setKeyboardFocus(this.previousKeyboardFocus);
            }
            if (this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null) {
                this.previousScrollFocus = null;
            }
            actor = stage.getScrollFocus();
            if (actor == null || actor.isDescendantOf((Actor)this)) {
                stage.setScrollFocus(this.previousScrollFocus);
            }
        }
        if (action != null) {
            this.addCaptureListener((EventListener)this.ignoreTouchDown);
            this.addAction((Action)Actions.sequence(action, (Action)Actions.removeListener((EventListener)this.ignoreTouchDown, true), (Action)Actions.removeActor()));
        }
        else {
            this.remove();
        }
    }
    
    public void hide() {
        this.hide((Action)Actions.fadeOut(0.4f, Interpolation.fade));
    }
    
    public void setObject(final Actor actor, @Null final Object object) {
        this.values.put(actor, object);
    }
    
    public TypingDialog key(final int keycode, @Null final Object object) {
        this.addListener((EventListener)new InputListener() {
            public boolean keyDown(final InputEvent event, final int keycode2) {
                if (keycode == keycode2) {
                    Gdx.app.postRunnable((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            TypingDialog.this.result(object);
                            if (!TypingDialog.this.cancelHide) {
                                TypingDialog.this.hide();
                            }
                            TypingDialog.this.cancelHide = false;
                        }
                    });
                }
                return false;
            }
        });
        return this;
    }
    
    protected void result(@Null final Object object) {
    }
    
    public void cancel() {
        this.cancelHide = true;
    }
}
