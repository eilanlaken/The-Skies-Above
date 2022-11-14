package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.utils.Array;

public class GameObject {

    public final Array<Entity> entities;

    protected GameObject() {
        this.entities = new Array<>();
    }

}
