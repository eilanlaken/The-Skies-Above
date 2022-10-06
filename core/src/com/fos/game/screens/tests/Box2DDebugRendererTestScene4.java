package com.fos.game.screens.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.systems.renderer.base.Physics2DDebugRenderer;
import com.fos.game.engine.ecs.systems.renderer.base.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Box2DDebugRendererTestScene4 extends Scene {

    private World world;

    EntityMini entityMini1;
    EntityMini entityMini2;

    private Body body1, body2;
    private ComponentCamera camera;

    float capture = 20.0f;
    float viewportWidth = capture;
    float viewportHeight = capture * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    SpriteBatch spriteBatch = new SpriteBatch();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();

    class EntityMini {
        Animation<TextureAtlas.AtlasRegion> animation;
        Body body;
    }

    public Box2DDebugRendererTestScene4(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,-10), true);
        camera = context.factoryCamera.createCamera2D(capture, capture * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body1 = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        body1.createFixture(fixtureDef);
        circle.dispose();



        setBody2();

        entityMini1 = new EntityMini();
        entityMini1.animation = new Animation<>(1,
                context.assetManager.get("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class).findRegions("red"));
        entityMini1.body = body2;
    }

    private void setBody2() {
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(0, -5);
        body2 = world.createBody(bodyDef2);
        bodyDef2.fixedRotation = true;
        PolygonShape shape = new PolygonShape();

        final float textureWidth = 40;
        final float textureHeight = 40;

        final float w = textureWidth * viewportWidth / Gdx.graphics.getWidth();
        final float h = textureHeight * viewportHeight / Gdx.graphics.getHeight();

        shape.setAsBox(w/2,h/2);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape;
        fixtureDef2.density = 100;
        fixtureDef2.isSensor = false;
        body2.createFixture(fixtureDef2);
        shape.dispose();
    }

    @Override
    protected void update(float delta) {
        world.step(delta, 6, 2);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.lens.combined);
        spriteBatch.draw(entityMini1.animation.getKeyFrame(0), entityMini1.body, capture, capture * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        spriteBatch.end();

        physics2DDebugRenderer.begin();
        physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
        physics2DDebugRenderer.drawBody(body1);
        physics2DDebugRenderer.drawBody(body2);
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

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
