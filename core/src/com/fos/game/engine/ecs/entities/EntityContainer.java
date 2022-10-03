package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.audio.AudioPlayer;
import com.fos.game.engine.ecs.systems.physics2d.Physics2D;
import com.fos.game.engine.ecs.systems.physics3d.Physics3D;
import com.fos.game.engine.ecs.systems.signals.SignalRouter;

public class EntityContainer implements Disposable {

    // later think about possible improvements
    // systems
    private Physics2D physics2D;
    private Physics3D physics3D;
    private AudioPlayer audioPlayer;
    private SignalRouter signalRouter;

    // container array
    public Array<Entity> entities;
    public Array<Entity> toRemove;

    public EntityContainer(boolean use2DPhysics, boolean use3DPhysics) {
        if (use2DPhysics) initPhysicsWorld2D();
        if (use3DPhysics) initPhysicsWorld3D();
        this.audioPlayer = new AudioPlayer();
        this.signalRouter = new SignalRouter();
        this.entities = new Array<>();
        this.toRemove = new Array<>();
    }

    private void initPhysicsWorld2D() {
        this.physics2D = new Physics2D();
    }

    private void initPhysicsWorld3D() {
        this.physics3D = new Physics3D();
    }

    public World getPhysics2D() {
        return physics2D.world;
    }

    public btDynamicsWorld getPhysics3D() {
        return physics3D.dynamicsWorld;
    }

    public void addEntity(final Entity entity) {
        // TODO: implement like it should be
        entity.alive = true;
        entity.currentContainer = this;
        entities.add(entity);
        entity.localId = entities.size-1;
        if ((entity.componentsBitMask & ComponentType.PHYSICS_BODY_3D.bitMask) == ComponentType.PHYSICS_BODY_3D.bitMask) {
            btRigidBody body = (btRigidBody) entity.components[ComponentType.PHYSICS_BODY_3D.ordinal()];
            physics3D.dynamicsWorld.addRigidBody(body);
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
        if (physics3D != null) physics3D.process(this);
        if (physics2D != null) physics2D.process(this);
        if (signalRouter != null) signalRouter.process(this);
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
        if (physics2D != null) physics2D.dispose();
        if (physics3D != null) physics3D.dispose();
    }
}
