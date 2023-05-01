package com.fos.game.engine.ecs.components.rendered2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Animations2DData {

    public float elapsedTime;
    public Color tint;
    public boolean active;
    public int pixelsPerUnit;
    public float size;
    public int currentlyPlaying;
    public Animation2DData[] animation2DData;

    public Animations2DData(float elapsedTime, Color tint, boolean active, int pixelsPerUnit, float size, int currentlyPlaying, Animation2DData... animation2DData) {
        this.animation2DData = animation2DData;
        this.elapsedTime = elapsedTime;
        this.tint = tint;
        this.active = active;
        this.pixelsPerUnit = pixelsPerUnit;
        this.size = size;
        this.currentlyPlaying = currentlyPlaying;
    }

    public static class Animation2DData {
        public String filepath;
        public String animationName;
        public Animation.PlayMode playMode;
        public float frameDuration;

        public Animation2DData(String filepath, String animationName, float frameDuration, Animation.PlayMode playMode) {
            this.filepath = filepath;
            this.animationName = animationName;
            this.playMode = playMode;
            this.frameDuration = frameDuration;
        }
    }

}
