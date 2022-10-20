package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Animation2DData {

    public String filepath;
    public String animationName;
    public float frameDuration;
    public Animation.PlayMode playMode;

    public Animation2DData(final String filepath, final String animationName, final float frameDuration, final Animation.PlayMode playMode) {
        this.filepath = filepath;
        this.animationName = animationName;
        this.frameDuration = frameDuration;
        this.playMode = playMode;
    }

}
