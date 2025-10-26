package com.alikhan_s.mst.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alikhan_s.mst.model.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GraphInitializer {

    /**
     * Загружает граф из JSON по id
     * @param resourcePath путь к JSON файлу
     * @param graphId id нужного графа
     * @return Graph объект
     */
    public static Graph loadFromJson(String resourcePath, int graphId) {
        Graph graph = new Graph();
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream input = GraphInitializer.class.getResourceAsStream(resourcePath)) {

            if (input == null) {
                System.err.println("⚠️ Resource not found: " + resourcePath);
                return graph;
            }

            JsonNode root = mapper.readTree(input);
            JsonNode graphsNode = root.get("graphs");

            if (graphsNode == null || !graphsNode.isArray()) {
                System.err.println("⚠️ Invalid JSON: 'graphs' array not found.");
                return graph;
            }

            JsonNode targetGraph = null;
            for (JsonNode g : graphsNode) {
                if (g.get("id").asInt() == graphId) {
                    targetGraph = g;
                    break;
                }
            }

            if (targetGraph == null) {
                System.err.println("⚠️ Graph with id=" + graphId + " not found in file.");
                return graph;
            }

            // Создаем вершины
            Map<String, Node> nodeMap = new HashMap<>();
            for (JsonNode nodeName : targetGraph.get("nodes")) {
                String nodeLabel = nodeName.asText();
                Node node = new Node(nodeLabel);
                nodeMap.put(nodeLabel, node);
                graph.addNode(node);
            }

            // Создаем рёбра
            for (JsonNode edgeNode : targetGraph.get("edges")) {
                String fromLabel = edgeNode.get("from").asText();
                String toLabel = edgeNode.get("to").asText();
                int weight = edgeNode.get("weight").asInt();

                Node from = nodeMap.get(fromLabel);
                Node to = nodeMap.get(toLabel);

                if (from == null || to == null) {
                    System.err.printf("⚠️ Edge refers to missing node: %s -> %s%n", fromLabel, toLabel);
                    continue;
                }

                graph.addEdge(new Edge(from, to, weight));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }
}
