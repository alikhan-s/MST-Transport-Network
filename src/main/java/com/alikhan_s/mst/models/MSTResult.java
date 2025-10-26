package com.alikhan_s.mst.models;

import com.alikhan_s.mst.ds.Edge;
import java.util.List;

/**
 * Data container for the result of an MST algorithm (Prim or Kruskal).
 * Includes the MST edges, total cost, number of operations, and execution time.
 */
public class MSTResult {
    public List<Edge> mstEdges;
    public double totalCost;
    public long operationsCount;
    public double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, double totalCost, long operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }

    @Override
    public String toString() {
        return String.format("MSTResult(totalCost=%.2f, operations=%d, time=%.3f ms)", totalCost, operationsCount, executionTimeMs);
    }
}
