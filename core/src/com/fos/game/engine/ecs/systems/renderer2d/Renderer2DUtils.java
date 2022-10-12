package com.fos.game.engine.ecs.systems.renderer2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera2d.ComponentCamera2D;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.renderer.base.RenderTarget;

import java.util.*;

public class Renderer2DUtils {

    public static String CAMERAS_ERROR_DIFFERENT_CAMERAS_MUST_RENDER_DIFFERENT_LAYERS =
            "Error: camera conflict - you are using multiple " + ComponentCamera2D.class.getSimpleName() + "(s) " +
            "to render the same layer(s). Different " + ComponentCamera2D.class.getSimpleName() + "(s) " +
            " must render different layers.";

    private static String CAMERAS_ERROR_NO_CAMERAS_IN_CONTAINER = "Error: no " + ComponentCamera2D.class.getSimpleName() + "(s) " + " in container. " +
            "You must create at least 1 " + ComponentCamera2D.class.getSimpleName() + " in order to produce an image.";

    private static Set<Enum> currentRenderedLayers = new HashSet<>();

    protected static final int ATTACHED_GRAPHICS_2D_COMPONENT =
            ComponentType.ANIMATIONS_2D.bitMask
            | ComponentType.LIGHT_2D.bitMask
            | ComponentType.CAMERA_2D.bitMask;

    private static Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCamerasResult;

    // TODO: change back to protected
    public static final Comparator<Entity> rendering2DComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            int depthSort = Float.compare(e1.layer, e2.layer);
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

            return 0;
        }
    };

    protected static void applyTransform(final ComponentTransform2D transform2D, final ComponentCamera2D camera) {
        final OrthographicCamera lens = camera.lens;
        lens.position.set(transform2D.getPosition(), 0);
        lens.update();
    }

    protected static void applyTransform(final ComponentTransform2D transform2D, final ComponentLight2D light2D) {
        light2D.light.setPosition(transform2D.getPosition().x, transform2D.getPosition().y);
        light2D.light.setDirection(transform2D.getRotation());
    }

    protected static Map<RenderTarget, Array<ComponentCamera2D>> getRenderTargetCamerasMap(final Array<ComponentCamera2D> cameras) {
        if (renderTargetCamerasResult == null)  renderTargetCamerasResult = new HashMap<>();
        renderTargetCamerasResult.clear();
        // scan all 2D cameras for render targets
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

    protected static RuntimeException checkForCamerasErrors(final Array<ComponentCamera2D> cameras) {
        if (cameras == null || cameras.size <= 0) return new RuntimeException(CAMERAS_ERROR_NO_CAMERAS_IN_CONTAINER);
        currentRenderedLayers.clear();
        for (final ComponentCamera2D camera : cameras) {
            for (final Enum layer : camera.layers) {
                if (currentRenderedLayers.contains(layer)) return new RuntimeException(CAMERAS_ERROR_DIFFERENT_CAMERAS_MUST_RENDER_DIFFERENT_LAYERS);
                else currentRenderedLayers.add(layer);
            }
        }
        return null;
    }

    // TODO: use
    protected static boolean cull(final Entity entity, final OrthographicCamera camera) {
        ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
        TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
        final float width = atlasRegion.getRegionWidth() * transform2D.scaleX;
        final float height = atlasRegion.getRegionHeight() * transform2D.scaleY;
        final float boundingRadius = Math.max(width, height) * 2;
        return !camera.frustum.sphereInFrustum(transform2D.transform.getPosition().x, transform2D.transform.getPosition().y, 0, boundingRadius);
    }
}
