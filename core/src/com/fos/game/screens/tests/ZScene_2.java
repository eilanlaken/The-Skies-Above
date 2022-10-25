package com.fos.game.screens.tests;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteBatch;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.systems.renderer_old.base.Physics2DDebugRenderer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ZScene_2 extends Scene_old {

    private World world;
    Array<EntityMini> entities;
    EntityMini entityMini1, entityMini2;

    private ComponentCamera camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();
    RayHandler rayHandler;
    PointLight pointLight1, pointLight2, pointLight3;


    class EntityMini {
        ComponentTransform2D transform;
        ComponentAnimations2D animations;
        Body body;
        Joint joint;
    }

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public ZScene_2(final GameContext context) {
        super(context);
        runCode();
    }

    private void runCode() {
        Matrix4 m = new Matrix4();
        Vector3 pos = new Vector3(1,1,1);
        Quaternion rot = new Quaternion(new Vector3(0,0,1), 80);
        Vector3 scl = new Vector3(2,3,4);
        System.out.println("m:");
        System.out.println(m);

        m.translate(pos);
        m.scl(scl);
        m.rotate(rot);

        System.out.println("m:");
        System.out.println(m);

        System.out.println("trans: " + m.getTranslation(new Vector3()));
        System.out.println("scl: " + m.getScale(new Vector3()));
        System.out.println("rot: " + m.getRotation(new Quaternion()).getAngleAround(new Vector3(0,0,1)));
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,0), true);
        world.setContactListener(getContactListener());
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.2f);
        pointLight1 = new PointLight(rayHandler, 100, Color.BLUE, 5, 0,0);
        pointLight2 = new PointLight(rayHandler, 100, Color.RED, 5, 2,2);
        pointLight3 = new PointLight(rayHandler, 100, Color.RED, 5, 2,2);
        //coneLight = new ConeLight(rayHandler1, 200, Color.RED, 5, 0, 0, 0, 15);
        //directionalLight = new DirectionalLight(rayHandler, 100, Color.LIME, 30);

        entities = new Array<>();
        camera = context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT);

        entityMini1 = new EntityMini();
        entityMini1.transform = context.factoryTransform2D.create(3, 0, 1, 0, 1, 1);
        entityMini1.animations = context.factoryAnimation2D.create("atlases/test/testSpriteSheet3.atlas", "a", 0.5f, pixelsPerUnit);
        Filter filter = new Filter();
        filter.categoryBits = 0x0001;
        filter.maskBits = 0x0011;
        entityMini1.body = createBody(world, new RigidBody2DData(
                BodyDef.BodyType.DynamicBody,
                RigidBody2DData.Shape.RECTANGLE,
                1,
                1,
                filter,
                1,1,0.2f,false),
                entityMini1.transform);
        entityMini1.body.setUserData(entityMini1);
        entityMini1.transform.transform = entityMini1.body.getTransform();
        entities.add(entityMini1);


        entityMini2 = new EntityMini();
        entityMini2.transform = context.factoryTransform2D.create(-3, 0, 1, 0, 1, 1);
        entityMini2.animations = context.factoryAnimation2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1, pixelsPerUnit);
        Filter filter2 = new Filter();
        filter2.categoryBits = 0x0001;
        filter2.maskBits = 0x0011;
        entityMini2.body = createBody(world, new RigidBody2DData(
                        BodyDef.BodyType.DynamicBody,
                        RigidBody2DData.Shape.RECTANGLE,
                        1,
                        1,
                        filter2,
                        1,1,0.2f,false),
                entityMini2.transform);
        entityMini2.body.setUserData(entityMini2);
        entityMini2.transform.transform = entityMini2.body.getTransform();
        entities.add(entityMini2);

    }

    @Override
    protected void update(float delta) {
        world.step(delta, 6, 2);
        entities.sort(new Comparator<EntityMini>() {
            @Override
            public int compare(EntityMini o1, EntityMini o2) {
                final float z1 = o1.transform.z;
                final float z2 = o2.transform.z;
                return Float.compare(z1, z2);
            }
        });
        rayHandler.update();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.lens.update();
        rayHandler.setCombinedMatrix((OrthographicCamera) camera.lens);
        spriteBatch.setProjectionMatrix(camera.lens.combined);
        spriteBatch.begin();
        for (EntityMini entityMini : entities) {
            if (entityMini.animations == null) continue;
            entityMini.transform.transform.setPosition(entityMini.body.getPosition());
            entityMini.transform.transform.setOrientation(entityMini.body.getTransform().getOrientation());
            if (cull(entityMini.transform, entityMini.animations, camera.lens)) {
                System.out.println("culling");
                continue;
            }
            spriteBatch.draw(entityMini.animations.currentPlayingAnimation.getKeyFrame(delta), entityMini.transform, entityMini.animations.size, entityMini.animations.pixelsPerUnit);
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

        OrthographicCamera orthographicCamera = (OrthographicCamera) camera.lens;
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            orthographicCamera.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            orthographicCamera.zoom -= 0.1f;
        if (Gdx.input.isKeyJustPressed(Input.Keys.K))
            pointLight1.setActive(!pointLight1.isActive());

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            entityMini1.body.setTransform(entityMini1.transform.getPosition().x - 0.1f, entityMini1.transform.getPosition().y, entityMini1.body.getAngle());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            entityMini1.body.setTransform(entityMini1.transform.getPosition().x + 0.1f, entityMini1.transform.getPosition().y, entityMini1.body.getAngle());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            entityMini1.body.setTransform(entityMini1.transform.getPosition().x, entityMini1.transform.getPosition().y, entityMini1.body.getAngle() + 0.1f);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.buildFrameBuffer();
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
        int rand = MathUtils.random(0, 3);
        if (rand == 0) return "a";
        if (rand == 1) return "b";
        if (rand == 2) return "c";
        return "a";
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
        assetNameClassMap.put("atlases/test/testSpriteSheet3.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet4.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }

    // TODO: test culling
    private static boolean cull(ComponentTransform2D transform, ComponentAnimations2D animation, final Camera camera) {
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform.scaleX;
        final float height = atlasRegion.getRegionHeight() * transform.scaleY;
        final float boundingRadius = Math.max(width, height) * 2 * animation.size / animation.pixelsPerUnit;
        return !camera.frustum.sphereInFrustum(transform.transform.getPosition().x, transform.transform.getPosition().y, 0, boundingRadius);
    }

}
