package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.systems.audio.AudioPlayer;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;
import com.fos.game.engine.ecs.systems.base.SystemConfig;
import com.fos.game.engine.ecs.systems.physics2d.Physics2D;
import com.fos.game.engine.ecs.systems.physics3d.Physics3D;
import com.fos.game.engine.ecs.systems.renderer.base.Renderer;
import com.fos.game.engine.ecs.systems.scripting.ScriptsUpdater;
import com.fos.game.engine.ecs.systems.signals.SignalRouter;

import java.util.HashMap;
import java.util.Map;

public class EntityContainer implements Disposable {

    // container array
    protected Array<Entity> entities = new Array<>();
    protected Array<Entity> toAdd = new Array<>();
    protected Array<Entity> toRemove = new Array<>();

    // systems
    protected HashMap<EntitiesProcessor, Array<Entity>> systemEntitiesMap = new HashMap<>();
    public AudioPlayer audioPlayer = new AudioPlayer();
    public Physics2D physics2D = new Physics2D();
    public Physics3D physics3D = new Physics3D();
    //public Renderer_old2 rendererOld2 = new Renderer_old2();
    public Renderer renderer = new Renderer();
    public ScriptsUpdater scriptsUpdater = new ScriptsUpdater();
    public SignalRouter signalRouter = new SignalRouter();


    public EntityContainer() {
        this.systemEntitiesMap.put(audioPlayer, new Array<Entity>());
        this.systemEntitiesMap.put(physics2D, new Array<Entity>());
        this.systemEntitiesMap.put(physics3D, new Array<Entity>());
        // TODO: remove old renderer.
        //this.systemEntitiesMap.put(rendererOld2, new Array<Entity>());
        this.systemEntitiesMap.put(renderer, new Array<Entity>());
        this.systemEntitiesMap.put(scriptsUpdater, new Array<Entity>());
        this.systemEntitiesMap.put(signalRouter, new Array<Entity>());
    }

    // TODO: implement like it should be. Delete this shit.
    @Deprecated
    public void addEntity(final Entity entity) {
        this.toAdd.add(entity);
    }

    public void update() {
        EntityContainerUtils.removeEntities(this);
        EntityContainerUtils.addEntities(this);
        EntityContainerUtils.prepareForProcessing(entities, systemEntitiesMap);
        EntityContainerUtils.process(systemEntitiesMap);
    }

    @Deprecated
    public World getPhysics2D() {
        return physics2D.world;
    }
    @Deprecated
    public btDynamicsWorld getPhysics3D() {
        return physics3D.dynamicsWorld;
    }

    public void config(final SystemConfig ...configs) {
        for (final SystemConfig config : configs) {
            if (config instanceof Renderer.Config) renderer.config((Renderer.Config) config);
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
