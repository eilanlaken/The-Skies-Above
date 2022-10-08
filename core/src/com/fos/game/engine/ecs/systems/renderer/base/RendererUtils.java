package com.fos.game.engine.ecs.systems.renderer.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera2d.ComponentCamera2D;
import com.fos.game.engine.ecs.components.camera3d.ComponentCamera3D;
import com.fos.game.engine.ecs.components.lights3d.Light;
import com.fos.game.engine.ecs.components.lights3d.LightingEnvironment;
import com.fos.game.engine.ecs.components.lights3d.UtilsLights;
import com.fos.game.engine.ecs.components.modelinstance.ComponentModelInstance;
import com.fos.game.engine.ecs.components.modelinstance.ModelInstance;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.components.transform3d.ComponentTransform3D;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.Comparator;
import java.util.Map;

public class RendererUtils {

    protected static final int ATTACHED_GRAPHICS_COMPONENT = ComponentType.ANIMATIONS_2D.bitMask
                    | ComponentType.MODEL_INSTANCE.bitMask
                    | ComponentType.LIGHT_3D.bitMask
                    | ComponentType.CAMERA_2D.bitMask
                    | ComponentType.CAMERA_3D.bitMask;

    private static final Vector3 position = new Vector3();
    private static final Array<Entity> rendered2DEntities = new Array<>();
    private static final Array<Entity> rendered3DEntities = new Array<>();
    private static final Array<ComponentCamera2D> cameras2D = new Array<>();
    private static final Array<ComponentCamera3D> cameras3D = new Array<>();

    private static final Comparator<Entity> rendering2DComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float z1 = ((ComponentTransform2D) e1.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            final float z2 = ((ComponentTransform2D) e2.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            return z1 > z2 ? 1 : z1 < z2 ? -1 : 0;
        }
    };

    protected static void prepareForRendering(final Array<Entity> entities,
                                           LightingEnvironment lightingEnvironmentResult,
                                           Map<ComponentCamera2D, Array<Entity>> use2DCameraEntitiesResult,
                                           Map<ComponentCamera3D, Array<Entity>> use3DCameraEntitiesResult,
                                           Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCameras2DResult,
                                           Map<RenderTarget, Array<ComponentCamera3D>> renderTargetCameras3DResult) {
        prepareRenderingGroups(entities, lightingEnvironmentResult);
        prepare2DRenderingGroups(use2DCameraEntitiesResult);
        prepare3DRenderingGroups(use3DCameraEntitiesResult);
        prepareRenderTargetGroupsForCameras2D(renderTargetCameras2DResult);
        prepareRenderTargetGroupsForCameras3D(renderTargetCameras3DResult);
    }

    private static void prepareRenderingGroups(final Array<Entity> entities, LightingEnvironment lightingEnvironmentResult) {
        cameras2D.clear();
        cameras3D.clear();
        rendered2DEntities.clear();
        rendered3DEntities.clear();
        lightingEnvironmentResult.clearLights();
        for (Entity entity : entities) {
            if ((entity.componentsBitMask & ComponentType.ANIMATIONS_2D.bitMask) > 0) {
                ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
                rendered2DEntities.add(entity);
            }
            if ((entity.componentsBitMask & ComponentType.MODEL_INSTANCE.bitMask) > 0) {
                rendered3DEntities.add(entity);
            }
            if ((entity.componentsBitMask & ComponentType.LIGHT_3D.bitMask) > 0) {
                ComponentTransform3D transform = (ComponentTransform3D) entity.components[ComponentType.TRANSFORM_3D.ordinal()];
                Light light = (Light) entity.components[ComponentType.LIGHT_3D.ordinal()];
                UtilsLights.applyTransformToLight(transform, light);
                lightingEnvironmentResult.addLight(light);
            }
            if ((entity.componentsBitMask & ComponentType.CAMERA_2D.bitMask) > 0) {
                // TODO: test
                final ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentCamera2D camera2D = (ComponentCamera2D) entity.components[ComponentType.CAMERA_2D.ordinal()];
                syncTransform(transform2D, camera2D);
                cameras2D.add(camera2D);
            }
            if ((entity.componentsBitMask & ComponentType.CAMERA_3D.bitMask) > 0) {
                // TODO: test
                final ComponentTransform3D transform3D = (ComponentTransform3D) entity.components[ComponentType.TRANSFORM_3D.ordinal()];
                final ComponentCamera3D camera3D = (ComponentCamera3D) entity.components[ComponentType.CAMERA_3D.ordinal()];
                syncTransform(transform3D, camera3D);
                cameras3D.add(camera3D);
            }
        }
    }

