package org.example.mocktest_test2.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVUtil {

        public static List<String[]> readCsv(String filePath) throws IOException {
            List<String[]> data = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextLine()) {
                    // Split the line into columns by comma delimiter
                    String line;
                    line = scanner.nextLine();
                    data.add(line.split(","));
                }
            }
            return data;
        }


        public static void writeCsv(String filePath, List<String[]> data) throws IOException {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String[] row : data) {
                    // Join columns with commas and write to the file
                    bw.write(String.join(",", row));
                    bw.newLine();
                }
            }
        }

}
