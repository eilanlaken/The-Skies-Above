package com.fos.game.engine.ecs.components.logic;

import com.fos.game.engine.ecs.entities.Entity;

public class Logic {

    protected final Entity entity;

    protected Logic(final Entity entity) {
        this.entity = entity;
    }

    public void start() {}
    public void update(float delta) {}

}
