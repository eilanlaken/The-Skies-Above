package com.fos.game.engine.components.rigidbody;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btShapeHull;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryRigidBody3D extends Factory {

    private static final Vector3 localInertia = new Vector3();
    private static final Vector3 shapeData = new Vector3();
    private static boolean bulletPhysicsInitialized = false;

    public FactoryRigidBody3D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public static ComponentRigidBody3D createRigidBody(final Model model, float mass, final boolean optimized) {
        final Mesh mesh = model.meshes.get(0);
        final btConvexHullShape shape = new btConvexHullShape(mesh.getVerticesBuffer(), mesh.getNumVertices(),
                mesh.getVertexSize());
        if (!optimized) return createRigidBody(shape, mass);
        // else
        final btShapeHull hull = new btShapeHull(shape);
        hull.buildHull(shape.getMargin());
        final btConvexHullShape optimizedShape = new btConvexHullShape(hull);
        shape.dispose();
        hull.dispose();
        return createRigidBody(optimizedShape, mass);
    }

    public static ComponentRigidBody3D createRigidBox(float width, float height, float depth, float mass) {
        init();
        btCollisionShape boxShape = createBoxShape(width, height, depth);
        return createRigidBody(boxShape, mass);
    }

    public static ComponentRigidBody3D createRigidBody(btCollisionShape collisionShape, float mass) {
        init();
        if (mass > 0f) collisionShape.calculateLocalInertia(mass, localInertia);
        else localInertia.set(0, 0, 0);
        btRigidBody.btRigidBodyConstructionInfo constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, collisionShape, localInertia);
        ComponentRigidBody3D body = new ComponentRigidBody3D(constructionInfo);
        constructionInfo.dispose();
        return body;
    }

    // TODO: see if this actually works
    public static ComponentRigidBody3D createRigidBody(btCollisionShape collisionShape, float mass, final btRigidBody source) {
        if (mass > 0f) collisionShape.calculateLocalInertia(mass, localInertia);
        else localInertia.set(0, 0, 0);
        btRigidBody.btRigidBodyConstructionInfo constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, collisionShape, localInertia);
        ComponentRigidBody3D body = new ComponentRigidBody3D(constructionInfo);

        body.setLinearVelocity(source.getLinearVelocity());
        body.setAngularVelocity(source.getAngularVelocity());
        // body.setMotionState(source.getMotionState());

        constructionInfo.dispose();
        return body;
    }

    private static btCollisionShape createBoxShape(float width, float height, float depth) {
        shapeData.set(width, height, depth);
        btCollisionShape boxShape = new btBoxShape(shapeData);
        return boxShape;
    }

    private static void init() {
        if (bulletPhysicsInitialized) return;
        else {
            Bullet.init();
            bulletPhysicsInitialized = true;
        }
    }

}
