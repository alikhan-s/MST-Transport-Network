package com.alikhan_s.mst.algorithms;

import com.alikhan_s.mst.ds.DisjointSetUnion;
import com.alikhan_s.mst.ds.Edge;
import com.alikhan_s.mst.ds.Graph;
import com.alikhan_s.mst.models.MSTResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implements Kruskal's algorithm for Minimum Spanning Tree (MST).
 *
 * Metrics collected:
 * - total number of "operations" (a composite metric including sort comparisons,
 *   loop iterations, DSU find/union operations as reported by DSU),
 * - measured wall-clock execution time in milliseconds,
 * - MST edge list and total cost.
 *
 * Sorting comparisons are counted precisely by using a Comparator that increments
 * an {@link AtomicLong} for each comparison performed during sort.
 *
 * DSU internals (find/union) maintain their own counters (getFindOperations/getUnionOperations).
 * Kruskal sums newly observed DSU counters during runtime into the operations metric.
 *
 * Note: Graph uses String node names externally but internally maps nodes to integer indices.
 */
public class KruskalAlgorithm {

    /**
     * Find MST for the provided graph using Kruskal's algorithm.
     *
     * @param graph Graph object with nodes and edges.
     * @return MSTResult containing MST edges, total cost, operations count and execution time (ms).
     */
    public static MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();

        long operations = 0L;

        // Copy edges to avoid mutating original list and to be safe for sorting.
        List<Edge> allEdges = new ArrayList<>(graph.getAllEdges());

        // Atomic counter to record number of comparisons during sorting.
        AtomicLong sortComparisonCounter = new AtomicLong(0);

        // Sort edges by weight and count comparisons precisely.
        Collections.sort(allEdges, (e1, e2) -> {
            sortComparisonCounter.incrementAndGet();
            return Double.compare(e1.weight, e2.weight);
        });

        // Add sorting comparisons to operations metric.
        operations += sortComparisonCounter.get();

        // Prepare DSU for cycle detection.
        DisjointSetUnion dsu = new DisjointSetUnion(graph.getV());

        List<Edge> mst = new ArrayList<>();
        double totalCost = 0.0;

        // Iterate over sorted edges and pick edges that connect different components.
        for (Edge edge : allEdges) {
            operations++; // count this loop iteration

            // Translate node names to indices used by DSU/graph internals.
            int u = graph.getIndex(edge.from);
            int v = graph.getIndex(edge.to);

            // Capture DSU find operations before calls to compute delta later.
            long beforeFindOps = dsu.getFindOperations();

            int rootU = dsu.find(u);
            int rootV = dsu.find(v);

            // Add the newly occurred find operations to metrics.
            long addedFindOps = dsu.getFindOperations() - beforeFindOps;
            operations += addedFindOps;

            // If they belong to different components, include this edge.
            if (rootU != rootV) {
                mst.add(edge);
                totalCost += edge.weight;

                // Capture DSU union operations before union to compute added unions.
                long beforeUnionOps = dsu.getUnionOperations();
                dsu.union(rootU, rootV);
                long addedUnionOps = dsu.getUnionOperations() - beforeUnionOps;
                operations += addedUnionOps;

                // If MST is complete, we can break early: |mst| == V - 1
                if (mst.size() == graph.getV() - 1) {
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;

        // Compose result.
        MSTResult result = new MSTResult(mst, totalCost, timeMs, operations);
        return result;
    }
}
