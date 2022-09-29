package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactoryAnimation extends Factory {

    public FactoryAnimation(final GameAssetManager assetManager) {
        super(assetManager);
    }

    public ComponentAnimations2D create(final String atlasName, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        SpriteSheet spriteSheet = this.assetManager.get(atlasName, SpriteSheet.class);
        Array<TextureAtlas.AtlasRegion> regions = spriteSheet.findRegions(animationName);
        return new ComponentAnimations2D(spriteSheet, frameDuration, regions, playMode);
    }

    @Deprecated
    public static ComponentAnimations2D create(final float frameDuration, final SpriteSheet spriteSheet, final String name, final Animation.PlayMode playMode) {
        Array<TextureAtlas.AtlasRegion> regions = spriteSheet.findRegions(name);
        return new ComponentAnimations2D(spriteSheet, frameDuration, regions, playMode);
    }

    @Deprecated
    public static ComponentAnimations2D create(final SpriteSheet spriteSheet, final String name) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        regions.add(spriteSheet.findRegion(name));
        return new ComponentAnimations2D(spriteSheet,1, regions, Animation.PlayMode.NORMAL);
    }

    @Deprecated
    public static ComponentAnimations2D create(final GameAssetManager assetManager, final String atlasName, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        SpriteSheet spriteSheet = assetManager.get(atlasName, SpriteSheet.class);
        Array<TextureAtlas.AtlasRegion> regions = spriteSheet.findRegions(animationName);
        return new ComponentAnimations2D(spriteSheet, frameDuration, regions, playMode);
    }

}
