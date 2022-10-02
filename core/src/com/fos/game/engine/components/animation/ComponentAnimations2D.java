package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentAnimations2D extends Array<Animation<TextureAtlas.AtlasRegion>> implements Component {

    public AnimationData[] animationsData;
    public int currentAnimation = 0;
    public float elapsedTime = 0;

    protected ComponentAnimations2D(final AnimationData ...animationsData) {
        this.animationsData = animationsData;
        for (final AnimationData data : animationsData) {
            Array<TextureAtlas.AtlasRegion> keyFrames = data.spriteSheet.findRegions(data.animationName);
            Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(data.frameDuration, keyFrames, data.playMode);
            add(animation);
        }
    }

    public TextureAtlas.AtlasRegion getTextureRegion() {
        return get(currentAnimation).getKeyFrame(elapsedTime);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.ANIMATION;
    }
}
