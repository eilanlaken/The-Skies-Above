package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.renderer.materials.base.Material;

import java.util.HashMap;

public class ModelInstance extends com.badlogic.gdx.graphics.g3d.ModelInstance {

    public HashMap<String, Material> materialInstances;
    public final Vector3 center = new Vector3();
    public final Vector3 dimensions = new Vector3();
    public float radius;

    protected ModelInstance(final HashMap<String, String> assetsPathsMap, final Model model, final HashMap<String, Material> materialInstances,
                            Matrix4 transform) {
        super(model, transform, assetsPathsMap.get("nodeIds"));
    }

    @Deprecated
    protected ModelInstance(final Model model, final HashMap<String, Material> materialInstances, Matrix4 transform, final String ...nodeIds) {
        super(model, transform, nodeIds);
        this.materialInstances = materialInstances;
        super.userData = this;
        UtilsModelInstance.computeBoundingRegions(this);
    }

    public Material getMaterial(final Renderable renderable) {
        final String gdxMaterialId = renderable.material.id;
        return materialInstances.get(gdxMaterialId);
    }

}
