package com.fos.game.engine.renderer.system_old;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.components.lights.ComponentLight;
import com.fos.game.engine.components.lights.LightingEnvironment;
import com.fos.game.engine.components.lights.UtilsLights;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.components.transform.ComponentTransform3D;
import com.fos.game.engine.entities.Entity;

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

    public static void prepareForRendering(final Array<Entity> entities,
                                           LightingEnvironment lightingEnvironmentResult,
                                           Map<ComponentCamera, Array<Entity>> use2DCameraEntitiesResult,
                                           Map<ComponentCamera, Array<Entity>> use3DCameraEntitiesResult) {
        splitIntoRenderingGroups(entities, lightingEnvironmentResult);
        prepare2DRenderingGroups(use2DCameraEntitiesResult);
        prepare3DRenderingGroups(use3DCameraEntitiesResult);
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
                ComponentLight light = (ComponentLight) entity.components[ComponentType.LIGHT.ordinal()];
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
        for (ComponentCamera componentCamera : cameras2D) {
            Array<Entity> entitiesRenderedWithCamera = use2DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use2DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            } else {
                entitiesRenderedWithCamera.clear();
            }
            for (Entity entity : rendered2DEntities) {
                if ((entity.layer & componentCamera.layersBitMask) > 0) entitiesRenderedWithCamera.add(entity);
            }
        }
    }

    private static void prepare3DRenderingGroups(Map<ComponentCamera, Array<Entity>> use3DCameraEntitiesResult) {
        for (ComponentCamera componentCamera : cameras3D) {
            Array<Entity> entitiesRenderedWithCamera = use3DCameraEntitiesResult.get(componentCamera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                use3DCameraEntitiesResult.put(componentCamera, entitiesRenderedWithCamera);
            } else {
                entitiesRenderedWithCamera.clear();
            }
            for (Entity entity : rendered3DEntities) {
                if ((entity.layer & componentCamera.layersBitMask) > 0) entitiesRenderedWithCamera.add(entity);
            }
        }
    }

    public static boolean doFrustumCulling(final ModelInstance modelInstance, final Camera camera) {
        modelInstance.transform.getTranslation(position);
        position.add(modelInstance.center);
        return !camera.frustum.sphereInFrustum(position, modelInstance.radius);
    }

}
