package com.fos.game.engine.ecs.systems.dynamics2D;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class Dynamics2DCollisionResolver implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        System.out.println("begin contact");

        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        ComponentSignalBox signalBoxA = (ComponentSignalBox) entityA.components[ComponentType.SIGNALS.ordinal()];
        if (signalBoxA != null) {
            signalBoxA.signalsToSend.add(new Dynamics2DCollisionResolver.BeginCollisionSignal(entityA, contact, entityB));
        }

        ComponentSignalBox signalBoxB = (ComponentSignalBox) entityB.components[ComponentType.SIGNALS.ordinal()];
        if (signalBoxB != null) {
            signalBoxB.signalsToSend.add(new Dynamics2DCollisionResolver.BeginCollisionSignal(entityB, contact, entityA));
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        ComponentSignalBox signalBoxA = (ComponentSignalBox) entityA.components[ComponentType.SIGNALS.ordinal()];
        if (signalBoxA != null) {
            signalBoxA.signalsToSend.add(new Dynamics2DCollisionResolver.EndCollisionSignal(entityA, contact, entityB));
        }

        ComponentSignalBox signalBoxB = (ComponentSignalBox) entityB.components[ComponentType.SIGNALS.ordinal()];
        if (signalBoxB != null) {
            signalBoxB.signalsToSend.add(new Dynamics2DCollisionResolver.EndCollisionSignal(entityB, contact, entityA));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    public static final class BeginCollisionSignal extends Signal {

        private final Entity target;

        protected BeginCollisionSignal(Entity target, Contact contact, Entity collidedWith) {
            super(new Collision(contact, collidedWith));
            this.target = target;
        }

        @Override
        public boolean isTarget(Entity entity) {
            return this.target == entity;
        }
    }

    public static final class EndCollisionSignal extends Signal {

        private final Entity target;

        protected EndCollisionSignal(Entity target, Contact contact, Entity collidedWith) {
            super(new Collision(contact, collidedWith));
            this.target = target;
        }

        @Override
        public boolean isTarget(Entity entity) {
            return this.target == entity;
        }
    }

}
