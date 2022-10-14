package com.fos.game.engine.ecs.components.modelinstance_old;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class UtilsModel {

    private static boolean enableInstancedRendering(final Mesh mesh, final int instanceCount) {
        if (mesh.isInstanced()) return false;
        mesh.enableInstancedRendering(true, instanceCount,
                new VertexAttribute(VertexAttributes.Usage.Generic, 3, "i_transform_col_0"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 3, "i_transform_col_1"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 3, "i_transform_col_2"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 3, "i_transform_col_3"));
        return true;
    }

    private static void setStaticInstancedData(final Mesh mesh, final int floatsCount, final Array<Node> nodes) {
        FloatBuffer offsets = BufferUtils.newFloatBuffer(floatsCount);
        for (Node node : nodes) {
            Vector3 translation = node.translation;
            Quaternion rotation = node.rotation;
            Vector3 scale = node.scale;
            Matrix4 transform = new Matrix4().idt();
            transform.scale(scale.x, scale.y, scale.z);
            transform.translate(translation);
            transform.rotate(rotation);
            final float[] values = new float[] {
                    transform.val[Matrix4.M00], transform.val[Matrix4.M10], transform.val[Matrix4.M20],
                    transform.val[Matrix4.M01], transform.val[Matrix4.M11], transform.val[Matrix4.M21],
                    transform.val[Matrix4.M02], transform.val[Matrix4.M12], transform.val[Matrix4.M22],
                    transform.val[Matrix4.M03], transform.val[Matrix4.M13], transform.val[Matrix4.M23],
            };
            offsets.put(values);
        }
        ((Buffer)offsets).position(0);
        mesh.setInstanceData(offsets);
    }

    public static void prepareForStaticBundleInstancing(final Mesh mesh, final int instanceCount, final Array<Node> nodes) {
        if (enableInstancedRendering(mesh, instanceCount)) setStaticInstancedData(mesh, instanceCount * 12, nodes);
    }

}
