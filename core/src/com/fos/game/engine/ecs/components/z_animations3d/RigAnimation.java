package com.fos.game.engine.ecs.components.z_animations3d;

import com.badlogic.gdx.utils.Array;

public class RigAnimation {

    public final Array<RigKeyFrame> keyFrames;
    public com.badlogic.gdx.graphics.g2d.Animation.PlayMode playMode;

    public RigAnimation(com.badlogic.gdx.graphics.g2d.Animation.PlayMode playMode, final Array<RigKeyFrame> keyFrames) {
        this.keyFrames = keyFrames;
        this.playMode = playMode;
    }

    public float getAnimationDuration() {
        return keyFrames.get(keyFrames.size - 1).timeStamp;
    }

    public void scaleDuration(float stretchFactor) {
        for (RigKeyFrame rigKeyFrame : keyFrames) {
            rigKeyFrame.timeStamp *= stretchFactor;
        }
    }

    public RigAnimation(final Array<RigKeyFrame> keyFrames) {
        this(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP, keyFrames);
    }

    public static class RigKeyFrame {
        public float timeStamp;
        public final WeightedPose pose;

        public RigKeyFrame(final float timeStamp, final WeightedPose pose) {
            this.timeStamp = timeStamp;
            this.pose = pose;
        }
    }

}