    private static void prepare2DRenderingGroups(Map<ComponentCamera2D, Array<Entity>> use2DCameraEntitiesResult) {
        use2DCameraEntitiesResult.clear();
        for (ComponentCamera2D componentCamera : cameras2D) {
            Array<Entity> entitiesRenderedWithCamera = use2DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use2DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            }
            for (Entity entity : rendered2DEntities) {
                if (!((entity.layer & componentCamera.layersBitMask) > 0)) continue;
                if (RendererUtils.cull(entity, componentCamera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
            entitiesRenderedWithCamera.sort(rendering2DComparator);
        }
    }

    private static void prepare3DRenderingGroups(Map<ComponentCamera3D, Array<Entity>> use3DCameraEntitiesResult) {
        use3DCameraEntitiesResult.clear();
        for (ComponentCamera3D componentCamera : cameras3D) {
            Array<Entity> entitiesRenderedWithCamera = use3DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use3DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            }
            for (Entity entity : rendered3DEntities) {
                if (!((entity.layer & componentCamera.layersBitMask) > 0)) continue;
                if (RendererUtils.cull(entity, componentCamera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
        }
    }

    private static void prepareRenderTargetGroupsForCameras2D(Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCameras2DResult) {
        renderTargetCameras2DResult.clear();
        // scan all 2D cameras for render targets
        for (ComponentCamera2D camera2D : cameras2D) {
            final RenderTarget renderTarget = camera2D.renderTarget;
            Array<ComponentCamera2D> camerasForRenderTarget = renderTargetCameras2DResult.get(renderTarget);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCameras2DResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera2D);
        }

    }

    private static void prepareRenderTargetGroupsForCameras3D(Map<RenderTarget, Array<ComponentCamera3D>> renderTargetCameras3DResult) {
        renderTargetCameras3DResult.clear();
        // scan all 3D cameras for render targets
        for (ComponentCamera3D camera3D : cameras3D) {
            final RenderTarget renderTarget = camera3D.renderTarget;
            Array<ComponentCamera3D> camerasForRenderTarget = renderTargetCameras3DResult.get(camera3D);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCameras3DResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera3D);
        }
    }

    private static void syncTransform(final ComponentTransform2D transform2D, final ComponentCamera2D camera) {
        final OrthographicCamera lens = camera.lens;
        lens.position.set(transform2D.getPosition(), 0);
        lens.update();
    }

    // TODO: test.
    private static void syncTransform(final ComponentTransform3D transform3D, final ComponentCamera3D camera) {
        final PerspectiveCamera lens = camera.lens;
        transform3D.getTranslation(lens.position);
        transform3D.getDirection(lens.direction);
        transform3D.getUp(lens.up);
        lens.update();
    }

    private static boolean cull(final Entity entity, final PerspectiveCamera lens) {
        final ComponentModelInstance component = (ComponentModelInstance) entity.components[ComponentType.MODEL_INSTANCE.ordinal()];
        final ModelInstance instance = component.instance;
        position.add(instance.center);
        return !lens.frustum.sphereInFrustum(position, instance.radius);
    }

    private static boolean cull(final Entity entity, final OrthographicCamera camera) {
        ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform2D.scaleX;
        final float height = atlasRegion.getRegionHeight() * transform2D.scaleY;
        final float boundingRadius = Math.max(width, height) * 2;
        return !camera.frustum.sphereInFrustum(transform2D.transform.getPosition().x, transform2D.transform.getPosition().y, 0, boundingRadius);
    }

}
