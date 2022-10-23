package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.cameras_old.ComponentCamera2D;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.*;

public class RendererUtils {

    protected static final int ATTACHED_GRAPHICS_2D_COMPONENT =
            ComponentType.ANIMATIONS_2D.bitMask
                    | ComponentType.LIGHT_2D.bitMask
                    | ComponentType.CAMERA.bitMask;

    private static Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasResult = new HashMap<>();
    private static Map<ComponentCamera, Array<Entity>> cameraEntitiesMapResult = new HashMap<>();

    protected static final Comparator<Entity> entitiesComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float z1 = ((ComponentTransform) e1.components[ComponentType.TRANSFORM.ordinal()]).matrix.val[Matrix4.M23];
            final float z2 = ((ComponentTransform) e2.components[ComponentType.TRANSFORM.ordinal()]).matrix.val[Matrix4.M23];
            int depthSort = Float.compare(z1, z2);
            if (depthSort != 0) return depthSort;

            Component animations1 = (Component) e1.components[ComponentType.ANIMATIONS_2D.ordinal()];
            Component light1 = (Component) e1.components[ComponentType.LIGHT_2D.ordinal()];
            Component animations2 = (Component) e2.components[ComponentType.ANIMATIONS_2D.ordinal()];
            Component light2 = (Component) e2.components[ComponentType.LIGHT_2D.ordinal()];
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

            return Float.compare(e1.layer, e2.layer);
        }
    };

    protected static final Comparator<ComponentCamera> camerasComparator = new Comparator<ComponentCamera>() {
        @Override
        public int compare(ComponentCamera c1, ComponentCamera c2) {
            return Float.compare(c1.depth, c2.depth);
        }
    };

    // TODO: implement correctly
    protected static void applyTransform(final ComponentTransform transform, final ComponentCamera camera) {
        final Camera lens = camera.lens;
        lens.position.set(transform.getPoxitionX(), transform.getPositionY(), transform.getPositionZ());
        //lens.lookAt(transform.getDirection());
        lens.update();
    }

    protected static void applyTransform(final ComponentTransform transform, final ComponentLight2D light2D) {
        light2D.light.setPosition(transform.getPoxitionX(), transform.getPositionY());
        light2D.light.setDirection(transform.getRotationAroundAxis(0,0,1));
    }

    /**
     this method will build a map of all the render targets and the cameras_old rendering to these targets:
     {
     renderTarget1: cameraA, cameraB;
     renderTarget2: cameraA;
     null (screen): cameraB
     }
     */
    protected static Map<RenderTarget, Array<ComponentCamera>> getRenderTargetCamerasMap(final Array<ComponentCamera> cameras) {
        renderTargetCamerasResult.clear();
        // scan all 2D cameras_old for render targets
        for (ComponentCamera camera : cameras) {
            final RenderTarget renderTarget = camera.renderTarget;
            Array<ComponentCamera> camerasForRenderTarget = renderTargetCamerasResult.get(renderTarget);
            if (camerasForRenderTarget == null) {
                camerasForRenderTarget = new Array<>();
                renderTargetCamerasResult.put(renderTarget, camerasForRenderTarget);
            }
            camerasForRenderTarget.add(camera);
        }
        return renderTargetCamerasResult;
    }

    protected static Map<ComponentCamera, Array<Entity>> getCameraEntitiesMap(final Array<ComponentCamera> cameras, final Array<Entity> entities) {
        cameraEntitiesMapResult.clear();
        for (ComponentCamera camera : cameras) {
            Array<Entity> entitiesRenderedWithCamera = cameraEntitiesMapResult.get(camera);
            if (entitiesRenderedWithCamera == null) {
                entitiesRenderedWithCamera = new Array<>();
                cameraEntitiesMapResult.put(camera, entitiesRenderedWithCamera);
            }
            for (Entity entity : entities) {
                if (!((entity.layer & camera.layersBitMask) > 0)) continue;
                if (cull(entity, camera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
            entitiesRenderedWithCamera.sort(entitiesComparator);
        }
        return cameraEntitiesMapResult;
    }

    private static boolean cull(final Entity entity, final Camera camera) {
        ComponentTransform transform = (ComponentTransform) entity.components[ComponentType.TRANSFORM.ordinal()];
        ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform.matrix.getScaleX();
        final float height = atlasRegion.getRegionHeight() * transform.matrix.getScaleY();
        final float boundingRadius = Math.max(width, height) * 2 * animation.size;
        return !camera.frustum.sphereInFrustum(transform.getPoxitionX(), transform.getPositionY(), 0, boundingRadius);
    }

}
