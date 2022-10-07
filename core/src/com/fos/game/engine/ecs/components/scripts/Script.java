package com.fos.game.engine.ecs.components.scripts;

import com.fos.game.engine.ecs.entities.Entity;

public class Script {

    protected final Entity entity;

    protected Script(final Entity entity) {
        this.entity = entity;
    }

    public void start() {}
    public void update(float delta) {}

}
