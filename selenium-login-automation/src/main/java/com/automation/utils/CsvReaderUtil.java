package com.automation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading CSV test data files for Data-Driven Testing (Bonus Requirement)
 */
public class CsvReaderUtil {

    /**
     * Reads a CSV file and converts it into a 2D Object array for TestNG @DataProvider
     * 
     * @param filePath Relative or absolute path to the CSV file
     * @return 2D Object array containing rows and column values
     */
    public static Object[][] readCsvData(String filePath) {
        List<String[]> records = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("CSV file not found at path: " + file.getAbsolutePath());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // Ignore empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Skip header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Parse line by commas, keeping trailing empty tokens (e.g. empty fields)
                String[] rawTokens = line.split(",", -1);
                String[] cleanedTokens = new String[rawTokens.length];
                for (int i = 0; i < rawTokens.length; i++) {
                    cleanedTokens[i] = rawTokens[i].trim();
                }
                records.add(cleanedTokens);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file at: " + filePath, e);
        }

        Object[][] data = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            data[i] = records.get(i);
        }

        return data;
    }
}
