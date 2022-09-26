package com.fos.game.scripts.test;

import com.fos.game.engine.components.scripts.Script;
import com.fos.game.engine.entities.Entity;

public class MultipleEntitiesPrintScript extends Script {

    private Entity[] entities;

    public MultipleEntitiesPrintScript(final Entity... entities) {
        this.entities = entities;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {
        for (final Entity entity : entities) {
            System.out.println(entity.localId);
        }
    }
}
