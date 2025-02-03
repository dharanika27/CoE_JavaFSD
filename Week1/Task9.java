package Week1;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Task9 {

    public static void analyzeLogFile(String inputFile, String outputFile) {
        Map<String, Integer> keywordCounts = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            for (String line : lines) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toUpperCase();

                    if (keywordCounts.containsKey(word)) {
                        keywordCounts.put(word, keywordCounts.get(word) + 1);
                    } else {
                        keywordCounts.put(word, 1);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("Log Analysis Results:\n");

                for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
                    if (entry.getValue() > 1) {
                        writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                }

                System.out.println("Log analysis complete. Results saved to " + outputFile);
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFile = "D:/jcodes/input.log";
        String outputFile = "D:/jcodes/output.txt";

        analyzeLogFile(inputFile, outputFile);
    }
}