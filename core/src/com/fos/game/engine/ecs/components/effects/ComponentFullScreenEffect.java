package com.fos.game.engine.ecs.components.effects;

import com.fos.game.engine.core.graphics.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentFullScreenEffect implements Component {

    public PostProcessingEffect postProcessingEffect;
    public int priority;

    protected ComponentFullScreenEffect(PostProcessingEffect postProcessingEffect, int priority) {
        this.postProcessingEffect = postProcessingEffect;
        this.priority = priority;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
