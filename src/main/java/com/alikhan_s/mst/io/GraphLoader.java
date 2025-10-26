package com.alikhan_s.mst.io;

import com.alikhan_s.mst.ds.Edge;
import com.alikhan_s.mst.ds.Graph;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphLoader {

    public static List<Graph> loadGraphs(String filePath) {
        List<Graph> graphs = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphsArray = root.getAsJsonArray("graphs");

            if (graphsArray == null) {
                System.err.println("⚠️ No 'graphs' array found in JSON file!");
                return graphs;
            }

            for (JsonElement graphElem : graphsArray) {
                JsonObject graphObj = graphElem.getAsJsonObject();

                // Parse nodes
                List<String> nodes = new ArrayList<>();
                JsonArray nodesJson = graphObj.getAsJsonArray("nodes");
                for (JsonElement node : nodesJson) {
                    nodes.add(node.getAsString());
                }

                // Parse edges
                List<Edge> edges = new ArrayList<>();
                JsonArray edgesJson = graphObj.getAsJsonArray("edges");
                for (JsonElement edgeElem : edgesJson) {
                    JsonObject e = edgeElem.getAsJsonObject();
                    String from = e.get("from").getAsString();
                    String to = e.get("to").getAsString();
                    double weight = e.get("weight").getAsDouble();
                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(nodes, edges));
            }

        } catch (IOException e) {
            System.err.println("Error loading graphs: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Invalid JSON format: " + e.getMessage());
        }

        return graphs;
    }
}
