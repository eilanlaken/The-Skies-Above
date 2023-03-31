package com.fos.game.engine.ecs.systems.dynamics2D;

import com.badlogic.gdx.physics.box2d.*;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.Body2DData;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class Dynamics2DUtils {

    protected static final int PHYSICS_2D_BIT_MASK = ComponentType.PHYSICS_2D.bitMask;

    protected static void addBody2D(final World world, final Entity entity, final ComponentBody2D componentBody2D,
                                    final ComponentTransform2D transform) {
        Body2DData data = componentBody2D.data;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        bodyDef.position.set(transform.x, transform.y);
        bodyDef.angle = transform.angle;
        componentBody2D.body = world.createBody(bodyDef);
        componentBody2D.body.setUserData(entity);
        Shape shape = getShape(data);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = data.filter.categoryBits;
        fixtureDef.filter.maskBits = data.filter.maskBits;
        fixtureDef.filter.groupIndex = data.filter.groupIndex;
        fixtureDef.density = data.density;
        fixtureDef.isSensor = data.isSensor;
        fixtureDef.friction = data.friction;
        fixtureDef.restitution = data.restitution;
        componentBody2D.body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected static void destroyBody2D(final World world, final ComponentBody2D componentBody2D) {
        world.destroyBody(componentBody2D.body);
    }

    private static Shape getShape(final Body2DData data) {
        if (data.shape == Body2DData.Shape.CIRCLE) {
            CircleShape shape = new CircleShape();
            shape.setRadius((data.width + data.height) * 0.5f); // average of width and height
            return shape;
        }
        if (data.shape == Body2DData.Shape.RECTANGLE) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(data.width / 2, data.height / 2);
            return shape;
        }
        return null;
    }

}
