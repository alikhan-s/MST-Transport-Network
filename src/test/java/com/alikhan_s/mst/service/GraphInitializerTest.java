package com.alikhan_s.mst.service;

import com.alikhan_s.mst.model.Edge;
import com.alikhan_s.mst.model.Graph;
import com.alikhan_s.mst.model.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphInitializerTest {

    @Test
    public void testLoadGraph1() {
        Graph graph = GraphInitializer.loadFromJson("/all_graphs.json", 1);

        // Проверяем, что граф не пустой
        assertNotNull(graph, "Graph should not be null");
        assertFalse(graph.getNodes().isEmpty(), "Graph should contain nodes");
        assertFalse(graph.getEdges().isEmpty(), "Graph should contain edges");

        // Проверяем количество вершин
        assertEquals(5, graph.getNodes().size(), "Graph 1 should have 5 nodes");

        // Проверяем, что есть конкретное ребро 1 -> 2 (3)
        boolean edgeFound = graph.getEdges().stream()
                .anyMatch(e -> e.getFrom().getName().equals("1")
                        && e.getTo().getName().equals("2")
                        && e.getWeight() == 3);
        assertTrue(edgeFound, "Graph 1 should contain edge 1->2 (3)");
    }

    @Test
    public void testLoadGraph2() {
        Graph graph = GraphInitializer.loadFromJson("/all_graphs.json", 2);

        assertNotNull(graph);
        assertEquals(10, graph.getNodes().size(), "Graph 2 should have 10 nodes");
        assertTrue(graph.getEdges().size() > 0, "Graph 2 should have edges");

        // Проверяем наличие одного из рёбер
        boolean edgeFound = graph.getEdges().stream()
                .anyMatch(e -> e.getFrom().getName().equals("1")
                        && e.getTo().getName().equals("2")
                        && e.getWeight() == 28);
        assertTrue(edgeFound, "Graph 2 should contain edge 1->2 (28)");
    }
}
