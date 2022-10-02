package com.fos.game.engine.systems.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.utils.Disposable;

public class PhysicsWorld3D implements Disposable {

    public btDynamicsWorld dynamicsWorld;
    private btConstraintSolver constraintSolver;
    private btDefaultCollisionConfiguration collisionConfig;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase broadPhase;
    private ContactListener contactListener;

    public PhysicsWorld3D() {
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

    public void stepSimulation(float delta) {
        dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
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
