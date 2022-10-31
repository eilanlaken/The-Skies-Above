package com.fos.game.engine.ecs.components.animations2d;

import com.fos.game.engine.core.graphics.spine.AnimationState;
import com.fos.game.engine.core.graphics.spine.Skeleton;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentBoneAnimations2D implements Component {

    public Skeleton skeleton;
    public AnimationState state;

    protected ComponentBoneAnimations2D(Skeleton skeleton, AnimationState state) {
        this.skeleton = skeleton;
        this.state = state;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.ANIMATIONS_BONES_2D;
    }
}
