package com.fos.game.screens.tests;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.renderer2d.Renderer2DUtils;

import java.util.HashMap;
import java.util.Map;

public class Renderer2DSortTestScene extends Scene {

    public Renderer2DSortTestScene(final GameContext context) {
        super(context);
    }

    Array<Entity> entities = new Array<>();

    @Override
    protected void start() {
        for (int i = 0; i < 25; i++) {
            Entity entity = new Entity();
            ComponentLight2D light = null;
            ComponentAnimations2D animations = null;
            if (MathUtils.random(10) >= 5) animations = context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "blue");
            if (MathUtils.random(10) >= 5) light = context.factoryLight2D.create();
            entity.attachComponents(
                    context.factoryTransform2D.create(0,0, MathUtils.random(5), 0, 1, 1),
                    animations,
                    light
            );
            entity.layer = MathUtils.random(0,1);
            entities.add(entity);
        }

        System.out.println("unsorted: " + entities);
        entities.sort(Renderer2DUtils.entitiesComparator);
        System.out.println("sorted: " + entities);
    }

    @Override
    protected void update(float delta) {


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
