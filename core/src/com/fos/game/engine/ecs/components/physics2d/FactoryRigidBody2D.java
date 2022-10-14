package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryRigidBody2D extends Factory {

    public FactoryRigidBody2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentRigidBody2D create(final RigidBody2DData data) {
        return new ComponentRigidBody2D(data);
    }








    @Deprecated
    public static ComponentRigidBody2D create(final EntityContainer container, float x, float y, float w, float h) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2f/32f,h/2f/32f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100;

        ComponentRigidBody2D rigidBody2D = new ComponentRigidBody2D(container.getPhysics2D().createBody(bodyDef));
        rigidBody2D.body.setTransform(0, 0, 0);
        //rigidBody2D.body.setTransform(0, 0, 90 * MathUtils.degreesToRadians);
        shape.dispose();
        return rigidBody2D;
    }

    @Deprecated
    public static ComponentRigidBody2D create(final EntityContainer container) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        bodyDef.fixedRotation = true;

        CircleShape shape = new CircleShape();
        shape.setRadius(50f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100;
        fixtureDef.filter.categoryBits = 0;
        fixtureDef.filter.maskBits = 0;

        ComponentRigidBody2D rigidBody2D = new ComponentRigidBody2D(container.getPhysics2D().createBody(bodyDef));
        //rigidBody2D.body.setTransform(0, 0, 0);
        rigidBody2D.body.setTransform(0, 0, 90 * MathUtils.degreesToRadians);
        shape.dispose();
        return rigidBody2D;
    }

    @Deprecated
    public static ComponentRigidBody2D create2(final EntityContainer container, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        // to pos of cursor
        bodyDef.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f,0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100;
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = 0;
        fixtureDef.filter.maskBits = 0;

        ComponentRigidBody2D componentRigidBody2D = new ComponentRigidBody2D(container.getPhysics2D().createBody(bodyDef));
        componentRigidBody2D.body.setTransform(0, 0, 90 * MathUtils.degreesToRadians);
        shape.dispose();
        return componentRigidBody2D;
    }

    @Deprecated
    public static ComponentRigidBody2D create3(final EntityContainer container) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        Body body = container.getPhysics2D().createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1.59375f/2, 1.59375f/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.getPosition().set(0,0);
        body.setTransform(body.getPosition(), 0);
        return new ComponentRigidBody2D(body);
    }

    @Deprecated
    public static ComponentRigidBody2D create4(final EntityContainer container, final TextureAtlas.AtlasRegion region, final float ppm) {
        final float width = region.getRegionWidth();
        final float height = region.getRegionHeight();

        System.out.println(width);
        System.out.println(height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        Body body = container.getPhysics2D().createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / ppm, height / 2 / ppm);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.getPosition().set(0,0);
        body.setTransform(body.getPosition(), 0);
        return new ComponentRigidBody2D(body);
    }

    @Deprecated
    public static ComponentRigidBody2D create5(final EntityContainer container, final TextureAtlas.AtlasRegion region, final float ppm, final float scale) {
        final float width = region.getRegionWidth();
        final float height = region.getRegionHeight();

        System.out.println(width);
        System.out.println(height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        Body body = container.getPhysics2D().createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(scale * width / 2 / ppm, scale * height / 2 / ppm);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.getPosition().set(0,0);
        body.setTransform(body.getPosition(), 0);
        return new ComponentRigidBody2D(body);
    }

}
