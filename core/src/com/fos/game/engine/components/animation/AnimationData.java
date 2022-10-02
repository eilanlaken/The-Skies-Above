package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationData {

    public final SpriteSheet spriteSheet;
    public final String animationName;
    public final float frameDuration;
    public final Animation.PlayMode playMode;

    public AnimationData(final SpriteSheet spriteSheet, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        this.spriteSheet = spriteSheet;
        this.animationName = animationName;
        this.frameDuration = frameDuration;
        this.playMode = playMode;
    }

}
