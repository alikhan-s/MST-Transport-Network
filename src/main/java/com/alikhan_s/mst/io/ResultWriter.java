package com.alikhan_s.mst.io;

import com.alikhan_s.mst.models.OutputResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Serializes and writes the given object (e.g., list of output results)
 * to a JSON file in a human-readable format.
 */

public class ResultWriter {

    private static class ResultsWrapper {
        @SerializedName("results")
        List<OutputResult> results;

        ResultsWrapper(List<OutputResult> results) {
            this.results = results;
        }
    }

    public static void writeResults(List<OutputResult> results, String outputPath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ResultsWrapper wrapper = new ResultsWrapper(results);

        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(wrapper, writer);
            System.out.println("[0] Results written to " + outputPath);
        } catch (IOException e) {
            System.err.println("[!] Error writing results to file: " + e.getMessage());
        }
    }
}
