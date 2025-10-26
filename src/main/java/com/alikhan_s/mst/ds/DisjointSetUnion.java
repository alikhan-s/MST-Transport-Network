package com.alikhan_s.mst.ds;

/**
 * Disjoint Set Union (Union-Find) data structure with path compression and union by rank.
 * Used in Kruskal's algorithm for cycle detection.
 */
public class DisjointSetUnion {

    private int[] parent;
    private int[] rank;
    private long findOperations;
    private long unionOperations;

    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        findOperations = 0;
        unionOperations = 0;
    }

    public int find(int i) {
        findOperations++;
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // Path compression
        }
        return parent[i];
    }

    public void union(int i, int j) {
        unionOperations++;
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI == rootJ) return;

        if (rank[rootI] < rank[rootJ]) {
            parent[rootI] = rootJ;
        } else if (rank[rootI] > rank[rootJ]) {
            parent[rootJ] = rootI;
        } else {
            parent[rootJ] = rootI;
            rank[rootI]++;
        }
    }

    public long getFindOperations() { return findOperations; }

    public long getUnionOperations() { return unionOperations; }
}
