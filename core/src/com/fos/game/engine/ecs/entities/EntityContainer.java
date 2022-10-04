package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.systems.audio.AudioPlayer;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;
import com.fos.game.engine.ecs.systems.physics2d.Physics2D;
import com.fos.game.engine.ecs.systems.physics3d.Physics3D;
import com.fos.game.engine.ecs.systems.renderer.base.Renderer;
import com.fos.game.engine.ecs.systems.scripting.ScriptsUpdater;
import com.fos.game.engine.ecs.systems.signals.SignalRouter;

import java.util.HashMap;
import java.util.Map;

public class EntityContainer implements Disposable {

    // container array
    public Array<Entity> entities;
    private HashMap<EntitiesProcessor, Array<Entity>> systemEntitiesMap = new HashMap<>();
    private Array<Entity> toAdd;
    private Array<Entity> toRemove;

    public EntityContainer() {
        this.entities = new Array<>();
        this.toRemove = new Array<>();
        this.toAdd = new Array<>();
        this.systemEntitiesMap.put(new AudioPlayer(), new Array<Entity>());
        this.systemEntitiesMap.put(new Physics2D(), new Array<Entity>());
        this.systemEntitiesMap.put(new Physics3D(), new Array<Entity>());
        this.systemEntitiesMap.put(new Renderer(), new Array<Entity>());
        this.systemEntitiesMap.put(new ScriptsUpdater(), new Array<Entity>());
        this.systemEntitiesMap.put(new SignalRouter(), new Array<Entity>());
    }

    // TODO: implement like it should be. Delete this shit.
    public void addEntity(final Entity entity) {
        entity.active = true;
        entity.currentContainer = this;
        entities.add(entity);
        entity.localId = entities.size-1;
        /*
        if ((entity.componentsBitMask & ComponentType.PHYSICS_BODY_3D.bitMask) == ComponentType.PHYSICS_BODY_3D.bitMask) {
            btRigidBody body = (btRigidBody) entity.components[ComponentType.PHYSICS_BODY_3D.ordinal()];
            physics3D.dynamicsWorld.addRigidBody(body);
        }
         */
    }

    public void update(float deltaTime) {
        for (final Entity entity : this.toRemove) {
            // TODO: if has physics...
            this.entities.removeValue(entity, true);
        }
        this.toRemove.clear();
        for (final Entity entity : this.toAdd) {
            // TODO: if has physics...
            this.entities.add(entity);
        }
        this.toAdd.clear();


        for (Entity entity : entities) entity.update(deltaTime); // TODO: this should be gone. No update() method on entities. entity update should be refactored into systems.

        EntityContainerUtils.prepareForProcessing(entities, systemEntitiesMap);
        for (final Map.Entry<EntitiesProcessor, Array<Entity>> systemEntitiesMapEntry : systemEntitiesMap.entrySet()) {
            final EntitiesProcessor entitiesProcessor = systemEntitiesMapEntry.getKey();
            final Array<Entity> processorEntities = systemEntitiesMapEntry.getValue();
            entitiesProcessor.process(processorEntities);
        }
    }



    public World getPhysics2D() {
        return null;
        //return physics2D.world;
    }
    public btDynamicsWorld getPhysics3D() {
        return null;
        //return physics3D.dynamicsWorld;
    }

    @Override
    public void dispose() {
        for(final Map.Entry<EntitiesProcessor, Array<Entity>> entry : systemEntitiesMap.entrySet()) {
            final EntitiesProcessor entitiesProcessor = entry.getKey();
            if (entitiesProcessor instanceof Disposable) ((Disposable) entitiesProcessor).dispose();
        }
    }
}
