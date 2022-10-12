package com.fos.game.engine.ecs.systems.renderer.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights3d.LightingEnvironment;
import com.fos.game.engine.ecs.components.modelinstance.ComponentModelInstance;
import com.fos.game.engine.ecs.components.modelinstance.ModelInstance;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;
import com.fos.game.engine.ecs.systems.base.SystemConfig;
import com.fos.game.engine.ecs.systems.renderer.shaders.postprocessing.PostProcessingShaderProgram;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Renderer implements EntitiesProcessor, Disposable {

    public static final String NO_CAMERA_IN_CONTAINER_EXCEPTION_MESSAGE = "Entity Container must contain at least 1 (one) " +
            "Entity with a" + ComponentCamera.class.getSimpleName() + " " + Component.class.getSimpleName() + " in order to render.";
    public static final float DEFAULT_GAMMA = 1f / 2.2f;

    private ModelBatch modelBatch;
    private SpriteBatch spriteBatch;

    // TODO: test
    private Physics2DDebugRenderer physics2DDebugRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;

    private ShapeRenderer shapeRenderer;
    // config
    public float pixelsPerMeter;
    public boolean debugMode;

    private PostProcessingShaderProgram postProcessingShaderProgram;
    private FrameBuffer frameBuffer;

    private final Map<ComponentCamera, Array<Entity>> camera2DEntitiesMap = new HashMap<>();
    private final Map<ComponentCamera, Array<Entity>> camera3DEntitiesMap = new HashMap<>();
    private final LightingEnvironment lightingEnvironment = new LightingEnvironment();
    private final Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasMap = new HashMap<>();

    public Renderer() {
        this.modelBatch = new ModelBatch(new ShaderProvider(), new ModelInstanceSorter());
        this.spriteBatch = new SpriteBatch();

        // TODO: test
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.shapeRenderer = new ShapeRenderer();
        this.pixelsPerMeter = Config.DEFAULT.pixelsPerMeter;
        this.debugMode = Config.DEFAULT.debugMode;
        this.postProcessingShaderProgram = new PostProcessingShaderProgram();
        /* create FrameBuffer */
        final GLFrameBuffer.FrameBufferBuilder frameBufferBuilderScene = new GLFrameBuffer.FrameBufferBuilder(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frameBufferBuilderScene.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- regular
        frameBufferBuilderScene.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- bloom
        frameBufferBuilderScene.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- black silhouette
        frameBufferBuilderScene.addDepthTextureAttachment(GL30.GL_DEPTH_COMPONENT , GL30.GL_UNSIGNED_BYTE);
        frameBuffer = frameBufferBuilderScene.build();
        for (Texture attached : frameBuffer.getTextureAttachments()) {
            attached.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            attached.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    @Override
    public void process(final Array<Entity> entities) {
        RenderingUtils.prepareForRendering(entities, lightingEnvironment, camera2DEntitiesMap, camera3DEntitiesMap, renderTargetCamerasMap);
        int camera3DCount = camera3DEntitiesMap.size();
        int camera2DCount = camera2DEntitiesMap.size();
        if (camera2DCount == 0 && camera3DCount == 0) throw new IllegalStateException(NO_CAMERA_IN_CONTAINER_EXCEPTION_MESSAGE);
        for (Map.Entry<RenderTarget, Array<ComponentCamera>> renderTargetCameras : renderTargetCamerasMap.entrySet()) {
            renderToTarget(renderTargetCameras.getKey());
        }
    }

    private void renderToTarget(final RenderTarget renderTarget) {
        final FrameBuffer secondaryFrameBuffer = renderTarget == null ? frameBuffer : renderTarget.secondaryFrameBuffer;
        final FrameBuffer primaryFrameBuffer = renderTarget == null ? null : renderTarget.primaryFrameBuffer;
        // render 3D scene
        secondaryFrameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        int index = 0;
        for (Map.Entry<ComponentCamera, Array<Entity>> cameraEntities : camera3DEntitiesMap.entrySet()) {
            if (index == 0) modelBatch.begin(cameraEntities.getKey().lens);
            else modelBatch.setCamera(cameraEntities.getKey().lens);
            for (Entity entity : cameraEntities.getValue()) {
                ComponentModelInstance component = (ComponentModelInstance) entity.components[ComponentType.MODEL_INSTANCE.ordinal()];
                ModelInstance instance = component.instance;
                modelBatch.render(instance, lightingEnvironment);
            }
            index++;
        }
        modelBatch.end();
        secondaryFrameBuffer.end();
        if (primaryFrameBuffer != null) primaryFrameBuffer.begin();
        // render 2D scene
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setShader(null);
        spriteBatch.begin();
        TextureRegion sceneRegion = new TextureRegion(secondaryFrameBuffer.getTextureAttachments().get(0));
        sceneRegion.flip(false, true);
        spriteBatch.draw(sceneRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.setShader(postProcessingShaderProgram);
        TextureRegion region = new TextureRegion(secondaryFrameBuffer.getTextureAttachments().get(1));
        region.flip(false, true);
        spriteBatch.draw(region, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.setShader(null);
        // draw all 2d atlas regions
        for (Map.Entry<ComponentCamera, Array<Entity>> cameraEntities : camera2DEntitiesMap.entrySet()) {
            ComponentCamera camera = cameraEntities.getKey();
            spriteBatch.setProjectionMatrix(camera.lens.combined);
            for (Entity entity : cameraEntities.getValue()) {
                ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
                TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
                spriteBatch.render(atlasRegion, transform2D, pixelsPerMeter);
            }
        }
        spriteBatch.end();

        // TODO: test. If debug mode, render shapes for 2d entities with physics.
        if (debugMode) {
            for (Map.Entry<ComponentCamera, Array<Entity>> cameraEntities : camera2DEntitiesMap.entrySet()) {
                ComponentCamera camera = cameraEntities.getKey();
                //physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);


            }
        }
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.rect(0,0,80,80);
        //shapeRenderer.end();
        if (primaryFrameBuffer != null) primaryFrameBuffer.end();
    }

    @Override
    // TODO: fix to make more efficient using bitwise operations.
    public boolean shouldProcess(Entity entity) {
        if ((entity.componentsBitMask & RenderingUtils.ATTACHED_ANIMATIONS_2D_BIT_MASK) > 0) return true;
        if ((entity.componentsBitMask & RenderingUtils.ATTACHED_MODEL_INSTANCE_BIT_MASK) > 0) return true;
        if ((entity.componentsBitMask & RenderingUtils.ATTACHED_LIGHT_BIT_MASK) > 0) return true;
        if ((entity.componentsBitMask & RenderingUtils.ATTACHED_CAMERA_BIT_MASK) > 0) return true;
        return false;
    }

    @Override
    public void dispose() {
        // TODO: implement as it should be.
    }

    public void config(final Config config) {
        this.pixelsPerMeter = config.pixelsPerMeter;
        this.debugMode = config.debugMode;
    }

    public static final class Config extends SystemConfig {

        public static final Config DEFAULT = new Config(32, true);
        public final boolean debugMode;
        public final float pixelsPerMeter;

        public Config(final float pixelsPerMeter, final boolean debugMode) {
            this.debugMode = debugMode;
            this.pixelsPerMeter = pixelsPerMeter;
        }
    }


}
