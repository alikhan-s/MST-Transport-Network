package com.alikhan_s.mst.ds;

import java.util.*;

/**
 * Represents a weighted undirected graph with both map-based and list-based adjacency structures.
 * Uses integer indices for internal processing to improve performance.
 */
public class Graph {
    private int V;
    private int E;
    private List<Edge> allEdges;
    private Map<String, Integer> nodeToIndex;
    private Map<Integer, List<Edge>> adj;
    private final List<List<Edge>> adjacencyList = new ArrayList<>();

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
            adjacencyList.add(new ArrayList<>()); // keep adjacencyList in sync
        }

        // Build adjacency structures (both map-based and list-based)
        for (Edge e : edges) {
            int u = nodeToIndex.get(e.from);
            int v = nodeToIndex.get(e.to);

            // Map-based adjacency for algorithms using Map<Integer, List<Edge>>
            adj.get(u).add(new Edge(e.from, e.to, e.weight));
            adj.get(v).add(new Edge(e.to, e.from, e.weight)); // undirected

            // List-based adjacency for algorithms using indexed access
            adjacencyList.get(u).add(new Edge(e.from, e.to, e.weight));
            adjacencyList.get(v).add(new Edge(e.to, e.from, e.weight));
        }
    }

    /** Returns the number of vertices in the graph. */
    public int getV() { return V; }

    /** Returns the number of edges in the graph. */
    public int getE() { return E; }

    /** Returns all edges in the graph. */
    public List<Edge> getAllEdges() { return allEdges; }

    /** Returns the index of a given node label. */
    public int getIndex(String node) { return nodeToIndex.get(node); }

    /** Returns adjacency list for a vertex by its index (Map-based). */
    public List<Edge> getAdj(int index) { return adj.get(index); }

    /** Returns the full adjacency list as a List<List<Edge>> (for Prim). */
    public List<List<Edge>> getAdjacencyList() { return adjacencyList; }

    @Override
    public String toString() {
        return String.format("Graph(V=%d, E=%d)", V, E);
    }
}
