package com.fos.game.screens.tests;

import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.scripts.test.PeriodicReadTestScript;
import com.fos.game.scripts.test.PeriodicSendTestScript;

public class SignalTestScene extends Scene {

    EntityContainer container;

    public SignalTestScene(GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        container = new EntityContainer();
        Entity entity1 = new Entity();
        entity1.attachComponents(
                context.factorySignalBox.create(),
                context.factoryScripts.create(new PeriodicSendTestScript(entity1))
        );
        Entity entity2 = new Entity();
        entity2.attachComponents(
                context.factorySignalBox.create(),
                context.factoryScripts.create(new PeriodicReadTestScript(entity2))
        );

        Entity camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera()
        );
        container.addEntity(camera);
        container.addEntity(entity1);
        container.addEntity(entity2);
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
}
