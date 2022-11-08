package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryFrameAnimations2D extends Factory {

    public FactoryFrameAnimations2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentAnimations2D create(FrameAnimations2DData data) {
        return new ComponentAnimations2D(assetManager, data);
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, float frameDuration, float size, int pixelsPerUnit) {
        FrameAnimations2DData.AnimationData data = new FrameAnimations2DData.AnimationData(filepath, animationName, frameDuration, Animation.PlayMode.NORMAL);
        FrameAnimations2DData frameAnimations2DData = new FrameAnimations2DData(0, new Color(1,1,1,1), true, pixelsPerUnit, size, 0, data);
        return new ComponentAnimations2D(assetManager, frameAnimations2DData);
    }

    public ComponentAnimations2D createFromJson(final String json) {
        return null;
    }

    // TODO: add size and pixelsPerUnit to Animation2DData
    @Deprecated
    public ComponentAnimations2D create(final Animations2DData_old... animations2DDatumOlds) {
        return new ComponentAnimations2D();
    }

    // for simple single frame animations2d, which are really intended to be Sprites.
    @Deprecated
    public ComponentAnimations2D create(final String filepath, final String animationName, float size, int pixelsPerUnit) {
        Animations2DData_old animations2DDataOld = new Animations2DData_old(filepath, animationName, 1.0f);
        return new ComponentAnimations2D();
    }

    @Deprecated
    public ComponentAnimations2D create(final String filepath, final String animationName) {
        Animations2DData_old animations2DDataOld = new Animations2DData_old(filepath, animationName, 1.0f);
        return new ComponentAnimations2D();
    }

    @Deprecated
    public ComponentAnimations2D create(final String filepath, final String animationName, final Color tint, final float frameDuration, final Animation.PlayMode playMode) {
        Animations2DData_old animations2DDataOld = new Animations2DData_old(filepath, animationName, tint, frameDuration, playMode);
        return new ComponentAnimations2D();
    }




}
