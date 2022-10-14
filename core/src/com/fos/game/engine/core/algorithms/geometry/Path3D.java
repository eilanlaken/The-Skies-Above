package com.fos.game.engine.core.algorithms.geometry;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Path3D extends Array<Path3D.Path3DNode> {

    public class Path3DNode {
        public Vector3 position;
        public Vector3 direction;
    }

}
