package com.alikhan_s.mst.service;

import com.alikhan_s.mst.model.Edge;
import com.alikhan_s.mst.model.Graph;
import com.alikhan_s.mst.model.Node;

import java.util.*;

public class MVPKruskal {

    public static List<Edge> run(Graph graph) {
        List<Edge> mst = new ArrayList<>();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        Map<Node, Node> parent = new HashMap<>();
        for (Node node : graph.getNodes()) {
            parent.put(node, node);
        }

        for (Edge edge : edges) {
            Node root1 = find(parent, edge.getFrom());
            Node root2 = find(parent, edge.getTo());

            if (!root1.equals(root2)) {
                mst.add(edge);
                parent.put(root1, root2);
            }
        }

        return mst;
    }

    private static Node find(Map<Node, Node> parent, Node node) {
        while (!parent.get(node).equals(node)) {
            node = parent.get(node);
        }
        return node;
    }
}
