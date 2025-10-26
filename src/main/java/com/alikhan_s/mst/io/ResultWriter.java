package com.alikhan_s.mst.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Serializes and writes the given object (e.g., list of output results)
 * to a JSON file in a human-readable format.
 */
public class ResultWriter {

    public static void writeResults(Object data, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("Results successfully written to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing results: " + e.getMessage());
        }
    }
}
