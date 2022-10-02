package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

@Deprecated
public class FactoryAnimation extends Factory {

    public FactoryAnimation(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentAnimations2D create(final AnimationData ...animationData) {
        return new ComponentAnimations2D(animationData);
    }

    // for simple single frame animations, which are really intended to be Sprites.
    public static ComponentAnimations2D create(final SpriteSheet spriteSheet, final String animationName) {
        AnimationData animationData = new AnimationData(spriteSheet, animationName, 1.0f, Animation.PlayMode.NORMAL);
        return new ComponentAnimations2D(animationData);
    }

    public ComponentAnimations2D create(final String json) {
        ComponentAnimations2D component = jsonConverter.gson.fromJson(json, ComponentAnimations2D.class);
        jsonConverter.gson.fromJson(json, ComponentAnimations2D.class);
        System.out.println("Animations data: " + component);

        System.out.println(component.animationsData[0].spriteSheet.filepath);
        System.out.println(component.elapsedTime);

        return null;
    }

    public ComponentAnimations2D create(final String filepath, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        AnimationData animationData = new AnimationData(this.assetManager.get(filepath, SpriteSheet.class), animationName, frameDuration, playMode);
        return new ComponentAnimations2D(animationData);
    }




}
