package com.fos.game.engine.systems.renderer.base;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.components.lights.Light;
import com.fos.game.engine.components.lights.LightingEnvironment;
import com.fos.game.engine.components.lights.UtilsLights;
import com.fos.game.engine.components.modelinstance.ComponentModelInstance;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.components.transform.ComponentTransform2D;
import com.fos.game.engine.components.transform.ComponentTransform3D;
import com.fos.game.engine.entities.Entity;

import java.util.Comparator;
import java.util.Map;

public class RenderingUtils {

    private static final short ENTITY_2D_BIT_MASK = ComponentType.ANIMATION.bitMask;
    private static final short ENTITY_3D_BIT_MASK = ComponentType.MODEL_INSTANCE.bitMask;
    private static final short ATTACHED_LIGHT_BIT_MASK = ComponentType.LIGHT.bitMask;
    private static final short ATTACHED_CAMERA_BIT_MASK = ComponentType.CAMERA.bitMask;

    private static final Vector3 position = new Vector3();
    private static final Array<Entity> rendered2DEntities = new Array<>();
    private static final Array<Entity> rendered3DEntities = new Array<>();
    private static final Array<ComponentCamera> cameras2D = new Array<>();
    private static final Array<ComponentCamera> cameras3D = new Array<>();

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
                                           Map<ComponentCamera, Array<Entity>> use2DCameraEntitiesResult,
                                           Map<ComponentCamera, Array<Entity>> use3DCameraEntitiesResult,
                                           Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasResult) {
        splitIntoRenderingGroups(entities, lightingEnvironmentResult);
        prepare2DRenderingGroups(use2DCameraEntitiesResult);
        prepare3DRenderingGroups(use3DCameraEntitiesResult);
        prepareRenderTargetGroups(renderTargetCamerasResult);
    }

    private static void splitIntoRenderingGroups(final Array<Entity> entities, LightingEnvironment lightingEnvironmentResult) {
        cameras2D.clear();
        cameras3D.clear();
        rendered2DEntities.clear();
        rendered3DEntities.clear();
        lightingEnvironmentResult.clearLights();
        for (Entity entity : entities) {
            if ((entity.componentsBitMask & ENTITY_2D_BIT_MASK) > 0) {
                rendered2DEntities.add(entity);
            }
            if ((entity.componentsBitMask & ENTITY_3D_BIT_MASK) > 0) {
                rendered3DEntities.add(entity);
            }
            if ((entity.componentsBitMask & ATTACHED_LIGHT_BIT_MASK) > 0) {
                ComponentTransform3D transform = (ComponentTransform3D) entity.components[ComponentType.TRANSFORM_3D.ordinal()];
                Light light = (Light) entity.components[ComponentType.LIGHT.ordinal()];
                UtilsLights.applyTransformToLight(transform, light);
                lightingEnvironmentResult.addLight(light);
            }
            if ((entity.componentsBitMask & ATTACHED_CAMERA_BIT_MASK) > 0) {
                final ComponentCamera componentCamera = (ComponentCamera) entity.components[ComponentType.CAMERA.ordinal()];
                final Camera camera = componentCamera.lens;
                if (camera instanceof OrthographicCamera) cameras2D.add(componentCamera);
                if (camera instanceof PerspectiveCamera) cameras3D.add(componentCamera);
            }
        }
    }

    private static void prepare2DRenderingGroups(Map<ComponentCamera, Array<Entity>> use2DCameraEntitiesResult) {
        use2DCameraEntitiesResult.clear();
        for (ComponentCamera componentCamera : cameras2D) {
            Array<Entity> entitiesRenderedWithCamera = use2DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use2DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            }
            for (Entity entity : rendered2DEntities) {
                if (!((entity.layer & componentCamera.layersBitMask) > 0)) continue;
                if (RenderingUtils.cull(entity, (OrthographicCamera) componentCamera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
            entitiesRenderedWithCamera.sort(rendering2DComparator);
        }
    }

    private static void prepare3DRenderingGroups(Map<ComponentCamera, Array<Entity>> use3DCameraEntitiesResult) {
        use3DCameraEntitiesResult.clear();
        for (ComponentCamera componentCamera : cameras3D) {
            Array<Entity> entitiesRenderedWithCamera = use3DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use3DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            }
            for (Entity entity : rendered3DEntities) {
                if (!((entity.layer & componentCamera.layersBitMask) > 0)) continue;
                if (RenderingUtils.cull(entity, (PerspectiveCamera) componentCamera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
        }
    }

    private static void prepareRenderTargetGroups(Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasResult) {
        renderTargetCamerasResult.clear();
        // scan all 2D cameras for render targets
        for (ComponentCamera camera2D : cameras2D) {
            final RenderTarget renderTarget = camera2D.renderTarget;
            Array<ComponentCamera> camerasForRenderTarget = renderTargetCamerasResult.get(renderTarget);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCamerasResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera2D);
        }
        // scan all 3D cameras for render targets
        for (ComponentCamera camera3D : cameras3D) {
            final RenderTarget renderTarget = camera3D.renderTarget;
            Array<ComponentCamera> camerasForRenderTarget = renderTargetCamerasResult.get(camera3D);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCamerasResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera3D);
        }
    }

    private static boolean cull(final Entity entity, final PerspectiveCamera lens) {
        final ComponentModelInstance component = (ComponentModelInstance) entity.components[ComponentType.MODEL_INSTANCE.ordinal()];
        final ModelInstance instance = component.instance;
        position.add(instance.center);
        return !lens.frustum.sphereInFrustum(position, instance.radius);
    }

    private static boolean cull(final Entity entity, final OrthographicCamera camera) {
        ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATION.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform2D.scaleX;
        final float height = atlasRegion.getRegionHeight() * transform2D.scaleY;
        final float boundingRadius = Math.max(width, height) * 2;
        return !camera.frustum.sphereInFrustum(transform2D.transform.getPosition().x, transform2D.transform.getPosition().y, 0, boundingRadius);
    }

}