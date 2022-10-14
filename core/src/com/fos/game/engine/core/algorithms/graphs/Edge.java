package com.fos.game.engine.core.algorithms.graphs;

public class Edge {
        
        public final Object a;
        public final Object b;
        public final boolean bidirectional;
        public float weight;
        
        public Edge(final Object a, final Object b, final boolean bidirectional, final float weight) {
            this.a = a;
            this.b = b;
            this.bidirectional = bidirectional;
            this.weight = weight;
        }
        
        public Edge(final Object a, final Object b, final boolean bidirectional) {
            this(a, b, bidirectional,1);
        }

        public Edge(final Object a, final Object b) {
            this(a, b, true,1);
        }
    }