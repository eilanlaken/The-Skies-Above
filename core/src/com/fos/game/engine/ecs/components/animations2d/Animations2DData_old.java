package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;

@Deprecated
public class Animations2DData_old {

    public String filepath;
    public String animationName;
    public float frameDuration;
    public Animation.PlayMode playMode;
    public Color tint;

    public Animations2DData_old(final String filepath, final String animationName, final float frameDuration) {
        this(filepath, animationName, new Color(1,1,1,1), frameDuration, Animation.PlayMode.NORMAL);
    }

    public Animations2DData_old(final String filepath, final String animationName, final Color tint, final float frameDuration, final Animation.PlayMode playMode) {
        this.filepath = filepath;
        this.animationName = animationName;
        this.frameDuration = frameDuration;
        this.playMode = playMode;
        this.tint = tint;
    }

    public static class Animation2DData {
        public String filepath;
        public String animationName;
        public Animation.PlayMode playMode;
    }

}
