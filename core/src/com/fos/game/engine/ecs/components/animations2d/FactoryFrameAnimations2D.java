package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryFrameAnimations2D extends Factory {

    public FactoryFrameAnimations2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentAnimations2D create(Animations2DData data) {
        return new ComponentAnimations2D(assetManager, data);
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, float frameDuration, float size, int pixelsPerUnit) {
        Animations2DData.Animation2DData data = new Animations2DData.Animation2DData(filepath, animationName, frameDuration, Animation.PlayMode.NORMAL);
        Animations2DData animations2DData = new Animations2DData(0, new Color(1,1,1,1), true, pixelsPerUnit, size, 0, data);
        return new ComponentAnimations2D(assetManager, animations2DData);
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, Animation.PlayMode playMode, float frameDuration, float size, int pixelsPerUnit) {
        Animations2DData.Animation2DData data = new Animations2DData.Animation2DData(filepath, animationName, frameDuration, playMode);
        Animations2DData animations2DData = new Animations2DData(0, new Color(1,1,1,1), true, pixelsPerUnit, size, 0, data);
        return new ComponentAnimations2D(assetManager, animations2DData);
    }

    public ComponentAnimations2D createFromJson(final String json) {
        return null;
    }

}
