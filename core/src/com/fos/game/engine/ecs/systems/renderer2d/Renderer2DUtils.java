package com.fos.game.engine.ecs.systems.renderer2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.ecs.components.animations2d.ComponentFrameAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.cameras_old.ComponentCamera2D;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.*;

public class Renderer2DUtils {

    public static String CAMERAS_ERROR_DIFFERENT_CAMERAS_MUST_RENDER_DIFFERENT_LAYERS =
            "Error: camera conflict - you are using multiple " + ComponentCamera2D.class.getSimpleName() + "(s) " +
            "to render the same category(s). Different " + ComponentCamera2D.class.getSimpleName() + "(s) " +
            " must render different categories.";

    private static Set<Enum> currentRenderedLayers = new HashSet<>();

    protected static final int ATTACHED_GRAPHICS_2D_COMPONENT =
            ComponentType.GRAPHICS.bitMask
            | ComponentType.GRAPHICS.bitMask
            | ComponentType.CAMERA_2D.bitMask;

    private static Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCamerasResult = new HashMap<>();
    private static Map<ComponentCamera2D, Array<Entity>> cameraEntitiesMapResult = new HashMap<>();

    // TODO: change back to protected
    protected static final Comparator<Entity> entitiesComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float z1 = ((ComponentTransform2D) e1.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            final float z2 = ((ComponentTransform2D) e2.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            int depthSort = Float.compare(z1, z2);
            if (depthSort != 0) return depthSort;

            Component animations1 = (Component) e1.components[ComponentType.GRAPHICS.ordinal()];
            Component light1 = (Component) e1.components[ComponentType.GRAPHICS.ordinal()];
            Component animations2 = (Component) e2.components[ComponentType.GRAPHICS.ordinal()];
            Component light2 = (Component) e2.components[ComponentType.GRAPHICS.ordinal()];
            if (light1 == null && animations1 != null && light2 == null & animations2 != null) return 0; // expected to be the most frequent case (usually)
            if (light1 != null && animations1 == null && light2 == null & animations2 == null) return 1;
            if (light1 != null && animations1 != null && light2 == null & animations2 == null) return 1;
            if (light1 == null && animations1 == null && light2 != null & animations2 == null) return -1;
            if (light1 == null && animations1 != null && light2 != null & animations2 == null) return -1;
            if (light1 != null && animations1 != null && light2 != null & animations2 == null) return -1;
            if (light1 != null && animations1 == null && light2 == null & animations2 != null) return 1;
            if (light1 != null && animations1 != null && light2 == null & animations2 != null) return 1;
            if (light1 == null && animations1 == null && light2 != null & animations2 != null) return -1;
            if (light1 != null && animations1 == null && light2 != null & animations2 != null) return 1;
            if (light1 == null && animations1 != null && light2 != null & animations2 != null) return -1;

            return Float.compare(e1.category, e2.category);
        }
    };

    protected static final Comparator<ComponentCamera2D> camerasComparator = new Comparator<ComponentCamera2D>() {
        @Override
        public int compare(ComponentCamera2D c1, ComponentCamera2D c2) {
            return Float.compare(c1.depth, c2.depth);
        }
    };

    protected static void applyTransform(final ComponentTransform2D transform2D, final ComponentCamera2D camera) {
        final OrthographicCamera lens = camera.lens;
        lens.position.set(transform2D.getPosition(), 0);
        lens.update();
    }

    protected static void applyTransform(final ComponentTransform2D transform2D, final ComponentLight2D light2D) {
        light2D.box2DLight.setPosition(transform2D.getPosition().x, transform2D.getPosition().y);
        light2D.box2DLight.setDirection(transform2D.getRotation());
    }

    /**
    this method will build a map of all the render targets and the cameras_old rendering to these targets:
    {
        renderTarget1: cameraA, cameraB;
        renderTarget2: cameraA;
        null (screen): cameraB
    }
     */
    protected static Map<RenderTarget, Array<ComponentCamera2D>> getRenderTargetCamerasMap(final Array<ComponentCamera2D> cameras) {
        renderTargetCamerasResult.clear();
        // scan all 2D cameras_old for render targets
        for (ComponentCamera2D camera2D : cameras) {
            final RenderTarget renderTarget = camera2D.renderTarget;
            Array<ComponentCamera2D> camerasForRenderTarget = renderTargetCamerasResult.get(renderTarget);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCamerasResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera2D);
        }
        return renderTargetCamerasResult;
    }

    protected static Map<ComponentCamera2D, Array<Entity>> getCameraEntitiesMap(final Array<ComponentCamera2D> cameras, final Array<Entity> entities) {
        cameraEntitiesMapResult.clear();
        for (ComponentCamera2D camera : cameras) {
            Array<Entity> entitiesRenderedWithCamera = cameraEntitiesMapResult.get(camera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                cameraEntitiesMapResult.put(camera, entitiesRenderedWithCamera);
            }
            for (Entity entity : entities) {
                if (!((entity.category & camera.layersBitMask) > 0)) continue;
                if (Renderer2DUtils.cull(entity, camera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
            entitiesRenderedWithCamera.sort(entitiesComparator);
        }
        return cameraEntitiesMapResult;
    }

    // TODO: remove
    protected static RuntimeException checkForCamerasErrors(final Array<ComponentCamera2D> cameras) {
        currentRenderedLayers.clear();
        for (final ComponentCamera2D camera : cameras) {
            for (final Enum layer : camera.layers) {
                if (currentRenderedLayers.contains(layer)) return new RuntimeException(CAMERAS_ERROR_DIFFERENT_CAMERAS_MUST_RENDER_DIFFERENT_LAYERS);
                else currentRenderedLayers.add(layer);
            }
        }
        return null;
    }

    private static boolean cull(final Entity entity, final OrthographicCamera camera) {
        ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentFrameAnimations2D animation = (ComponentFrameAnimations2D) entity.components[ComponentType.GRAPHICS.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform2D.scaleX;
        final float height = atlasRegion.getRegionHeight() * transform2D.scaleY;
        final float boundingRadius = Math.max(width, height) * 2;
        return !camera.frustum.sphereInFrustum(transform2D.transform.getPosition().x, transform2D.transform.getPosition().y, 0, boundingRadius);
    }

}
