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
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.rigidbody2d.RigidBody2DData;
import com.fos.game.engine.ecs.systems.renderer.base.Physics2DDebugRenderer;
import com.fos.game.engine.ecs.systems.renderer.base.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Box2DDebugRendererTestScene6 extends Scene {

    private World world;
    Array<EntityMini> entities;
    private ComponentCamera camera;

    SpriteBatch spriteBatch = new SpriteBatch();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();

    class EntityMini {
        Animation<TextureAtlas.AtlasRegion> animation;
        Body body;
    }

    public Box2DDebugRendererTestScene6(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,-10), true);
        entities = new Array<>();
        camera = context.factoryCamera.createCamera2D(20, 20 * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

        for (int i = 0; i < 10; i++) {
            EntityMini entityMini = new EntityMini();
            entityMini.animation = new Animation<>(1,
                    context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions(getRandomRegion()));
            entityMini.body = createBody(world, new RigidBody2DData(
                    BodyDef.BodyType.DynamicBody,
                    MathUtils.random(-10, 10),
                    MathUtils.random(-1, 4),
                    entityMini.animation.getKeyFrame(0),
                    camera.lens.viewportWidth,
                    camera.lens.viewportHeight,
                    1,1,1,false));
            entities.add(entityMini);
        }

        EntityMini floor = new EntityMini();
        floor.body = createBody(world, new RigidBody2DData(
                BodyDef.BodyType.StaticBody,
                0, -4.5f,
                RigidBody2DData.Shape.RECTANGLE,
                18, 0.5f,
                1,1,1,false)
        );
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
            spriteBatch.draw(entityMini.animation.getKeyFrame(0), entityMini.body, camera.lens.viewportWidth, camera.lens.viewportHeight);
        }
        spriteBatch.end();

        physics2DDebugRenderer.begin();
        physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
        for (EntityMini entityMini : entities) {
            physics2DDebugRenderer.renderBody(entityMini.body);
        }
        physics2DDebugRenderer.end();

        OrthographicCamera orthographicCamera = (OrthographicCamera) camera.lens;
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            orthographicCamera.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            orthographicCamera.zoom -= 0.1f;
        camera.lens.update();
    }

    @Override
    public void resize(int width, int height) {

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

    protected Body createBody(final World world, final RigidBody2DData data) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        bodyDef.position.set(data.positionX, data.positionY);
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

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
