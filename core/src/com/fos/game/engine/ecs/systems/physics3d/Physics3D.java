package com.fos.game.engine.ecs.systems.physics3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.rigidbody3d.ComponentRigidBody3D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform3D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class Physics3D implements EntitiesProcessor, Disposable {

    public btDynamicsWorld dynamicsWorld;
    private btConstraintSolver constraintSolver;
    private btDefaultCollisionConfiguration collisionConfig;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase broadPhase;
    // TODO: implement customizable contact listener that interacts with scripting logic.
    private ContactListener contactListener;

    public Physics3D() {
        Bullet.init();
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadPhase = new btDbvtBroadphase();
        constraintSolver = new btSequentialImpulseConstraintSolver();
        dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadPhase, constraintSolver, collisionConfig);
        dynamicsWorld.setGravity(new Vector3(0, -9.8f, 0));
        contactListener = new ContactListener() {
            @Override
            public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
                // userValue0, userValue1 are the entity ids of the colliding bodies.
                System.out.print("contact");
                return true;
            }
        };
    }

    public void addRigidBody(btRigidBody body) {
        dynamicsWorld.addRigidBody(body);
    }

    @Override
    public void process(final Array<Entity> entities) {
        // sync transforms: bullet3D's and transform component
        for (final Entity entity : entities) {
            ComponentRigidBody3D body = (ComponentRigidBody3D) entity.components[ComponentType.PHYSICS_BODY_3D.ordinal()];
            ComponentTransform3D transform = (ComponentTransform3D) entity.components[ComponentType.TRANSFORM_3D.ordinal()];
            body.getWorldTransform(transform);
        }
        // step bullet3D simulation
        final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
        dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & Physics3DUtils.PHYSICS_3D_BIT_MASK) > 0;
    }

    @Override
    public void dispose() {
        if (dynamicsWorld != null) dynamicsWorld.dispose();
        if (contactListener != null) contactListener.dispose();
        if (constraintSolver != null) constraintSolver.dispose();
        if (broadPhase != null) broadPhase.dispose();
        if (dispatcher != null) dispatcher.dispose();
        if (collisionConfig != null) collisionConfig.dispose();
    }

}
