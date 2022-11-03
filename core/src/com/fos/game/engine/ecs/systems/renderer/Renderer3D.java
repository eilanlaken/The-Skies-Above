package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.entities.Entity;

public class Renderer3D implements Disposable {

    private final ModelBatch modelBatch;

    protected Renderer3D() {
        this.modelBatch = new ModelBatch(new ShaderProvider(), new ModelInstanceSorter());
    }

    protected void renderToCameraInternalBuffer(final ComponentCamera camera, final Array<Entity> entities, final boolean debugMode) {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
