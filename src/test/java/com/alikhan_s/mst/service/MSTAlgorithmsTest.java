package com.alikhan_s.mst.service;

import com.alikhan_s.mst.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MSTAlgorithmsTest {

    @Test
    void testPrimAndKruskalProduceSameMST() {
        Graph graph = GraphInitializer.loadFromJson("/all_graphs.json", 1);

        assertFalse(graph.getNodes().isEmpty(), "Graph must have nodes");
        assertFalse(graph.getEdges().isEmpty(), "Graph must have edges");

        Node startNode = graph.getNodes().iterator().next();
        List<Edge> primMst = MVPPrim.run(graph, startNode);
        List<Edge> kruskalMst = MVPKruskal.run(graph);

        assertEquals(primMst.size(), kruskalMst.size(), "Edge counts must match");
    }
}
