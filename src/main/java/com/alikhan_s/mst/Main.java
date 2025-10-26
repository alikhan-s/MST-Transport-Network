package com.alikhan_s.mst;

import com.alikhan_s.mst.model.Graph;
import com.alikhan_s.mst.service.GraphInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Graph Initialization MVP ===");

        int graphId = 1;
        Graph graph = GraphInitializer.loadFromJson("/all_graphs.json", graphId);

        System.out.println(graph);
    }
}

