package com.fos.game.engine.components.animations3d;

import com.badlogic.gdx.math.Matrix4;

public class BoneTPoseData {

    public final int id;
    public final Matrix4 localTransform;
    public final Matrix4 globalTransform;

    public BoneTPoseData(final int id, final Matrix4 localTransform, final Matrix4 globalTransform) {
        this.id = id;
        this.localTransform = localTransform;
        this.globalTransform = globalTransform;
    }

    @Override
    public String toString() {
        return "BoneTPoseData{" +
                "id=" + id +
                '}';
    }
}
