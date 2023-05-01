package com.fos.game.engine.ecs.components.rendered2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

import java.util.HashMap;

public class ComponentAnimations2D implements Component {

    public Animations2DData data;
    public HashMap<String, Animation<TextureAtlas.AtlasRegion>> animations;
    public Animation<TextureAtlas.AtlasRegion> currentPlayingAnimation;
    public float size;
    public int pixelsPerUnit;
    public float elapsedTime;
    public Color tint;
    public boolean active = true;

    protected ComponentAnimations2D(GameAssetManager assetManager, Animations2DData data) {
        this.data = data;
        this.size = data.size;
        this.pixelsPerUnit = data.pixelsPerUnit;
        this.elapsedTime = data.elapsedTime;
        this.tint = data.tint;
        this.active = data.active;
        this.animations = new HashMap<>();
        for (Animations2DData.Animation2DData animation2DData : data.animation2DData) {
            SpriteSheet spriteSheet = assetManager.get(animation2DData.filepath, SpriteSheet.class);
            Array<TextureAtlas.AtlasRegion> keyFrames = spriteSheet.findRegions(animation2DData.animationName);
            Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(animation2DData.frameDuration, keyFrames, animation2DData.playMode);
            animations.put(animation2DData.animationName, animation);
        }
        currentPlayingAnimation = animations.get(data.animation2DData[data.currentlyPlaying].animationName);
    }

    // TODO: remove
    @Deprecated
    protected ComponentAnimations2D() {

    }

    public TextureAtlas.AtlasRegion getTextureRegion() {
        return currentPlayingAnimation.getKeyFrame(elapsedTime);
    }

    public void setAnimations(final String name) {
        this.currentPlayingAnimation = animations.get(name);
        this.elapsedTime = 0;
    }

    // TODO: change so that elapsedTime is bounded.
    public void advanceTime(final float delta) {
        this.elapsedTime += delta;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
