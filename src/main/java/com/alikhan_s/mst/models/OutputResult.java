package com.alikhan_s.mst.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the combined output of both Prim and Kruskal algorithms
 * for a single graph.
 */
public class OutputResult {

    @SerializedName("graph_id")
    private final String graphId;

    @SerializedName("vertices")
    private final int vertices;

    @SerializedName("edges")
    private final int edges;

    @SerializedName("prim_result")
    private final MSTResult primResult;

    @SerializedName("kruskal_result")
    private final MSTResult kruskalResult;

    public OutputResult(String graphId, int vertices, int edges,
                        MSTResult primResult, MSTResult kruskalResult) {
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
        this.primResult = primResult;
        this.kruskalResult = kruskalResult;
    }

    public String getGraphId() {
        return graphId;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public MSTResult getPrimResult() {
        return primResult;
    }

    public MSTResult getKruskalResult() {
        return kruskalResult;
    }
}
