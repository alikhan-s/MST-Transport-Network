package com.alikhan_s.mst;

import com.alikhan_s.mst.algorithms.KruskalAlgorithm;
import com.alikhan_s.mst.algorithms.PrimAlgorithm;
import com.alikhan_s.mst.ds.Graph;
import com.alikhan_s.mst.io.GraphLoader;
import com.alikhan_s.mst.io.ResultWriter;
import com.alikhan_s.mst.models.MSTResult;
import com.alikhan_s.mst.models.OutputResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for MST experiments.
 * <p>
 * Responsibilities:
 * - Load multiple graphs from JSON file(s).
 * - Run Prim’s and Kruskal’s algorithms on each graph.
 * - Collect and compare their metrics (cost, operations, execution time).
 * - Save final results into output.json (and optionally CSV later).
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Minimum Spanning Tree (MST) Experiment Runner ===");

        // Path to input graphs JSON file
        String inputPath = "data/all_graphs.json";
        String outputPath = "data/output.json";

        try {
            // Load all graphs from the JSON file
            List<Graph> graphs = GraphLoader.loadGraphs(inputPath);
            System.out.println("Loaded " + graphs.size() + " graphs from: " + inputPath);

            List<OutputResult> results = new ArrayList<>();
            int graphId = 1;

            // Iterate through all graphs and process them
            for (Graph graph : graphs) {
                System.out.println("\n--- Processing Graph " + graphId + " ---");
                System.out.println(graph);

                // Run Prim's algorithm
                MSTResult primResult = PrimAlgorithm.findMST(graph);
                System.out.println("Prim Algorithm:    " + primResult);

                // Run Kruskal's algorithm
                MSTResult kruskalResult = KruskalAlgorithm.findMST(graph);
                System.out.println("Kruskal Algorithm: " + kruskalResult);

                // Store results for output
                OutputResult output = new OutputResult(
                        String.valueOf(graphId),
                        graph.getV(),
                        graph.getE(),
                        primResult,
                        kruskalResult
                );
                results.add(output);

                graphId++;
            }

            // Write all results to JSON file
            ResultWriter.writeResults(results, outputPath);
            System.out.println("\n[0] All results successfully written to " + outputPath);

        } catch (Exception e) {
            System.err.println("[!] Error occurred while running MST experiments:");
            e.printStackTrace();
        }

        System.out.println("\n=== Experiment Completed ===");
    }
}
