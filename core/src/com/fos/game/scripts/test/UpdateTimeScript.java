package com.fos.game.scripts.test;

import com.fos.game.engine.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.components.scripts.Script;

public class UpdateTimeScript extends Script {

    ComponentAnimations2D animations;

    @Override
    public void start() {
        //animations2d = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
    }

    @Override
    public void update(float delta) {
        animations.elapsedTime += delta;
    }
}
