package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.files.assets.GameAssetManager;

import java.util.HashMap;

public class ComponentAnimations2D extends HashMap<String, Animation<TextureAtlas.AtlasRegion>> implements Component {

    public Animation2DData[] animationsData;
    public Animation<TextureAtlas.AtlasRegion> currentPlayingAnimation;
    public float elapsedTime;

    protected ComponentAnimations2D(final GameAssetManager assetManager, final Animation2DData...animationsData) {
        this.animationsData = animationsData;
        this.elapsedTime = 0;
        for (final Animation2DData data : animationsData) {
            SpriteSheet spriteSheet = assetManager.get(data.filepath, SpriteSheet.class);
            Array<TextureAtlas.AtlasRegion> keyFrames = spriteSheet.findRegions(data.animationName);
            Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(data.frameDuration, keyFrames, data.playMode);
            put(data.animationName, animation);
        }
        currentPlayingAnimation = get(animationsData[0].animationName);
    }

    public TextureAtlas.AtlasRegion getTextureRegion() {
        return currentPlayingAnimation.getKeyFrame(elapsedTime);
    }

    public void setAnimations(final String name) {
        this.currentPlayingAnimation = get(name);
        this.elapsedTime = 0;
    }

    public void advanceTime(final float delta) {
        this.elapsedTime += delta;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.ANIMATIONS_2D;
    }
}
