package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.lights3d.Light;
import com.fos.game.engine.ecs.components.modelinstance_old.ComponentModelInstance;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.physics3d.ComponentRigidBody3D;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;
import com.fos.game.engine.ecs.components.transform2d_old.FactoryTransform2D;
import com.fos.game.engine.ecs.components.transform3d_old.ComponentTransform3D;
import com.fos.game.engine.ecs.components.transform3d_old.FactoryTransform3D;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

public class Entity implements Disposable {

    public EntityContainer container;

    public int localId = -1; // effectively its index in the current entity container's entities array
    public int globalId = -1; // a global unique identifier; Only persistent and "important" Entities has a globalId.
    public Object[] components;
    public int componentsBitMask;
    public int category;
    public boolean active = true;

    @Deprecated
    public Entity() {
        this.category = 0b000001;
    }

    @Deprecated
    public Entity(final Enum category, final Object ...componentsToAttach) {
        attachComponents(componentsToAttach);
        this.category = 0b000001 << category.ordinal();
    }

    @Deprecated
    public Entity(final Object ...componentsToAttach) {
        attachComponents(componentsToAttach);
        this.category = 0b000001;
    }

    public void attachComponents(final Object... componentsToAttach) {
        this.components = new Component[ComponentType.values().length];
        for (Object object : componentsToAttach) {
            final Component component = (Component) object;
            if (component != null) components[component.getComponentType().ordinal()] = component;
        }
        this.componentsBitMask = EntityUtils.computeBitMask(this.components);
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
