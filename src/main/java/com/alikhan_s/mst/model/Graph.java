package com.alikhan_s.mst.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Node> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph:\nNodes: ");
        nodes.forEach(n -> sb.append(n).append(" "));
        sb.append("\nEdges:\n");
        edges.forEach(e -> sb.append(e).append("\n"));
        return sb.toString();
    }
}
