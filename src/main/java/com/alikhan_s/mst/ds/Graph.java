package com.alikhan_s.mst.ds;

import java.util.*;

/**
 * Represents a weighted undirected graph with an adjacency list.
 * Uses integer indices for internal processing to improve performance.
 */
public class Graph {
    private int V;
    private int E;
    private List<Edge> allEdges;
    private Map<String, Integer> nodeToIndex;
    private Map<Integer, List<Edge>> adj;

    public Graph(List<String> nodes, List<Edge> edges) {
        this.V = nodes.size();
        this.E = edges.size();
        this.allEdges = new ArrayList<>(edges);
        this.nodeToIndex = new HashMap<>();
        this.adj = new HashMap<>();

        // Map node names to indices
        for (int i = 0; i < nodes.size(); i++) {
            nodeToIndex.put(nodes.get(i), i);
            adj.put(i, new ArrayList<>());
        }

        // Build adjacency list
        for (Edge e : edges) {
            int u = nodeToIndex.get(e.from);
            int v = nodeToIndex.get(e.to);
            adj.get(u).add(new Edge(e.from, e.to, e.weight));
            adj.get(v).add(new Edge(e.to, e.from, e.weight)); // undirected
        }
    }

    public int getV() { return V; }

    public int getE() { return E; }

    public List<Edge> getAllEdges() { return allEdges; }

    public int getIndex(String node) { return nodeToIndex.get(node); }

    public List<Edge> getAdj(int index) { return adj.get(index); }

    @Override
    public String toString() {
        return String.format("Graph(V=%d, E=%d)", V, E);
    }
}
