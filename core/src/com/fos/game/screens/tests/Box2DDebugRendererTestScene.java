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
import com.fos.game.engine.ecs.systems.renderer.base.Physics2DDebugRenderer;
import com.fos.game.engine.ecs.systems.renderer.base.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class Box2DDebugRendererTestScene extends Scene {

    private World world;

    EntityMini entityMini1;
    EntityMini entityMini2;

    private Body body1, body2;
    private OrthographicCamera camera;

    int capture = 160;
    SpriteBatch spriteBatch = new SpriteBatch();
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    Physics2DDebugRenderer physics2DDebugRenderer = new Physics2DDebugRenderer();

    class EntityMini {
        Animation<TextureAtlas.AtlasRegion> animation;
        Body body;
    }

    public Box2DDebugRendererTestScene(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        world = new World(new Vector2(0,-10), true);
        camera = new OrthographicCamera(capture, capture * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());


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


        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;;
        bodyDef2.position.set(0, -1);
        body2 = world.createBody(bodyDef2);
        bodyDef2.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape;
        fixtureDef2.density = 100;
        fixtureDef2.isSensor = false;
        body2.createFixture(fixtureDef2);
        shape.dispose();


        entityMini1 = new EntityMini();
        entityMini1.animation = new Animation<>(1,
                context.assetManager.get("atlases/test/testSpriteSheet.atlas", SpriteSheet.class).findRegions("animatedArrow"));
        entityMini1.body = body2;
    }

    @Override
    protected void update(float delta) {
        world.step(delta, 6, 2);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.draw(entityMini1.animation.getKeyFrame(0), entityMini1.body, capture, capture * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        spriteBatch.end();

        physics2DDebugRenderer.begin();
        physics2DDebugRenderer.setProjectionMatrix(camera.combined);
        physics2DDebugRenderer.renderBody(body1);
        physics2DDebugRenderer.renderBody(body2);
        physics2DDebugRenderer.end();

        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            camera.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            camera.zoom -= 0.1f;
        camera.update();
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
        return assetNameClassMap;
    }
}
