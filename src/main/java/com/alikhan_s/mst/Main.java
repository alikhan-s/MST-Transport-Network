package com.alikhan_s.mst;

import com.alikhan_s.mst.model.*;
import com.alikhan_s.mst.service.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Graph MST MVP ===");
        System.out.println("Enter graph id (1–28): ");

        int graphId;
        try {
            graphId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.err.println("[X] Incorrect input. Use graphId = 1");
            graphId = 1;
        }

        Graph graph = GraphInitializer.loadFromJson("/all_graphs.json", graphId);

        if (graph.getNodes().isEmpty()) {
            System.err.printf("[!] Graph with id=%d not found or empty.%n", graphId);
            return;
        }

        System.out.printf("[0] Graph loaded #%d: %d vertives, %d edges.%n%n",
                graphId, graph.getNodes().size(), graph.getEdges().size());

        Node start = graph.getNodes().iterator().next();

        System.out.println("=== MVP Prim Algorithm ===");
        long startTimePrim = System.nanoTime();
        List<Edge> primMST = MVPPrim.run(graph, start);
        long endTimePrim = System.nanoTime();
        printMST(primMST);
        System.out.printf("Time: %.3f ms%n%n", (endTimePrim - startTimePrim) / 1e6);

        System.out.println("=== MVP Kruskal Algorithm ===");
        long startTimeKruskal = System.nanoTime();
        List<Edge> kruskalMST = MVPKruskal.run(graph);
        long endTimeKruskal = System.nanoTime();
        printMST(kruskalMST);
        System.out.printf("Time: %.3f ms%n", (endTimeKruskal - startTimeKruskal) / 1e6);

        if (primMST.size() == kruskalMST.size()) {
            System.out.println("\n[0] Both algorithms produced the same number of MST edges.");
        } else {
            System.out.println("\n[!] Different numbers of edges!");
        }
    }

    private static void printMST(List<Edge> mst) {
        int totalWeight = 0;
        for (Edge e : mst) {
            System.out.printf("%s --(%d)--> %s%n", e.getFrom().getName(), e.getWeight(), e.getTo().getName());
            totalWeight += e.getWeight();
        }
        System.out.println("Total Weight: " + totalWeight);
    }
}
