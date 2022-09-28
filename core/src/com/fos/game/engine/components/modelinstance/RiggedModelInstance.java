package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.renderer.materials.base.Material;
import com.fos.game.engine.rig.Rig;
import com.fos.game.engine.rig.RigFactory;

import java.util.HashMap;

public class RiggedModelInstance extends ModelInstance {

    public Rig rig;

    public RiggedModelInstance(final Model model, final HashMap<String, Material> materialInstances, Matrix4 transform, final String riggedModelNodeId, final String rigHierarchyId) {
        super(model, materialInstances, transform, riggedModelNodeId, rigHierarchyId);
        this.rig = RigFactory.createRig(model, riggedModelNodeId, rigHierarchyId);
    }

}
