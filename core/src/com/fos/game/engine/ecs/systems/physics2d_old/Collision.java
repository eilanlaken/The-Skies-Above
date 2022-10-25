package com.fos.game.engine.ecs.systems.physics2d_old;

import com.badlogic.gdx.physics.box2d.Contact;
import com.fos.game.engine.ecs.entities.Entity;

public final class Collision {

    public final Contact contact;
    public final Entity collidedWith;

    protected Collision(final Contact contact, final Entity collidedWith) {
        this.contact = contact;
        this.collidedWith = collidedWith;
    }

}
