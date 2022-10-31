package com.fos.game.engine.ecs.components.animations2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;

public class FrameAnimations2DData {

    public float elapsedTime;
    public Color tint;
    public boolean active;
    public int pixelsPerUnit;
    public float size;
    public int currentlyPlaying;
    public AnimationData[] animationData;

    public FrameAnimations2DData(float elapsedTime, Color tint, boolean active, int pixelsPerUnit, float size, int currentlyPlaying, AnimationData... animationData) {
        this.animationData = animationData;
        this.elapsedTime = elapsedTime;
        this.tint = tint;
        this.active = active;
        this.pixelsPerUnit = pixelsPerUnit;
        this.size = size;
        this.currentlyPlaying = currentlyPlaying;
    }

    public static class AnimationData {
        public String filepath;
        public String animationName;
        public Animation.PlayMode playMode;
        public float frameDuration;

        public AnimationData(String filepath, String animationName, float frameDuration, Animation.PlayMode playMode) {
            this.filepath = filepath;
            this.animationName = animationName;
            this.playMode = playMode;
            this.frameDuration = frameDuration;
        }
    }

}
