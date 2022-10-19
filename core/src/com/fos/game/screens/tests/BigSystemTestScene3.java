package com.fos.game.screens.tests;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.cameras.ComponentCamera2D;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.physics2d.UtilsRigidBody2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.systems.renderer_old.base.Physics2DDebugRenderer;
import com.fos.game.engine.ecs.systems.renderer_old.base.SpriteBatch;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BigSystemTestScene3 extends Scene_old {

    private World world;
    Array<EntityMini> entities;
    private ComponentCamera2D camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();
    RayHandler rayHandler;
    PointLight pointLight1, pointLight2, pointLight3;

    class EntityMini {
        ComponentTransform2D transform2D;
        Animation<TextureAtlas.AtlasRegion> animation;
        Body body;
        Joint joint;
    }

    EntityMini mouse;

    public BigSystemTestScene3(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,-10), true);
        world.setContactListener(getContactListener());
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.2f);
        pointLight1 = new PointLight(rayHandler, 100, Color.BLUE, 5, 0,0);
        pointLight2 = new PointLight(rayHandler, 100, Color.RED, 5, 2,2);
        pointLight3 = new PointLight(rayHandler, 100, Color.RED, 5, 2,2);
        //coneLight = new ConeLight(rayHandler1, 200, Color.RED, 5, 0, 0, 0, 15);
        //directionalLight = new DirectionalLight(rayHandler, 100, Color.LIME, 30);

        entities = new Array<>();
        camera = context.factoryCamera2D.createCamera2D(30, 30 * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

        for (int i = 0; i < 10; i++) {
            EntityMini entityMini = new EntityMini();
            entityMini.transform2D = context.factoryTransform2D.
                    create(MathUtils.random(-10, 10), MathUtils.random(-1, 4), 1, MathUtils.random(0, 2 * (float)Math.PI), 1, 1);
            entityMini.animation = new Animation<>(1,
                    context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions(getRandomRegion()));
            Filter filter = new Filter();
            filter.categoryBits = 0x0001;
            filter.maskBits = 0x0011;
            entityMini.body = createBody(world, new RigidBody2DData(
                    BodyDef.BodyType.DynamicBody,
                    RigidBody2DData.Shape.RECTANGLE,
                    UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionWidth(), camera.pixelsPerMeterX),
                    UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionHeight(), camera.pixelsPerMeterY),
                    filter,
                    1,1,0.2f,false),
                    entityMini.transform2D);
            entityMini.body.setUserData(entityMini);
            entityMini.transform2D.transform = entityMini.body.getTransform();
            entities.add(entityMini);
        }

        // create entities with joints
        /*
        for (int i = 0; i < 3; i++) {
            EntityMini entityMini = new EntityMini();
            WeldJointDef def = new WeldJointDef();
            def.initialize(entities.get(i).body, entities.get(i + 2).body, new Vector2(1,1));
            entityMini.joint = world.createJoint(def);
            entities.add(entityMini);
        }
        */

        // create mouse follow
        mouse = new EntityMini();
        mouse.transform2D = context.factoryTransform2D.create(0, 0f, 1, 0, 1, 1);
        mouse.animation = new Animation<>(1,
                context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions("purple"));
        mouse.body = createBody(world, new RigidBody2DData(
                        BodyDef.BodyType.KinematicBody,
                        RigidBody2DData.Shape.RECTANGLE,
                        UtilsRigidBody2D.getBox2DLength(mouse.animation.getKeyFrame(0).getRegionWidth(), camera.pixelsPerMeterX),
                        UtilsRigidBody2D.getBox2DLength(mouse.animation.getKeyFrame(0).getRegionHeight(), camera.pixelsPerMeterY),
                        new Filter(),
                        1,1,0.2f,false),
                        mouse.transform2D
        );
        entities.add(mouse);

        // create floor
        EntityMini floor = new EntityMini();
        floor.transform2D = context.factoryTransform2D.create(0, -4.5f, 1, 0, 1, 1);
        Filter filter = new Filter();
        filter.categoryBits = 0x0010;
        filter.maskBits = 0x0101;
        floor.body = createBody(world, new RigidBody2DData(
                BodyDef.BodyType.StaticBody,
                RigidBody2DData.Shape.RECTANGLE,
                18, 0.5f, filter,
                1,1,0.2f,false),
                floor.transform2D
        );
        floor.body.setUserData(floor);
        entities.add(floor);

    }

    @Override
    protected void update(float delta) {
        world.step(delta, 6, 2);
        entities.sort(new Comparator<EntityMini>() {
            @Override
            public int compare(EntityMini o1, EntityMini o2) {
                final float z1 = o1.transform2D.z;
                final float z2 = o2.transform2D.z;
                return Float.compare(z1, z2);
            }
        });
        rayHandler.update();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.lens.update();
        rayHandler.setCombinedMatrix(camera.lens);
        spriteBatch.setProjectionMatrix(camera.lens.combined);
        spriteBatch.begin();
        for (EntityMini entityMini : entities) {
            if (entityMini.animation == null) continue;
            entityMini.transform2D.transform.setPosition(entityMini.body.getPosition());
            entityMini.transform2D.transform.setOrientation(entityMini.body.getTransform().getOrientation());
            spriteBatch.draw2(entityMini.animation.getKeyFrame(0), entityMini.transform2D, camera.pixelsPerMeterX, camera.pixelsPerMeterY);
        }
        spriteBatch.end();
        // the ambient light is determined by the last rendered RayHandler.
        rayHandler.render();

        physics2DDebugRenderer.begin();
        physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
        for (EntityMini entityMini : entities) {
            Body body = entityMini.body;
            Joint joint = entityMini.joint;
            if (body != null) physics2DDebugRenderer.drawBody(entityMini.body);
            if (joint != null) physics2DDebugRenderer.drawJoint(entityMini.joint);
        }
        physics2DDebugRenderer.end();

        OrthographicCamera orthographicCamera = camera.lens;
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            orthographicCamera.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            orthographicCamera.zoom -= 0.1f;
        if (Gdx.input.isKeyJustPressed(Input.Keys.K))
            pointLight1.setActive(!pointLight1.isActive());

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            EntityMini entityMini = new EntityMini();
            entityMini.transform2D = context.factoryTransform2D.
                    create(MathUtils.random(-10, 10), MathUtils.random(-1, 4), 1, MathUtils.random(0, 2 * (float)Math.PI), 1, 1);
            entityMini.animation = new Animation<>(1,
                    context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions(getRandomRegion()));
            Filter filter = new Filter();
            filter.categoryBits = 0x0100;
            filter.maskBits = 0x0010;
            entityMini.body = createBody(world, new RigidBody2DData(
                            BodyDef.BodyType.DynamicBody,
                            RigidBody2DData.Shape.RECTANGLE,
                            UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionWidth(), camera.pixelsPerMeterX),
                            UtilsRigidBody2D.getBox2DLength(entityMini.animation.getKeyFrame(0).getRegionHeight(), camera.pixelsPerMeterY),
                            filter,
                            1,1,0.2f,false),
                    entityMini.transform2D);
            entityMini.body.setUserData(entityMini);
            entityMini.transform2D.transform = entityMini.body.getTransform();
            entities.add(entityMini);
        }

        // activate and deactivate bodies
        EntityMini entityMini = entities.get(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            entityMini.body.setActive(!entityMini.body.isActive()); // <- active / inactive bodies will maintain linear and angular velocity.
            //entityMini.body.setAwake(!entityMini.body.isAwake());
        }
        System.out.println(entityMini.body.getLinearVelocity());
        System.out.println(entityMini.body.getAngularVelocity());


        // FOLLOW MOUSE
        Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.lens.unproject(v);
        mouse.body.setTransform(v.x, v.y,0);
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
        fixtureDef.filter.categoryBits = data.filter.categoryBits;
        fixtureDef.filter.maskBits = data.filter.maskBits;
        fixtureDef.filter.groupIndex = data.filter.groupIndex;
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
