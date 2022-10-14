package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.lights3d.Light;
import com.fos.game.engine.ecs.components.modelinstance_old.ComponentModelInstance;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.physics3d.ComponentRigidBody3D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.components.transform3d.ComponentTransform3D;
import com.fos.game.engine.ecs.components.transform2d.FactoryTransform2D;
import com.fos.game.engine.ecs.components.transform3d.FactoryTransform3D;

public class Entity implements Disposable {

    public EntityContainer container;

    public int localId = -1; // effectively its index in the current entity container's entities array
    public int globalId = -1; // a global unique identifier; Only persistent and "important" Entities has a globalId.
    public Object[] components;
    public short componentsBitMask;
    public int layer;
    public boolean active = true;

    @Deprecated
    public Entity() {
        this.layer = 0b000001;
    }

    @Deprecated
    public Entity(final Enum layer, final Object ...componentsToAttach) {
        attachComponents(componentsToAttach);
        refresh();
        this.layer = 0b000001 << layer.ordinal();
    }

    @Deprecated
    public Entity(final Object ...componentsToAttach) {
        attachComponents(componentsToAttach);
        refresh();
        this.layer = 0b000001;
    }

    public void attachComponents(final Object... componentsToAttach) {
        this.components = new Component[ComponentType.values().length];
        for (Object object : componentsToAttach) {
            final Component component = (Component) object;
            if (component != null) components[component.getComponentType().ordinal()] = component;
        }
        refresh();
    }

    @Deprecated // TODO: deprecate.
    private void refresh() {
        componentsBitMask = EntityUtils.computeBitMask(this.components);

        ComponentTransform2D transform2D = (ComponentTransform2D) components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentTransform3D transform3d = (ComponentTransform3D) components[ComponentType.TRANSFORM_3D.ordinal()];
        ComponentModelInstance modelInstance = (ComponentModelInstance) components[ComponentType.MODEL_INSTANCE.ordinal()];
        ComponentRigidBody2D rigidBody2d = (ComponentRigidBody2D) components[ComponentType.PHYSICS_2D_BODY.ordinal()];
        ComponentRigidBody3D rigidBody3d = (ComponentRigidBody3D) components[ComponentType.PHYSICS_3D_BODY.ordinal()];
        Light light = (Light) components[ComponentType.LIGHT_3D.ordinal()];

        if (transform2D == null) {
            components[ComponentType.TRANSFORM_2D.ordinal()] = FactoryTransform2D.create();
            transform2D = (ComponentTransform2D) components[ComponentType.TRANSFORM_2D.ordinal()];
        }
        if (transform3d == null) {
            components[ComponentType.TRANSFORM_3D.ordinal()] = FactoryTransform3D.create();
        }
        if (rigidBody2d != null) {
            rigidBody2d.body.setTransform(transform2D.transform.getPosition().x, transform2D.transform.getPosition().y, transform2D.transform.getRotation());
            transform2D.transform = rigidBody2d.body.getTransform();
            for (Fixture fixture : rigidBody2d.body.getFixtureList()) {
                fixture.setUserData(this);
            }
        }
        if (rigidBody3d != null) {
            rigidBody3d.userData = this;
            rigidBody3d.setWorldTransform(transform3d);
        }
        if (modelInstance != null) {
            modelInstance.instance.transform = (Matrix4) components[ComponentType.TRANSFORM_3D.ordinal()];
        }
        if (light != null) {

        }
    }

    public Object getComponent(final ComponentType componentType) {
        return components[componentType.ordinal()];
    }

    @Override
    public void dispose() {
        if (components[ComponentType.PHYSICS_3D_BODY.ordinal()] != null) {
            btRigidBody body = (btRigidBody) components[ComponentType.PHYSICS_3D_BODY.ordinal()];
            body.getCollisionShape().dispose();
            body.dispose();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("e_:");
        ComponentTransform2D t = (ComponentTransform2D) components[ComponentType.TRANSFORM_2D.ordinal()];
        stringBuilder.append(t.z);
        return stringBuilder.toString();
    }

}
