package com.fos.game.engine.ecs.components.z_animations3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class Bone {

    public final int index;
    public final String name;

    public final Matrix4 tPoseLocalTransform;
    public final Matrix4 tPoseGlobalTransformInv; // <- constant but will be calculated after the Bone tree is set.
    public final Array<Bone> children;

    // <- This is the important bit - this value will be loaded into the GPU. ->
    public Matrix4 currentGlobalTransform ; // the current position, rotation and scale of the bone, in the models frame of reference.

    protected Bone(final int index, final String name, final Matrix4 tPoseLocalTransform) {
        this.index = index;
        this.name = name;
        this.tPoseLocalTransform = tPoseLocalTransform;
        this.tPoseGlobalTransformInv = new Matrix4().idt();
        // will be filled later
        this.children = new Array<>();
        this.currentGlobalTransform = new Matrix4().idt();
    }

    protected void calculateTPoseGlobalTransformInvForBoneTree(Matrix4 parentBindTransform) {
        Matrix4 bindTransform = new Matrix4(parentBindTransform).mul(tPoseLocalTransform);
        this.tPoseGlobalTransformInv.set(bindTransform).inv();
        for (Bone child : children) {
            child.calculateTPoseGlobalTransformInvForBoneTree(bindTransform);
        }
    }

    public void attachChild(Bone bone) {
        children.add(bone);
    }

}
