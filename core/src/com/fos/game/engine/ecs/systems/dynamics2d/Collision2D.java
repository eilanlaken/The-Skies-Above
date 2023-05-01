package com.fos.game.engine.ecs.systems.dynamics2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.fos.game.engine.ecs.entities.Entity;

public final class Collision2D {

    public final Contact contact;
    public final Entity collidedWith;

    protected Collision2D(final Contact contact, final Entity collidedWith) {
        this.contact = contact;
        this.collidedWith = collidedWith;
    }

}
