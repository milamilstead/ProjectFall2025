package edu.desu.answermap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class AnswerMap {
    // Only component is the map
    public Map<String, String> map = new TreeMap<>();

    public AnswerMap() {
        //String filename = "DataFiles/TestExpectedResults.txt";
        String filename = "DataFiles/TestExpectedResults.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String key = null;
            StringBuilder valueBuilder = new StringBuilder();
            String line;
            boolean readingValue = false;
            while ((line = br.readLine()) != null) {
                //if (line.trim().isEmpty()) {
                if (line.trim().equals("END VALUE")) {
                    // End of value
                    if (readingValue && key != null) {
                        map.put(key, valueBuilder.toString().trim());
                        valueBuilder.setLength(0);
                        key = null;
                        readingValue = false;
                    }
                    continue;
                }
                if (!readingValue) {
                    // Start new key
                    key = line.trim();
                    readingValue = true;
                } else {
                    // Continue value
                    if (valueBuilder.length() > 0) valueBuilder.append("\n");
                    valueBuilder.append(line);
                }
            }
            // Handle last key/value (if file doesn't end with a blank line)
            if (readingValue && key != null && valueBuilder.length() > 0) {
                map.put(key, valueBuilder.toString().trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void printAnswerKeyMap() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\n" + entry.getValue());
        }
    }

    // Add QueryAnswerKeyMap

    public static void main(String[] args) {
        // For informal test
        AnswerMap ourMap = new AnswerMap();
        ourMap.printAnswerKeyMap();
        //ourMap.printAnswerKeyMap();
        //String TestValue1 = ourMap.map.get("Answer1");
        //System.out.println("Answer1 is:\n" + TestValue1);
        //String TestValue2 = ourMap.map.get("Answer2");
        //System.out.println("Answer2 is:\n" + TestValue2);
    }
}