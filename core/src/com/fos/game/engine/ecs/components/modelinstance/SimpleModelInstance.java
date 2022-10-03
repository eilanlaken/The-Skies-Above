package com.fos.game.engine.ecs.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;

import java.util.HashMap;

public class SimpleModelInstance extends ModelInstance {

    protected SimpleModelInstance(final String modelFilePath, final String nodeId, final String[] atlasFilePaths, final Model model, final HashMap<String, Material> materialsMap, Matrix4 transform) {
        super(modelFilePath, nodeId, atlasFilePaths, model, materialsMap, transform);
    }

    @Deprecated
    protected SimpleModelInstance(final Model model, final HashMap<String, Material> materialsMap, Matrix4 transform, final String ...nodeIds) {
        super(model, materialsMap, transform, nodeIds);
    }

}
