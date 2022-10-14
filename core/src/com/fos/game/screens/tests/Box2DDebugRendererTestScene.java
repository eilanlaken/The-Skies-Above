package com.fos.game.screens.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.cameras.ComponentCamera2D;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.physics2d.UtilsRigidBody2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.systems.renderer.base.Physics2DDebugRenderer;
import com.fos.game.engine.ecs.systems.renderer.base.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Box2DDebugRendererTestScene extends Scene {

    private World world;
    Array<EntityMini> entities;
    private ComponentCamera2D camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();


    class EntityMini {
        ComponentTransform2D transform2D;
        Animation<TextureAtlas.AtlasRegion> animation;
        Body body;
        Joint joint;
    }

    public Box2DDebugRendererTestScene(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,-10), true);
        world.setContactListener(getContactListener());
        entities = new Array<>();
        camera = context.factoryCamera2D.createCamera2D(30, 30 * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

        // create entities with bodies
        for (int i = 0; i < 10; i++) {
            EntityMini entityMini = new EntityMini();
            entityMini.transform2D = context.factoryTransform2D.
                    create(MathUtils.random(-10, 10), MathUtils.random(-1, 4), 1, MathUtils.random(0, 2 * (float)Math.PI), 1, 1);
            entityMini.animation = new Animation<>(1,
                    context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions(getRandomRegion()));
            entityMini.body = createBody(world, new RigidBody2DData(
                    BodyDef.BodyType.DynamicBody,
                    RigidBody2DData.Shape.RECTANGLE,
                    UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionWidth(), camera.pixelsPerMeterX),
                    UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionHeight(), camera.pixelsPerMeterY),
                    1,1,0.2f,false),
                    entityMini.transform2D);
            entityMini.body.setUserData(entityMini);
            entityMini.transform2D.transform = entityMini.body.getTransform();
            entities.add(entityMini);
        }

        // create entities with joints
        for (int i = 0; i < 3; i++) {
            EntityMini entityMini = new EntityMini();
            WeldJointDef def = new WeldJointDef();
            def.initialize(entities.get(i).body, entities.get(i + 2).body, new Vector2(1,1));
            entityMini.joint = world.createJoint(def);
            entities.add(entityMini);
        }

        // create floor
        EntityMini floor = new EntityMini();
        floor.transform2D = context.factoryTransform2D.create(0, -4.5f, 1, 0, 1, 1);
        floor.body = createBody(world, new RigidBody2DData(
                BodyDef.BodyType.StaticBody,
                RigidBody2DData.Shape.RECTANGLE,
                18, 0.5f,
                1,1,0.2f,false),
                floor.transform2D
        );
        floor.body.setUserData(floor);
        entities.add(floor);

    }

    @Override
    protected void update(float delta) {
        world.step(delta, 6, 2);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.lens.combined);
        for (EntityMini entityMini : entities) {
            if (entityMini.animation == null) continue;
            entityMini.transform2D.transform.setPosition(entityMini.body.getPosition());
            entityMini.transform2D.transform.setOrientation(entityMini.body.getTransform().getOrientation());
            spriteBatch.draw2(entityMini.animation.getKeyFrame(0), entityMini.transform2D, camera.pixelsPerMeterX, camera.pixelsPerMeterY);
        }
        spriteBatch.end();

        physics2DDebugRenderer.begin();
        physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
        for (EntityMini entityMini : entities) {
            Body body = entityMini.body;
            Joint joint = entityMini.joint;
            if (body != null) physics2DDebugRenderer.drawBody(entityMini.body);
            if (joint != null) physics2DDebugRenderer.drawJoint(entityMini.joint);
        }
        physics2DDebugRenderer.end();

        OrthographicCamera orthographicCamera = (OrthographicCamera) camera.lens;
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            orthographicCamera.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            orthographicCamera.zoom -= 0.1f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            EntityMini entityMini = new EntityMini();
            entityMini.transform2D = context.factoryTransform2D.
                    create(MathUtils.random(-10, 10), MathUtils.random(-1, 4), 1, MathUtils.random(0, 2 * (float)Math.PI), 1, 1);
            entityMini.animation = new Animation<>(1,
                    context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions(getRandomRegion()));
            entityMini.body = createBody(world, new RigidBody2DData(
                            BodyDef.BodyType.DynamicBody,
                            RigidBody2DData.Shape.RECTANGLE,
                            UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionWidth(), camera.pixelsPerMeterX),
                            UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionHeight(), camera.pixelsPerMeterY),
                            1,1,0.2f,false),
                    entityMini.transform2D);
            entityMini.body.setUserData(entityMini);
            entityMini.transform2D.transform = entityMini.body.getTransform();
            entities.add(entityMini);
        }

        camera.lens.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.lens.viewportWidth = camera.viewWorldWidth;
        camera.lens.viewportHeight = camera.viewWorldWidth * (float) height / width;
        camera.lens.update();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private String getRandomRegion() {
        int rand = MathUtils.random(0, 5);
        if (rand == 0) return "blue";
        if (rand == 1) return "green";
        if (rand == 2) return "orange";
        if (rand == 3) return "purple";
        if (rand == 4) return "red";
        return "blue";
    }


    private Body createBody(World world, RigidBody2DData data, ComponentTransform2D transform2D) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        bodyDef.position.set(transform2D.getPosition().x, transform2D.getPosition().y);
        bodyDef.angle = transform2D.transform.getRotation();
        Body body = world.createBody(bodyDef);
        bodyDef.fixedRotation = false;
        Shape shape = getShape(data);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = data.density;
        fixtureDef.isSensor = data.isSensor;
        fixtureDef.friction = data.friction;
        fixtureDef.restitution = data.restitution;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    private Shape getShape(final RigidBody2DData data) {
        if (data.shape == RigidBody2DData.Shape.CIRCLE) {
            CircleShape shape = new CircleShape();
            shape.setRadius((data.width + data.height) * 0.5f); // average of width and height
            return shape;
        }
        if (data.shape == RigidBody2DData.Shape.RECTANGLE) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(data.width / 2, data.height / 2);
            return shape;
        }
        return null;
    }

    private ContactListener getContactListener() {
        ContactListener contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                EntityMini entityMiniA = (EntityMini) contact.getFixtureA().getBody().getUserData();
                EntityMini entityMiniB = (EntityMini) contact.getFixtureB().getBody().getUserData();
                //System.out.println("beging contact: " + entityMiniA  + " : " + entityMiniB);
            }

            @Override
            public void endContact(Contact contact) {
                EntityMini entityMiniA = (EntityMini) contact.getFixtureA().getBody().getUserData();
                EntityMini entityMiniB = (EntityMini) contact.getFixtureB().getBody().getUserData();
                //System.out.println("end contact: " + entityMiniA  + " : " + entityMiniB);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
        return contactListener;
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
