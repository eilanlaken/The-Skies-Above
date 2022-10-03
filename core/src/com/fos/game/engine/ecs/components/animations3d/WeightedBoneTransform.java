package com.fos.game.engine.ecs.components.animations3d;

public class WeightedBoneTransform {

    final float weight;
    final BoneTransform boneTransform;

    protected WeightedBoneTransform(final float weight, final BoneTransform boneTransform) {
        this.weight = weight;
        this.boneTransform = boneTransform;
    }

}
