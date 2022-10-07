package com.fos.game.engine.ecs.systems.physics2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class Physics2DCollisionResolver implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        ComponentSignalBox signalReceiverA = (ComponentSignalBox) entityA.components[ComponentType.SIGNAL_BOX.ordinal()];
        if (signalReceiverA != null) {
            // the only current reasonable way to make sure a signal
            signalReceiverA.receivedSignals.add(new CollisionSignal(entityA, entityB, contact));
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private static final class CollisionSignal extends Signal {

        private final Entity target;

        protected CollisionSignal(Entity target, Entity collidedWith, Contact contact) {
            super(new Collision(contact, collidedWith));
            this.target = target;
        }

        @Override
        public boolean isTarget(Entity entity) {
            return this.target == entity;
        }
    }

}
