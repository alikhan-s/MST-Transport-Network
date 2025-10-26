package com.alikhan_s.mst.service;

import com.alikhan_s.mst.model.Edge;
import com.alikhan_s.mst.model.Graph;
import com.alikhan_s.mst.model.Node;

import java.util.*;

public class MVPPrim {

    public static List<Edge> run(Graph graph, Node start) {
        List<Edge> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        visited.add(start);

        while (visited.size() < graph.getNodes().size()) {
            Edge minEdge = null;

            for (Edge e : graph.getEdges()) {
                boolean fromVisited = visited.contains(e.getFrom());
                boolean toVisited = visited.contains(e.getTo());

                if (fromVisited && !toVisited || !fromVisited && toVisited) {
                    if (minEdge == null || e.getWeight() < minEdge.getWeight()) {
                        minEdge = e;
                    }
                }
            }

            if (minEdge == null) break;

            mst.add(minEdge);
            visited.add(minEdge.getFrom());
            visited.add(minEdge.getTo());
        }

        return mst;
    }
}
