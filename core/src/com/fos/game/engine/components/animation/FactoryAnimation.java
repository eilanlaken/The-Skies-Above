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
        TextureAtlas atlas = this.assetManager.get(atlasName, TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(animationName);
        return new ComponentAnimations2D(frameDuration, regions, playMode);
    }

    @Deprecated
    public static ComponentAnimations2D create(final float frameDuration, final TextureAtlas atlas, final String name, final Animation.PlayMode playMode) {
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(name);
        return new ComponentAnimations2D(frameDuration, regions, playMode);
    }

    @Deprecated
    public static ComponentAnimations2D create(final TextureAtlas atlas, final String name) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        regions.add(atlas.findRegion(name));
        return new ComponentAnimations2D(1, regions, Animation.PlayMode.NORMAL);
    }

    @Deprecated
    public static ComponentAnimations2D create(final GameAssetManager assetManager, final String atlasName, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        TextureAtlas atlas = assetManager.get(atlasName, TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(animationName);
        return new ComponentAnimations2D(frameDuration, regions, playMode);
    }

}
