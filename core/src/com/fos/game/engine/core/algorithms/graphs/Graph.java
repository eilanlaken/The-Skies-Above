package com.fos.game.engine.core.algorithms.graphs;

import com.badlogic.gdx.utils.Array;

public class Graph {

    private Array<Object> nodes;
    private Array<Edge> edges;

    public Graph() {
        this.nodes = new Array<>();
        this.edges = new Array<>();
    }

}
