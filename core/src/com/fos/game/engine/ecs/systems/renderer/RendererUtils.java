package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.spine.Skeleton;
import com.fos.game.engine.ecs.components.rendered2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.shape2d.ComponentShapes2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RendererUtils {

    protected static final int RENDERER_ENTITY =
            ComponentType.GRAPHICS.bitMask;

    private static Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasResult = new HashMap<>();
    private static Map<ComponentCamera, Array<Entity>> cameraEntitiesMapResult = new HashMap<>();
    private static Vector2 offset = new Vector2();
    private static Vector2 bounds = new Vector2();
    private static FloatArray floatArray = new FloatArray();

    // comparators
    protected static final Comparator<Entity> entitiesComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float z1 = ((ComponentTransform2D) e1.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            final float z2 = ((ComponentTransform2D) e2.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            return Float.compare(z1, z2);
        }
    };

    protected static final Comparator<ComponentCamera> camerasComparator = new Comparator<ComponentCamera>() {
        @Override
        public int compare(ComponentCamera c1, ComponentCamera c2) {
            return Float.compare(c1.depth, c2.depth);
        }
    };

    // TODO: test.
    protected static void applyTransform(final ComponentTransform2D transform, final ComponentCamera camera) {
        final Camera lens = camera.lens;
        lens.position.set(transform.worldX, transform.worldY, lens.position.z);
        //lens.rotate(transform.rotation);
        lens.update();
    }

    // TODO: test
    protected static void applyTransform(final ComponentTransform2D transform, final ComponentLight2D light2D) {
        light2D.box2DLight.setPosition(transform.worldX, transform.worldY);
        light2D.box2DLight.setDirection(transform.worldAngle);
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
                if (!((entity.category & camera.layersBitMask) > 0)) continue;
                if (cull(entity, camera.lens)) continue;
                entitiesRenderedWithCamera.add(entity);
            }
            entitiesRenderedWithCamera.sort(entitiesComparator);
        }
        return cameraEntitiesMapResult;
    }

    private static boolean cull(final Entity entity, final Camera camera) {
        Object graphics = entity.components[ComponentType.GRAPHICS.ordinal()];
        if (graphics instanceof ComponentShapes2D) return false;
        ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.GRAPHICS.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform.worldScaleY;
        final float height = atlasRegion.getRegionHeight() * transform.worldScaleY;
        final float boundingRadius = Math.max(width, height) * 2 * animation.size;
        return !camera.frustum.sphereInFrustum(transform.worldX, transform.worldY, 0, boundingRadius);
    }

    /** culling for skeletons */
    private static boolean cull(Skeleton skeleton, Camera camera) {
        floatArray.clear();
        skeleton.getBounds(offset, bounds, floatArray);
        float x = skeleton.getRootBone().getWorldX();
        float y = skeleton.getRootBone().getWorldY();
        float boundingRadius = Math.max(bounds.x, bounds.y) * 2;
        return !camera.frustum.sphereInFrustum(x, y, 0, boundingRadius);
    }

}
