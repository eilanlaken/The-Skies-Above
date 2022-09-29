package com.fos.game.screens.tests;

import com.fos.game.engine.components.animation.FactoryAnimation;
import com.fos.game.engine.components.animation.SpriteSheet;
import com.fos.game.engine.components.camera.FactoryCamera;
import com.fos.game.engine.components.scripts.ComponentScripts;
import com.fos.game.engine.components.transform.FactoryTransform2D;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen;
import com.fos.game.engine.entities.Entity;
import com.fos.game.engine.entities.EntityContainer;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.renderer.system.Renderer;
import com.fos.game.scripts.common.ParentTransform2D;

import java.util.HashMap;
import java.util.Map;

public class SimpleScene extends GameScreen {
    private final GameAssetManager assetManager;

    // models
    private SpriteSheet testSpriteSheet;
    private EntityContainer container;

    // rendering
    private Renderer renderer;

    // entities
    private Entity camera;
    private Entity orangeSquare, greenSquare;

    public enum EntityLayers {
        LAYER_1,
        LAYER_2,
    }

    public SimpleScene(final GameContext context) {
        super(context);
        this.assetManager = context.assetManager;
        this.renderer = new Renderer(false);


    }

    @Override
    public void show() {
        setupAssets();
        setupScene();
    }

    private void setupAssets() {
        testSpriteSheet = assetManager.get("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
    }

    private void setupScene() {
        container = new EntityContainer(false, true);

        /*
        Script cameraScript = new Script() {
            OrthographicCamera lens;
            @Override
            public void start() {
                ComponentCamera camera = (ComponentCamera) entity.components[ComponentType.CAMERA.ordinal()];
                lens = (OrthographicCamera) camera.lens;
            }

            @Override
            public void update(float delta) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) lens.position.x -= 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) lens.position.x += 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) lens.position.y += 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) lens.position.y -= 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) lens.zoom += 0.1f;
                if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) lens.zoom -= 0.1f;
                lens.update();
            }
        };

         */
        camera = new Entity(
                FactoryCamera.create2DCamera(EntityLayers.LAYER_1, EntityLayers.LAYER_2)
        );

        /*
        Script script = new Script() {
            ComponentTransform2D transform2D;
            Vector2 position = new Vector2();
            @Override
            public void start() {
                transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            }

            @Override
            public void update(float delta) {
                float angle = transform2D.getRotation();
                float x = transform2D.getPosition().x;
                float y = transform2D.getPosition().y;
                float deltaX = 0;
                float deltaY = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.A)) deltaX -= 0.5f;
                if (Gdx.input.isKeyPressed(Input.Keys.D)) deltaX += 0.5f;
                if (Gdx.input.isKeyPressed(Input.Keys.W)) deltaY += 0.5f;
                if (Gdx.input.isKeyPressed(Input.Keys.S)) deltaY -= 0.5f;
                if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                    transform2D.scaleX += 0.01f;
                    transform2D.scaleY += 0.01f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                    transform2D.scaleX -= 0.01f;
                    transform2D.scaleY -= 0.01f;
                }
                this.position.set(x + deltaX, y + deltaY);
                if (Gdx.input.isKeyPressed(Input.Keys.R)) transform2D.transform.setRotation(angle + 0.1f);
                transform2D.transform.setPosition(this.position);
            }
        };

         */

        orangeSquare = new Entity(
                FactoryTransform2D.create(0,0),
                FactoryAnimation.create(testSpriteSheet, "testArrowOrange"),
                new ComponentScripts()
        );

        greenSquare = new Entity(
                FactoryTransform2D.create(50,50),
                FactoryAnimation.create(testSpriteSheet, "testArrowGreen"),
                new ComponentScripts(new ParentTransform2D(orangeSquare))
        );

        container.addEntity(camera);
        container.addEntity(orangeSquare);
        container.addEntity(greenSquare);

    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        renderer.render(container);
    }

    @Override
    public void update(float deltaTime) {
        final float delta = Math.min(1f / 30f, deltaTime);
        container.update(delta);
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }

}
