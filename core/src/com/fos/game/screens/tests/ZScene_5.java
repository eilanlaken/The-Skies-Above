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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.PolygonSpriteBatch;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.core.graphics.spine.*;
import com.fos.game.engine.ecs.components.animations2d.ComponentFrameAnimations2D;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.transform.ComponentTransform;
import com.fos.game.engine.ecs.systems.renderer_old.base.Physics2DDebugRenderer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Spine integration test
 */
public class ZScene_5 extends Scene_old {

    private World world;
    Array<EntityMini> entities;
    EntityMini entityMini1, entityMini2;

    private ComponentCamera camera;

    PolygonSpriteBatch batch = new PolygonSpriteBatch();
    SkeletonRenderer renderer = new SkeletonRenderer();
    SkeletonRendererDebug debugRenderer = new SkeletonRendererDebug();
    TextureAtlas atlas;
    Skeleton skeleton;
    AnimationState state;
    float angle = 0;

    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();
    RayHandler rayHandler;
    PointLight pointLight1, pointLight2, pointLight3;

    class EntityMini {
        ComponentTransform transform;
        ComponentFrameAnimations2D animations;
        Body body;
        Joint joint;
    }

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public ZScene_5(final GameContext context) {
        super(context);
        init();
    }

    private void init() {
        renderer.setPremultipliedAlpha(true);
        debugRenderer.setMeshTriangles(false);
        debugRenderer.setRegionAttachments(false);
        debugRenderer.setMeshHull(false);

        atlas = new TextureAtlas(Gdx.files.internal("spine/raptor-pma.atlas"));

        SkeletonJson loader = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
        // SkeletonLoader loader = new SkeletonBinary(atlas); // Or use SkeletonBinary to load binary data.
        loader.setScale(0.001f); // Load the skeleton at 50% the size it was in Spine.
        SkeletonData skeletonData = loader.readSkeletonData(Gdx.files.internal("spine/raptor-pro.json"));

        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeleton.setPosition(0, 0);


        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.

        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
        state.setTimeScale(1f); // Slow all animations down to 60% speed.

        // Queue animations on tracks 0 and 1.
        state.setAnimation(0, "walk", true);
        state.addAnimation(1, "gun-grab", false, 2); // Keys in higher tracks override the pose from lower tracks.
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
        entityMini1.transform = context.factoryTransform.create2d(-3, 0, 0, 0, 1, 1);
        System.out.println("transform " +  entityMini1.transform);
        entityMini1.animations = context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,0.5f, pixelsPerUnit);
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
        entities.add(entityMini1);


        entityMini2 = new EntityMini();
        entityMini2.transform = context.factoryTransform.create2d(3, 0, 0, 0, 1, 1);
        entityMini2.animations = context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1,1, pixelsPerUnit);
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
        entities.add(entityMini2);

    }

    @Override
    protected void update(float delta) {
        state.update(Gdx.graphics.getDeltaTime()); // Update the animation time.
        if (state.apply(skeleton)) // Poses skeleton using current animations. This sets the bones' local SRT.
            skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.
        debugRenderer.getShapeRenderer().setProjectionMatrix(camera.lens.combined);


        world.step(delta, 6, 2);

        entities.sort(new Comparator<EntityMini>() {
            @Override
            public int compare(EntityMini o1, EntityMini o2) {
                final float z1 = o1.transform.position.z;
                final float z2 = o2.transform.position.z;
                return Float.compare(z1, z2);
            }
        });
        rayHandler.update();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.lens.update();
        rayHandler.setCombinedMatrix((OrthographicCamera) camera.lens);
        batch.setProjectionMatrix(camera.lens.combined);
        batch.begin();
        for (EntityMini entityMini : entities) {
            batch.setColor(1,1,1,1);
            if (entityMini.animations == null) continue;
            entityMini.transform.position.set(entityMini.body.getPosition().x, entityMini.body.getPosition().y, entityMini.transform.position.z);
            entityMini.transform.rotation.set(new Vector3(0,0,1), entityMini.body.getAngle());
            if (cull(entityMini.transform, entityMini.animations, camera.lens)) {
                System.out.println("culling");
                continue;
            }
            if (entityMini == entityMini1) batch.setColor(0,1,0,1);

            batch.draw(entityMini.animations.currentPlayingAnimation.getKeyFrame(delta),
                    entityMini.transform.position.x, entityMini.transform.position.y,
                    entityMini.transform.rotation.getAngleAround(0,0,1),
                    entityMini.transform.scale.x, entityMini.transform.scale.y,
                    entityMini.animations.size, entityMini.animations.pixelsPerUnit);
        }
        renderer.draw(batch, skeleton); // Draw the skeleton images.
        angle += delta * 5;
        skeleton.getRootBone().setRotation(angle);
        skeleton.setPosition(2,2);
        System.out.println(skeleton.getBones().get(4).getData().getName());
        System.out.println(skeleton.getBones().get(4).getWorldY());
        batch.end();
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
            entityMini1.body.setTransform(entityMini1.transform.position.x - 0.1f, entityMini1.transform.position.y, entityMini1.body.getAngle());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            entityMini1.body.setTransform(entityMini1.transform.position.x + 0.1f, entityMini1.transform.position.y, entityMini1.body.getAngle());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            entityMini1.body.setTransform(entityMini1.transform.position.x, entityMini1.transform.position.y, entityMini1.body.getAngle() + 0.1f);
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


    private Body createBody(World world, RigidBody2DData data, ComponentTransform transform) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        if (transform == null) System.out.println("is null");
        bodyDef.position.set(transform.position.x, transform.position.y);
        bodyDef.angle = transform.rotation.getAngleAround(0,0,1);
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

    // TODO: implement culling for Spline 2D outputs.
    private static boolean cull(ComponentTransform transform, ComponentFrameAnimations2D animation, final Camera camera) {
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform.scale.x;
        final float height = atlasRegion.getRegionHeight() * transform.scale.y;
        final float boundingRadius = Math.max(width, height) * 2 * animation.size / animation.pixelsPerUnit;
        return !camera.frustum.sphereInFrustum(transform.position.x, transform.position.y, 0, boundingRadius);
    }

}
