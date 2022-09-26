package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class FactoryAnimation {

    public static ComponentAnimations2D create(final float frameDuration, final TextureAtlas atlas, final String name, final Animation.PlayMode playMode) {
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(name);
        return new ComponentAnimations2D(frameDuration, regions, playMode);
    }

    public static ComponentAnimations2D create(final TextureAtlas atlas, final String name) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        regions.add(atlas.findRegion(name));
        return new ComponentAnimations2D(1, regions, Animation.PlayMode.NORMAL);
    }

}
