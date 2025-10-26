package com.alikhan_s.mst.algorithms;

import com.alikhan_s.mst.ds.Edge;
import com.alikhan_s.mst.ds.Graph;
import com.alikhan_s.mst.models.MSTResult;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implements Prim's algorithm for computing the Minimum Spanning Tree (MST).
 *
 * Metrics tracked:
 *  - Total operations count (priority queue pushes/pops, edge relaxations, iterations)
 *  - Execution time (milliseconds)
 *  - MST edge list and total cost
 *
 * The algorithm assumes the graph is undirected and weighted.
 * It starts from an arbitrary vertex (index 0 by default) and uses
 * a min-priority queue (Java PriorityQueue) to pick the smallest-weight edge
 * connecting a visited and unvisited vertex.
 *
 * This implementation also handles disconnected graphs by restarting
 * Prim’s process for unvisited vertices (producing a forest).
 */
public class PrimAlgorithm {

    /**
     * Computes MST using Prim's algorithm with operation counting.
     *
     * @param graph The input Graph object.
     * @return MSTResult containing selected edges, total cost, operations, and execution time.
     */
    public static MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();
        long operations = 0L;

        int V = graph.getV();
        boolean[] visited = new boolean[V];
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0.0;

        // For tracking operation categories
        AtomicLong pqOperations = new AtomicLong(0);
        AtomicLong loopIterations = new AtomicLong(0);

        // Priority queue: stores edges by weight (ascending)
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));

        // If graph is disconnected, we run Prim's separately for each unvisited component
        for (int start = 0; start < V; start++) {
            if (!visited[start]) {
                // Begin from this component
                addAdjacentEdges(graph, start, pq, visited, pqOperations);

                while (!pq.isEmpty()) {
                    loopIterations.incrementAndGet();

                    // Pop the smallest edge
                    pqOperations.incrementAndGet(); // dequeue count
                    Edge minEdge = pq.poll();

                    int u = graph.getIndex(minEdge.from);
                    int v = graph.getIndex(minEdge.to);

                    // Skip edges connecting two already visited vertices
                    if (visited[u] && visited[v]) {
                        continue;
                    }

                    // Include this edge in MST
                    mstEdges.add(minEdge);
                    totalCost += minEdge.weight;

                    // Determine which vertex is newly visited and expand from it
                    int nextVertex = visited[u] ? v : u;
                    addAdjacentEdges(graph, nextVertex, pq, visited, pqOperations);
                }
            }
        }

        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;

        // Combine metrics
        operations += pqOperations.get() + loopIterations.get();

        return new MSTResult(mstEdges, totalCost, timeMs, operations);
    }

    /**
     * Adds all edges adjacent to a given vertex into the priority queue,
     * skipping those leading to already visited vertices.
     *
     * Each insertion counts as a single priority queue operation.
     *
     * @param graph Graph instance
     * @param vertexIndex Index of the vertex whose edges to explore
     * @param pq Priority queue to push edges into
     * @param visited Boolean visited array
     * @param pqOperations Counter for priority queue operations
     */
    private static void addAdjacentEdges(Graph graph, int vertexIndex, PriorityQueue<Edge> pq,
                                         boolean[] visited, AtomicLong pqOperations) {
        visited[vertexIndex] = true;

        // Retrieve neighbors from the graph’s adjacency structure
        for (Edge edge : graph.getAdjacencyList().get(vertexIndex)) {
            int neighborIndex = graph.getIndex(edge.to);

            if (!visited[neighborIndex]) {
                pq.add(edge);
                pqOperations.incrementAndGet(); // enqueue count
            }
        }
    }
}
