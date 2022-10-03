package com.fos.game.engine.ecs.components.animations3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class Rig {

    public static final int MaxNumberOfBones = 36;

    protected Bone[] bones;
    protected Bone root;
    public final int rigSize;
    private final Matrix4[] boneTransforms;
    public final HashMap<String, RigAnimation> namedAnimations;

    public Rig(final Bone root, final Bone[] bones, final HashMap<String, RigAnimation> namedAnimations) {
        this.root = root;
        this.bones = bones;
        this.rigSize = bones.length;
        this.boneTransforms = new Matrix4[rigSize];
        for (int i = 0; i < boneTransforms.length; i++) {
            this.boneTransforms[i] = new Matrix4().idt();
        }
        this.namedAnimations = namedAnimations;
    }

    public Matrix4[] getUpdatedBoneTransforms() {
        for (int i = 0; i < boneTransforms.length; i++) {
            boneTransforms[i].set(bones[i].currentGlobalTransform);
        }
        return boneTransforms;
    }

    public void applyPose(final Pose pose) {
        applyPose(pose, root, new Matrix4().idt());
    }

    private void applyPose(final Pose pose, final Bone bone, final Matrix4 parentGlobalTransform) {
        Matrix4 currentLocalTransform = computeCurrentLocalTransform(pose, bone);
        Matrix4 currentGlobalTransform = new Matrix4(parentGlobalTransform).mul(currentLocalTransform);
        for (Bone childBone : bone.children) {
            applyPose(pose, childBone, currentGlobalTransform);
        }
        currentGlobalTransform.mul(bone.tPoseGlobalTransformInv);
        bone.currentGlobalTransform.set(currentGlobalTransform);
    }

    private Matrix4 computeCurrentLocalTransform(final Pose pose, final Bone bone) {
        BoneTransform boneTransform = pose.boneTransformsMap.get(bone.name);
        Matrix4 currentLocalTransform = new Matrix4(bone.tPoseLocalTransform);
        if (boneTransform != null) {
            Vector3 translation = boneTransform.position;
            Quaternion rotation = boneTransform.rotation;
            currentLocalTransform.translate(translation);
            currentLocalTransform.rotate(rotation);
        }
        return currentLocalTransform;
    }

}
