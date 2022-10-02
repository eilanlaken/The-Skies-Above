package com.fos.game.engine.components.animations3d;

import java.util.HashMap;

public class WeightedPose {

    public HashMap<String, WeightedBoneTransform> boneWeightTransformMap;

    public WeightedPose(HashMap<String, WeightedBoneTransform> boneWeightTransformMap) {
        this.boneWeightTransformMap = boneWeightTransformMap;
    }

}
