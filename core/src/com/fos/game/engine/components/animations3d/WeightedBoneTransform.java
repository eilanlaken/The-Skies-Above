package com.fos.game.engine.components.animations3d;

public class WeightedBoneTransform {

    final float weight;
    final BoneTransform boneTransform;

    protected WeightedBoneTransform(final float weight, final BoneTransform boneTransform) {
        this.weight = weight;
        this.boneTransform = boneTransform;
    }

}
