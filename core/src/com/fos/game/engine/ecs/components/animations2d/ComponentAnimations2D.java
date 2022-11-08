package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.core.files.assets.GameAssetManager;

import java.util.HashMap;

public class ComponentAnimations2D implements Component {

    public FrameAnimations2DData data;
    public HashMap<String, Animation<TextureAtlas.AtlasRegion>> animations;
    public Animation<TextureAtlas.AtlasRegion> currentPlayingAnimation;
    public float size;
    public int pixelsPerUnit;
    public float elapsedTime;
    public Color tint;
    public boolean active = true;

    protected ComponentAnimations2D(GameAssetManager assetManager, FrameAnimations2DData data) {
        this.data = data;
        this.size = data.size;
        this.pixelsPerUnit = data.pixelsPerUnit;
        this.elapsedTime = data.elapsedTime;
        this.tint = data.tint;
        this.active = data.active;
        this.animations = new HashMap<>();
        for (FrameAnimations2DData.AnimationData animationData : data.animationData) {
            SpriteSheet spriteSheet = assetManager.get(animationData.filepath, SpriteSheet.class);
            Array<TextureAtlas.AtlasRegion> keyFrames = spriteSheet.findRegions(animationData.animationName);
            Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(animationData.frameDuration, keyFrames, animationData.playMode);
            animations.put(animationData.animationName, animation);
        }
        currentPlayingAnimation = animations.get(data.animationData[data.currentlyPlaying].animationName);
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
