package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.physics.PhysicsWorld2D;
import com.fos.game.engine.ecs.systems.physics.PhysicsWorld3D;
import com.fos.game.engine.ecs.systems.signals.SignalRouter;

public class EntityContainer implements Disposable {

    // later think about possible improvements
    // systems
    private SignalRouter signalRouter;
    private PhysicsWorld2D physicsWorld2D;
    private PhysicsWorld3D physicsWorld3D;

    // container array
    public Array<Entity> entities;
    public Array<Entity> toRemove;

    public EntityContainer(boolean use2DPhysics, boolean use3DPhysics) {
        if (use2DPhysics) initPhysicsWorld2D();
        if (use3DPhysics) initPhysicsWorld3D();
        signalRouter = new SignalRouter();
        this.entities = new Array<>();
        this.toRemove = new Array<>();
    }

    private void initPhysicsWorld2D() {
        this.physicsWorld2D = new PhysicsWorld2D();
    }

    private void initPhysicsWorld3D() {
        this.physicsWorld3D = new PhysicsWorld3D();
    }

    public World getPhysicsWorld2D() {
        return physicsWorld2D.world;
    }

    public btDynamicsWorld getPhysicsWorld3D() {
        return physicsWorld3D.dynamicsWorld;
    }

    public void addEntity(final Entity entity) {
        // TODO: implement like it should be
        entity.alive = true;
        entity.currentContainer = this;
        entities.add(entity);
        entity.localId = entities.size-1;
        if ((entity.componentsBitMask & ComponentType.PHYSICS_BODY_3D.bitMask) == ComponentType.PHYSICS_BODY_3D.bitMask) {
            btRigidBody body = (btRigidBody) entity.components[ComponentType.PHYSICS_BODY_3D.ordinal()];
            physicsWorld3D.dynamicsWorld.addRigidBody(body);
        }
    }

    public void removeEntity(final Entity entity) {
        // TODO: if (hasPhysics) ...

        entities.removeValue(entity, true);
    }

    public void update(float deltaTime) {
        // update all entities
        removeDeadEntities();
        // entity update should be refactored into systems.
        for (Entity entity : entities) entity.update(deltaTime);
        // update all scripts
        // ...
        final float delta = Math.min(1f / 30f, deltaTime);
        if (physicsWorld3D != null) physicsWorld3D.stepSimulation(delta);
        if (physicsWorld2D != null) physicsWorld2D.stepSimulation(deltaTime);
        if (signalRouter != null) signalRouter.routeSignals(this);
    }

    private void removeDeadEntities() {
        this.toRemove.clear();
        for (Entity entity : entities) {
            if (!entity.alive) toRemove.add(entity);
        }
        for (Entity entity : toRemove) {
            removeEntity(entity);
        }
    }

    @Override
    public void dispose() {
        if (physicsWorld2D != null) physicsWorld2D.dispose();
        if (physicsWorld3D != null) physicsWorld3D.dispose();
    }
}
