package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentAnimations2D extends Array<Animation<TextureAtlas.AtlasRegion>> implements Component {

    public final SpriteSheet spriteSheet;
    public int currentAnimation = 0;
    public float elapsedTime = 0;

    protected ComponentAnimations2D(final SpriteSheet spriteSheet, float frameDuration, Array<TextureAtlas.AtlasRegion> keyFrames, Animation.PlayMode playMode) {
        this.spriteSheet = spriteSheet;
        addAll(new Animation<>(frameDuration, keyFrames, playMode));
    }

    protected ComponentAnimations2D(final SpriteSheet spriteSheet, final Animation<TextureAtlas.AtlasRegion> ...animations) {
        this.spriteSheet = spriteSheet;
        addAll(animations);
    }

    public TextureAtlas.AtlasRegion getTextureRegion() {
        return get(currentAnimation).getKeyFrame(elapsedTime);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.ANIMATION;
    }
}
