package com.alikhan_s.mst.ds;

/**
 * Represents an edge in the graph.
 * Each edge connects two nodes (from → to) and has a weight.
 * Implements Comparable to allow sorting by weight (used in Kruskal's algorithm).
 */
public class Edge implements Comparable<Edge> {

    public String from;
    public String to;
    public double weight;

    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("Edge(%s -> %s, weight=%.2f)", from, to, weight);
    }
}
