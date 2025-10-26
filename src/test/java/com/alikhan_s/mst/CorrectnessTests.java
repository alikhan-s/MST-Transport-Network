package com.alikhan_s.mst;

import com.alikhan_s.mst.algorithms.*;
import com.alikhan_s.mst.ds.*;
import com.alikhan_s.mst.models.*;
import com.alikhan_s.mst.io.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Tests to verify correctness of Prim's and Kruskal's MST implementations.
 */
public class CorrectnessTests {

    /** Helper method to check connectivity of a graph built from given edges. */
    private boolean isGraphConnected(int V, List<Edge> edges) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < V; i++) adj.put(i, new ArrayList<>());

        // Convert edges to adjacency list
        for (Edge e : edges) {
            adj.get(e.getFromIndex()).add(e.getFromIndex());
            adj.get(e.getToIndex()).add(e.getFromIndex());
        }

        // BFS
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        visited[0] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                    count++;
                }
            }
        }

        return count == V;
    }

    @Test
    public void testCostsAreIdentical() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 4),
                new Edge("C", "D", 5)
        );

        Graph graph = new Graph(nodes, edges);

        MSTResult kruskal = KruskalAlgorithm.findMST(graph);
        MSTResult prim = PrimAlgorithm.findMST(graph);

        assertEquals(kruskal.getTotalCost(), prim.getTotalCost(), 1e-6,
                "Prim and Kruskal MST costs should be identical");
    }

    @Test
    public void testEdgeCount() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("A", "C", 4)
        );

        Graph graph = new Graph(nodes, edges);
        MSTResult prim = PrimAlgorithm.findMST(graph);

        assertEquals(graph.getV() - 1, prim.getMstEdges().size(),
                "MST should have exactly V-1 edges");
    }

    @Test
    public void testDisconnectedGraph() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("C", "D", 2)
        );

        Graph graph = new Graph(nodes, edges);
        MSTResult prim = PrimAlgorithm.findMST(graph);

        assertTrue(prim.getMstEdges().size() < graph.getV() - 1,
                "Disconnected graph should produce incomplete MST");
    }
}
