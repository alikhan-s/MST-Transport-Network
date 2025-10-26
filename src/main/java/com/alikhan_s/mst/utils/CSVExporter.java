package com.alikhan_s.mst.utils;

import com.alikhan_s.mst.models.OutputResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public static void exportToCSV(List<OutputResult> results, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Заголовки CSV
            writer.write("Graph ID,Vertices,Edges,"
                    + "Prim Cost,Prim Ops,Prim Time(ms),"
                    + "Kruskal Cost,Kruskal Ops,Kruskal Time(ms)\n");

            // Данные по каждому графу
            for (OutputResult r : results) {
                writer.write(String.format("%s,%d,%d,%.2f,%.2f,%.3f,%.2f,%.2f,%.3f\n",
                        r.getGraphId(),
                        r.getVertices(),
                        r.getEdges(),
                        r.getPrimResult().getTotalCost(),
                        r.getPrimResult().getOperationsCount(),
                        r.getPrimResult().getExecutionTimeMs(),
                        r.getKruskalResult().getTotalCost(),
                        r.getKruskalResult().getOperationsCount(),
                        r.getKruskalResult().getExecutionTimeMs()
                ));
            }

            System.out.println("[0] CSV report successfully generated: " + fileName);

        } catch (IOException e) {
            System.err.println("[!] Error writing CSV: " + e.getMessage());
        }
    }
}
