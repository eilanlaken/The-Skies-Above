package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

public class Entity implements Disposable {

    public EntityContainer container;
    public Entity parent;
    public Array<Entity> children;

    public Object[] components;
    public int componentsBitMask;
    public int category;
    public boolean active = true;

    public Entity(final Enum category) {
        this.category = 0b000001 << category.ordinal();
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

    public void attachChild(final Entity child) {
        if (children == null) children = new Array<>(false, 3);
        children.add(child);
        child.parent = this;
    }

    public void detachChild(final Entity child) {
        children.removeValue(child, true);
        child.parent = null;
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
        return stringBuilder.toString();
    }

}
