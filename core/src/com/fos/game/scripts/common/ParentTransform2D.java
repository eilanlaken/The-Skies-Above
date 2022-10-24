package com.fos.game.scripts.common;

import com.badlogic.gdx.math.Vector2;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.components.transform2d.FactoryTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class ParentTransform2D extends Script {

    private final Vector2 vector2 = new Vector2();

    private transient ComponentTransform2D transform2D;
    private transient ComponentTransform2D initialTransform2D;
    private transient ComponentTransform2D parentTransform2D;

    public ParentTransform2D(Entity entity) {
        super(entity);
        this.parentTransform2D =  (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
    }

    @Override
    public void start() {
        this.transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        this.initialTransform2D = FactoryTransform2D.copy(this.transform2D);
    }

    @Override
    public void update(float delta) {
        vector2.set(initialTransform2D.getPosition());
        vector2.rotateRad(parentTransform2D.getRotation());
        vector2.add(parentTransform2D.getPosition());
        transform2D.transform.setPosition(vector2);
        transform2D.transform.setRotation(initialTransform2D.getRotation() + parentTransform2D.getRotation());
    }
}
