package com.fos.game.engine.compute.geometry;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Path2D extends Array<Path2D.Path2DNode> {

    public class Path2DNode {
        public Vector2 position;
        public Vector2 direction;
    }

}
