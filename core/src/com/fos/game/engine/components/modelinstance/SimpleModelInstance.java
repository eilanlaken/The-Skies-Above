package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.renderer.materials.base.Material;

import java.util.HashMap;

public class SimpleModelInstance extends ModelInstance {

    protected SimpleModelInstance(final Model model, final HashMap<String, Material> materialInstances, Matrix4 transform, final String ...nodeIds) {
        super(model, materialInstances, transform, nodeIds);
    }

}
