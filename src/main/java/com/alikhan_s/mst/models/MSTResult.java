package com.alikhan_s.mst.models;

import com.alikhan_s.mst.ds.Edge;
import java.util.List;

/**
 * Data container for the result of an MST algorithm (Prim or Kruskal).
 * Includes the MST edges, total cost, number of operations, and execution time.
 */
public class MSTResult {
    private List<Edge> mstEdges;
    private double totalCost;
    private double executionTimeMs;
    private long operationsCount;

    public MSTResult(List<Edge> mstEdges, double totalCost, double executionTimeMs, long operationsCount) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.executionTimeMs = executionTimeMs;
        this.operationsCount = operationsCount;
    }

    public void setMstEdges(List<Edge> mstEdges) {
        this.mstEdges = mstEdges;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setExecutionTimeMs(double executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public void setOperationsCount(long operationsCount) {
        this.operationsCount = operationsCount;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    public long getOperationsCount() {
        return operationsCount;
    }

    @Override
    public String toString() {
        return String.format("MSTResult[cost=%.2f, edges=%d, time=%.3f ms, ops=%d]",
                totalCost, mstEdges.size(), executionTimeMs, operationsCount);
    }
}
