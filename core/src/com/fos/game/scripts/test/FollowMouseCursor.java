package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class FollowMouseCursor extends Script {

    ComponentTransform2D transform2D;

    public FollowMouseCursor(Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        transform2D = (ComponentTransform2D) entity.getComponent(ComponentType.TRANSFORM_2D);
    }

    @Override
    public void update(float delta) {

    }

}
