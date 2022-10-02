package com.fos.game.engine.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationData {

    public final String filepath;
    public final String animationName;
    public float frameDuration;
    public Animation.PlayMode playMode;

    public AnimationData(final String filepath, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        this.filepath = filepath;
        this.animationName = animationName;
        this.frameDuration = frameDuration;
        this.playMode = playMode;
    }

}
