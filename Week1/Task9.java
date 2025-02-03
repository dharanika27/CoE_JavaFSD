package Week1;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Task9 {

    // Function to analyze the log file and write results to an output file
    public static void analyzeLogFile(String inputFile, String outputFile, List<String> keywords) {
        // Map to keep track of keyword counts
        Map<String, Integer> keywordCounts = new HashMap<>();

        // Initialize keyword counts to 0
        for (String keyword : keywords) {
            keywordCounts.put(keyword, 0);
        }

        try {
            // Read the input file line by line
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            for (String line : lines) {
                // Check each keyword and if it's present in the line, increment the corresponding count
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        keywordCounts.put(keyword, keywordCounts.get(keyword) + 1);
                    }
                }
            }

            // Write the results to the output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("Log Analysis Results:\n");
                for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
                System.out.println("Log analysis complete. Results saved to " + outputFile);
            }

        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example usage of the Task9 LogFileAnalyzer

        // Input log file and output file
        String inputFile = "input.log";  // Replace with the actual log file path
        String outputFile = "output.txt"; // Replace with the desired output file path

        // Define the keywords to search for (can be customized)
        List<String> keywords = Arrays.asList("ERROR", "WARNING", "INFO", "CRITICAL");

        // Call the analyzeLogFile method to analyze the log and write results
        analyzeLogFile(inputFile, outputFile, keywords);
    }
}
