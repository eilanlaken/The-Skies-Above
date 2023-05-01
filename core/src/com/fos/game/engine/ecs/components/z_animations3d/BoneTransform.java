package com.fos.game.engine.ecs.components.z_animations3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class BoneTransform {

    public Vector3 position;
    public Quaternion rotation;
    public Vector3 scaling;

    public Matrix4 computedTransform = new Matrix4().idt();

    public BoneTransform(final Vector3 position, final Quaternion rotation) {
        if (position == null) this.position = new Vector3(0,0,0);
        else this.position = position;
        if (rotation == null) this.rotation = new Quaternion().idt();
        else this.rotation = rotation;
        this.scaling = new Vector3(1,1,1);
        update();
    }

    public BoneTransform(final Vector3 position, final Quaternion rotation, final Vector3 scaling) {
        if (position == null) this.position = new Vector3(0,0,0);
        else this.position = position;
        if (rotation == null) this.rotation = new Quaternion().idt();
        else this.rotation = rotation;
        if (scaling == null) this.scaling = new Vector3(1,1,1);
        else this.scaling = scaling;
        update();
    }

    public void update() {
        computedTransform.scale(scaling.x, scaling.y, scaling.z);
        computedTransform.rotate(rotation);
        computedTransform.translate(position);
    }

    public void invert() {
        position.scl(-1);
        rotation.conjugate();
        update();
    }

}
