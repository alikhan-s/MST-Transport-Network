package com.alikhan_s.mst;

import com.alikhan_s.mst.algorithms.*;
import com.alikhan_s.mst.ds.*;
import com.alikhan_s.mst.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Performance and scalability tests for MST algorithms.
 */
public class PerformanceTests {

    private Graph generateLargeGraph(int vertices, int edges) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < vertices; i++) nodes.add("N" + i);

        Random rand = new Random(42);
        Set<String> uniqueEdges = new HashSet<>();
        List<Edge> edgeList = new ArrayList<>();

        while (edgeList.size() < edges) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u != v) {
                String key = u + "-" + v;
                if (!uniqueEdges.contains(key)) {
                    uniqueEdges.add(key);
                    edgeList.add(new Edge("N" + u, "N" + v, 1 + rand.nextDouble() * 100));
                }
            }
        }

        return new Graph(nodes, edgeList);
    }

    @Test
    public void testMetricsArePositive() {
        Graph graph = generateLargeGraph(100, 300);

        MSTResult kruskal = KruskalAlgorithm.findMST(graph);
        MSTResult prim = PrimAlgorithm.findMST(graph);

        assertTrue(kruskal.getExecutionTimeMs() > 0);
        assertTrue(prim.getExecutionTimeMs() > 0);
        assertTrue(kruskal.getOperationsCount() > 0);
        assertTrue(prim.getOperationsCount() > 0);
    }

    @Test
    public void testScalability() {
        Graph graph = generateLargeGraph(1000, 5000);

        MSTResult kruskal = KruskalAlgorithm.findMST(graph);
        MSTResult prim = PrimAlgorithm.findMST(graph);

        assertTrue(kruskal.getExecutionTimeMs() < 1000, "Kruskal should finish under 1s for medium graph");
        assertTrue(prim.getExecutionTimeMs() < 1000, "Prim should finish under 1s for medium graph");
    }
}
