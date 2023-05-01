package com.fos.game.engine.ecs.systems.base;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.audio.AudioPlayer;
import com.fos.game.engine.ecs.systems.dynamics2d.Dynamics2D;
import com.fos.game.engine.ecs.systems.logic.LogicUpdater;
import com.fos.game.engine.ecs.systems.physics3d.Physics3D;
import com.fos.game.engine.ecs.systems.renderer.Config;
import com.fos.game.engine.ecs.systems.renderer.Renderer;
import com.fos.game.engine.ecs.systems.signals.SignalRouter;

import java.util.HashMap;
import java.util.Map;

public class EntityContainer implements Disposable {

    // container array
    protected Array<Entity> entities = new Array<>(false, 1000);
    protected Array<Entity> toAdd = new Array<>(false, 100);
    protected Array<Entity> toRemove = new Array<>(false, 100);
    protected Array<Entity> toUnparent = new Array<>(false, 100);
    protected Array<Entity> toParent = new Array<>(false, 100);

    // systems
    protected HashMap<EntitiesProcessor, Array<Entity>> systemEntitiesMap = new HashMap<>();
    public AudioPlayer audioPlayer = new AudioPlayer();
    public Dynamics2D dynamics2D = new Dynamics2D();
    public Physics3D physics3D = new Physics3D();
    public Renderer renderer = new Renderer(this);
    public LogicUpdater logicUpdater = new LogicUpdater();
    public SignalRouter signalRouter = new SignalRouter();


    public EntityContainer() {
        this.systemEntitiesMap.put(audioPlayer, new Array<Entity>(false, 100));
        this.systemEntitiesMap.put(dynamics2D, new Array<Entity>(false, 1000));
        this.systemEntitiesMap.put(physics3D, new Array<Entity>(false, 1000));
        this.systemEntitiesMap.put(renderer, new Array<Entity>(false, 1000));
        this.systemEntitiesMap.put(logicUpdater, new Array<Entity>(false, 1000));
        this.systemEntitiesMap.put(signalRouter, new Array<Entity>(false, 1000));
    }

    public void update() {
        EntityContainerUtils.parentEntities(this);
        EntityContainerUtils.unparentEntities(this);
        EntityContainerUtils.removeEntities(this);
        EntityContainerUtils.addEntities(this);
        EntityContainerUtils.prepareForProcessing(entities, systemEntitiesMap);
        EntityContainerUtils.process(systemEntitiesMap);
    }

    public void addEntity(Entity entity) {
        this.toAdd.add(entity);
        entity.container = this;
        entity.active = true;
        if (entity.children == null) return;
        for (Entity child : entity.children) {
            addEntity(child);
        }
    }

    public void removeEntity(Entity entity) {
        entity.active = false;
        if (entity.parent != null) entity.parent.detachChild(entity);
        addToRemoveArray(entity);
    }

    private void addToRemoveArray(Entity entity) {
        this.toRemove.add(entity);
        if (entity.children == null) return;
        for (Entity child : entity.children) {
            addToRemoveArray(child);
        }
    }

    public void unparent(Entity entity) {
        this.toUnparent.add(entity);
    }

    public void parent(Entity entity) {
        this.toParent.add(entity);
    }

    public void config(final SystemConfig ...configs) {
        for (final SystemConfig config : configs) {
            if (config instanceof Config) renderer.config((Config) config);
            // .. configure other systems.
        }
    }

    @Override
    public void dispose() {
        for(final Map.Entry<EntitiesProcessor, Array<Entity>> entry : systemEntitiesMap.entrySet()) {
            final EntitiesProcessor entitiesProcessor = entry.getKey();
            if (entitiesProcessor instanceof Disposable) ((Disposable) entitiesProcessor).dispose();
        }
    }
}
