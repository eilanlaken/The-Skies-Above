package com.fos.game.scripts.test;

import com.fos.game.engine.components.animation.ComponentAnimations2D;
import com.fos.game.engine.components.scripts.Script;

public class UpdateTimeScript extends Script {

    ComponentAnimations2D animations;

    @Override
    public void start() {
        //animations = (ComponentAnimations2D) entity.components[ComponentType.ANIMATION.ordinal()];
    }

    @Override
    public void update(float delta) {
        animations.elapsedTime += delta;
    }
}
