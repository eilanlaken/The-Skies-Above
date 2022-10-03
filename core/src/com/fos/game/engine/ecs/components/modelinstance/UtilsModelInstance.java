package com.fos.game.engine.ecs.components.modelinstance;

import com.badlogic.gdx.math.collision.BoundingBox;

public class UtilsModelInstance {

    private final static BoundingBox bounds = new BoundingBox();

    public static void computeBoundingRegions(ModelInstance modelInstance) {
        modelInstance.calculateBoundingBox(bounds);
        bounds.getCenter(modelInstance.center);
        bounds.getDimensions(modelInstance.dimensions);
        modelInstance.radius = modelInstance.dimensions.len() / 2f;
    }

}
