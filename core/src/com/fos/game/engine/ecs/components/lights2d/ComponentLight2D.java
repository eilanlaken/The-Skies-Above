package com.fos.game.engine.ecs.components.lights2d;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentLight2D implements Component {

    public Light2DData light2DData;
    public RayHandler rayHandler; // <- will be set when inserted into the box2d world.
    // if this light is an ambient light, this field will be null.
    public Light box2DLight; // <- will be created when inserted into the box2d world.

    protected ComponentLight2D(final Light2DData light2DData) {
        this.light2DData = light2DData;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
