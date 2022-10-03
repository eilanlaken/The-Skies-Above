package com.fos.game.engine.ecs.components.animations3d;

import java.util.HashMap;
import java.util.Map;

public class Pose {

    public static final Pose EMPTY_POSE = new Pose(new HashMap<String, BoneTransform>());

    protected Map<String, BoneTransform> boneTransformsMap;

    public Pose(final Map<String, BoneTransform> boneTransforms) {
        this.boneTransformsMap = boneTransforms;
    }

}
