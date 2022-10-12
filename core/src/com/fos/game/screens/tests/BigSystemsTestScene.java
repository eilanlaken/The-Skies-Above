package com.fos.game.screens.tests;

import com.badlogic.gdx.Gdx;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class BigSystemsTestScene extends Scene {

    public enum Layers {
        CAMERA,
        CHARACTER,
        ENEMY,
        UI,
    }

    EntityContainer container;

    public BigSystemsTestScene(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        container = new EntityContainer();
        for (int i = 0; i < 5; i++) {
            Entity entity = new Entity(Layers.CHARACTER);
            entity.attachComponents(
              context.factoryTransform2D.create(-5 + i,0,0,0,1,1),
              context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "orange")
            );
            container.addEntity(entity);
        }

        Entity camera = new Entity(Layers.CAMERA);
        camera.attachComponents(
                context.factoryTransform2D.create(0,0,0,0,1,1),
                context.factoryCamera2D.createCamera2D(30, 30 * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth(), Layers.CHARACTER)
        );
        container.addEntity(camera);

    }

    @Override
    protected void update(float delta) {
        container.update();
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
        assetNameClassMap.put("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
