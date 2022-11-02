package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

// TODO: consider transparency by sorting the Renderable(s) by their distance from the camera.
public class ModelInstanceSorter implements RenderableSorter, Comparator<Renderable> {

    @Override
    public void sort(Camera camera, Array<Renderable> renderables) {
        renderables.sort(this);
    }

    @Override
    public int compare(Renderable o1, Renderable o2) {

        return 0;
    }

}
