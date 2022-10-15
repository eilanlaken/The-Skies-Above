package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.core.files.assets.GameAssetManager;

import java.util.HashMap;

public class ComponentAnimations2D implements Component {

    public Animation2DData[] animationsData;
    public HashMap<String, Animation<TextureAtlas.AtlasRegion>> animations;
    public Animation<TextureAtlas.AtlasRegion> currentPlayingAnimation;
    public float elapsedTime;
    public boolean active = true;

    protected ComponentAnimations2D(final GameAssetManager assetManager, final Animation2DData...animationsData) {
        this.animationsData = animationsData;
        animations = new HashMap<>();
        this.elapsedTime = 0;
        for (final Animation2DData data : animationsData) {
            SpriteSheet spriteSheet = assetManager.get(data.filepath, SpriteSheet.class);
            Array<TextureAtlas.AtlasRegion> keyFrames = spriteSheet.findRegions(data.animationName);
            Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(data.frameDuration, keyFrames, data.playMode);
            animations.put(data.animationName, animation);
        }
        currentPlayingAnimation = animations.get(animationsData[0].animationName);
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
        return ComponentType.ANIMATIONS_2D;
    }
}
