package com.alikhan_s.mst.io;

import com.alikhan_s.mst.ds.Edge;
import com.alikhan_s.mst.ds.Graph;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Loads graph definitions from a JSON file and returns a list of Graph objects.
 * Expected JSON structure (example):
 *
 * [
 *   {
 *     "graph_id": "g1",
 *     "nodes": ["A", "B", "C"],
 *     "edges": [
 *       {"from": "A", "to": "B", "weight": 4.5},
 *       {"from": "B", "to": "C", "weight": 2.1}
 *     ]
 *   },
 *   ...
 * ]
 */
public class GraphLoader {

    public static List<Graph> loadGraphs(String filePath) {
        List<Graph> graphs = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();

                // Parse nodes
                List<String> nodes = new ArrayList<>();
                JsonArray nodesJson = obj.getAsJsonArray("nodes");
                for (JsonElement node : nodesJson) {
                    nodes.add(node.getAsString());
                }

                // Parse edges
                List<Edge> edges = new ArrayList<>();
                JsonArray edgesJson = obj.getAsJsonArray("edges");
                for (JsonElement edgeElem : edgesJson) {
                    JsonObject e = edgeElem.getAsJsonObject();
                    String from = e.get("from").getAsString();
                    String to = e.get("to").getAsString();
                    double weight = e.get("weight").getAsDouble();
                    edges.add(new Edge(from, to, weight));
                }

                Graph graph = new Graph(nodes, edges);
                graphs.add(graph);
            }

        } catch (IOException e) {
            System.err.println("Error loading graphs from file: " + e.getMessage());
        }
        return graphs;
    }
}
