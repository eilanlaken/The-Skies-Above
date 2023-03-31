package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.audio.ComponentMusic;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.logic.ComponentLogic;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.physics3d.ComponentBody3D;
import com.fos.game.engine.ecs.components.shape2d.ComponentShapes2D;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.storage.ComponentStorage;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.components.transform3d.ComponentTransform3D;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

public class Entity implements Disposable {

    public EntityContainer container;
    public Entity parent;
    public Array<Entity> children;

    public Object[] components;
    public int componentsBitMask;
    public int category;
    public boolean active = false;

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

    public void attachChild(final Entity e) {
        if (e == null) {
            throw new IllegalArgumentException("Child entity cannot be null");
        }
        if (e == this) {
            throw new IllegalArgumentException("Cannot attach an entity to itself");
        }
        if (e.parent != null) {
            throw new IllegalArgumentException("Entity already has a parent");
        }
        if (this.children != null && this.children.contains(e, true)) {
            throw new IllegalArgumentException("Entity is already a child of this entity");
        }
        if (this.children == null) this.children = new Array<>(false, 3);
        this.children.add(e);
        e.parent = this;
        if (this.container != null) this.container.parent(e);
    }

    public void detachChild(final Entity e) {
        if (e == null) {
            throw new IllegalArgumentException("Child entity cannot be null");
        }
        if (e.parent != this) {
            throw new IllegalArgumentException("Entity is not a child of this entity");
        }
        e.parent = null;
        children.removeValue(e, true);
        if (this.container != null) this.container.unparent(e);
    }

    @Override
    public void dispose() {
        if (components[ComponentType.PHYSICS_3D.ordinal()] != null) {
            btRigidBody body = (btRigidBody) components[ComponentType.PHYSICS_3D.ordinal()];
            body.getCollisionShape().dispose();
            body.dispose();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }

    // transform 2d
    public ComponentTransform2D getTransform2D() {
        return (ComponentTransform2D) components[ComponentType.TRANSFORM_2D.ordinal()];
    }

    // transform 2d
    public ComponentTransform3D getTransform3D() {
        return (ComponentTransform3D) components[ComponentType.TRANSFORM_3D.ordinal()];
    }

    // physics 2d
    public ComponentBody2D getBody2D() {
        return (ComponentBody2D) components[ComponentType.PHYSICS_2D.ordinal()];
    }

    // physics 3d
    public ComponentBody3D getPhysics3D() {
        return (ComponentBody3D) components[ComponentType.PHYSICS_3D.ordinal()];
    }

    // graphics
    public ComponentAnimations2D getAnimations2D() {
        return (ComponentAnimations2D) components[ComponentType.GRAPHICS.ordinal()];
    }

    // graphics
    public ComponentCamera getCamera() {
        return (ComponentCamera) components[ComponentType.GRAPHICS.ordinal()];
    }

    // graphics
    public ComponentLight2D getLights2D() {
        return (ComponentLight2D) components[ComponentType.GRAPHICS.ordinal()];
    }

    // graphics
    public ComponentShapes2D getShapes2D() {
        return (ComponentShapes2D) components[ComponentType.GRAPHICS.ordinal()];
    }

    public ComponentSignalBox getSignalBox() {
        return (ComponentSignalBox) components[ComponentType.SIGNALS.ordinal()];
    }

    // audio
    public ComponentSoundEffects getSoundEffects() {
        return (ComponentSoundEffects) components[ComponentType.AUDIO.ordinal()];
    }

    // audio
    public ComponentMusic getMusic() {
        return (ComponentMusic) components[ComponentType.AUDIO.ordinal()];
    }

    // logic
    public ComponentLogic getLogic() {
        return (ComponentLogic) components[ComponentType.LOGIC.ordinal()];
    }

    // storage
    public ComponentStorage getStorage() {
        return (ComponentStorage) components[ComponentType.STORAGE.ordinal()];
    }

}
