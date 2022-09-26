package com.fos.game.engine.renderer.system_old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.components.lights.LightingEnvironment;
import com.fos.game.engine.components.modelinstance.ComponentModelInstance;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.entities.Entity;
import com.fos.game.engine.entities.EntityContainer;
import com.fos.game.engine.renderer.shaders.post.PostProcessingSinglePassGaussianBlurHDShaderProgram;

import java.util.HashMap;
import java.util.Map;

public class Renderer implements Disposable {

    public static final float DEFAULT_PIXELS_PER_METER = 32;

    private ModelBatch modelBatch;
    private SpriteBatch spriteBatch;
    private Box2DDebugRenderer box2DDebugRenderer;
    private ShapeRenderer shapeRenderer;
    public float pixelsPerMeter;
    public boolean debugMode;

    private PostProcessingSinglePassGaussianBlurHDShaderProgram blurShaderProgram;
    private FrameBuffer frameBuffer;

    private Map<ComponentCamera, Array<Entity>> camera2DEntitiesMap = new HashMap<>();
    private Map<ComponentCamera, Array<Entity>> camera3DEntitiesMap = new HashMap<>();
    private LightingEnvironment lightingEnvironment = new LightingEnvironment();

    public Renderer(boolean debugMode) {
        this.modelBatch = new ModelBatch(new ShaderProvider(), new ModelInstanceSorter());
        this.spriteBatch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.pixelsPerMeter = DEFAULT_PIXELS_PER_METER;
        this.debugMode = debugMode;
        this.blurShaderProgram = new PostProcessingSinglePassGaussianBlurHDShaderProgram();
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

    public void render(final EntityContainer container) {
        RenderingUtils.prepareForRendering(container.entities, lightingEnvironment, camera2DEntitiesMap, camera3DEntitiesMap);
        int camera3DCount = camera3DEntitiesMap.size();
        int camera2DCount = camera2DEntitiesMap.size();
        if (camera2DCount == 0 && camera3DCount == 0) throw new IllegalStateException("Entity Container must contain at least 1 (one) " +
                "Entity with a" + ComponentCamera.class.getSimpleName() + " in order to render.");
        if (camera3DEntitiesMap.size() > 0) render3DSceneToBuffer();
        renderSceneToScreen();
    }

    private void render3DSceneToBuffer() {
        frameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        int index = 0;
        for (Map.Entry<ComponentCamera, Array<Entity>> cameraEntityEntry : camera3DEntitiesMap.entrySet()) {
            if (cameraEntityEntry.getKey().renderTarget != null) continue;
            if (index == 0) modelBatch.begin(cameraEntityEntry.getKey().lens);
            else modelBatch.setCamera(cameraEntityEntry.getKey().lens);
            for (Entity entity : cameraEntityEntry.getValue()) {
                ComponentModelInstance component = (ComponentModelInstance) entity.components[ComponentType.MODEL_INSTANCE.ordinal()];
                ModelInstance instance = component.instance;
                if (RenderingUtils.doFrustumCulling(instance, cameraEntityEntry.getKey().lens)) continue;
                modelBatch.render(instance, lightingEnvironment);
            }
            index++;
        }
        modelBatch.end();
        frameBuffer.end();
    }

    private void renderSceneToScreen() {
        // revise later for 2D rendering
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setShader(null);
        spriteBatch.begin();
        TextureRegion sceneRegion = new TextureRegion(frameBuffer.getTextureAttachments().get(0));
        sceneRegion.flip(false, true);
        spriteBatch.draw(sceneRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.setShader(blurShaderProgram);
        TextureRegion region = new TextureRegion(frameBuffer.getTextureAttachments().get(1));
        region.flip(false, true);
        spriteBatch.draw(region, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        modelBatch.dispose();
        frameBuffer.dispose();
        blurShaderProgram.dispose();
    }

}
