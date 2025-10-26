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

    // Optional: indexes (used in tests or internal mapping)
    private int fromIndex;
    private int toIndex;

    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(String from, String to, double weight, int fromIndex, int toIndex) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public double getWeight() { return weight; }

    public int getFromIndex() { return fromIndex; }
    public int getToIndex() { return toIndex; }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("Edge(%s -> %s, weight=%.2f)", from, to, weight);
    }
}
