package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.modelinstance_old.ModelInstance;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.UseTextureMaterial;

import java.util.Comparator;

// TODO: consider transparency by sorting the Renderable(s) by their distance from the camera.
public class ModelInstanceSorter implements RenderableSorter, Comparator<Renderable> {

    @Override
    public void sort(Camera camera, Array<Renderable> renderables) {
        renderables.sort(this);
    }

    @Override
    public int compare(Renderable o1, Renderable o2) {
        final ModelInstance modelInstance1 = (ModelInstance) o1.userData;
        final ModelInstance modelInstance2 = (ModelInstance) o2.userData;
        final Material material1 = modelInstance1.getMaterial(o1);
        final Material material2 = modelInstance2.getMaterial(o2);
        int compareMaterials = material1.compareTo(material2);

        // minimize shader switching
        if (compareMaterials != 0) {
            return compareMaterials;
        }
        // minimize texture bindings
        if (material1 instanceof UseTextureMaterial && material2 instanceof UseTextureMaterial) {
            int glTextureHandle_o1 = ((UseTextureMaterial) material1).getGLTextureHandle();
            int glTextureHandle_o2 = ((UseTextureMaterial) material2).getGLTextureHandle();
            if (glTextureHandle_o1 > glTextureHandle_o2) compareMaterials = 1;
            if (glTextureHandle_o2 > glTextureHandle_o1) compareMaterials = -1;
            return compareMaterials;
        }
        return 0;
    }

}
