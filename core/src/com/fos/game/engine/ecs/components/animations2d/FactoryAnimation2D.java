package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryAnimation2D extends Factory {

    public FactoryAnimation2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentAnimations2D create(final Animation2DData... animation2DData) {
        return new ComponentAnimations2D(assetManager, animation2DData);
    }

    // for simple single frame animations2d, which are really intended to be Sprites.
    public ComponentAnimations2D create(final String filepath, final String animationName) {
        Animation2DData animation2DData = new Animation2DData(filepath, animationName, 1.0f, Animation.PlayMode.NORMAL);
        return new ComponentAnimations2D(assetManager, animation2DData);
    }

    public ComponentAnimations2D createFromJson(final String json) {

        return null;
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        Animation2DData animation2DData = new Animation2DData(filepath, animationName, frameDuration, playMode);
        return new ComponentAnimations2D(assetManager, animation2DData);
    }




}
