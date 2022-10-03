package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryAnimation extends Factory {

    public FactoryAnimation(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentAnimations2D create(final AnimationData ...animationData) {
        return new ComponentAnimations2D(assetManager, animationData);
    }

    // for simple single frame animations2d, which are really intended to be Sprites.
    public ComponentAnimations2D create(final String filepath, final String animationName) {
        AnimationData animationData = new AnimationData(filepath, animationName, 1.0f, Animation.PlayMode.NORMAL);
        return new ComponentAnimations2D(assetManager, animationData);
    }

    public ComponentAnimations2D create(final String json) {

        return null;
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        AnimationData animationData = new AnimationData(filepath, animationName, frameDuration, playMode);
        return new ComponentAnimations2D(assetManager, animationData);
    }




}
