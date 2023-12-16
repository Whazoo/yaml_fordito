package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "test_yaml.yaml";
        String outputFilePath = "output.yaml";

        translateYaml(inputFilePath, outputFilePath);
    }

    private static void translateYaml(String inputFilePath, String outputFilePath) {
        try {
            // YAML beolvasása
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(new FileReader(inputFilePath));

            // Értékek visszafordítása
            reverseValues(data);

            // Visszafordított értékek YAML-be írása
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Opcionális formázási beállítások
            Yaml reverseYaml = new Yaml(options);
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                reverseYaml.dump(data, writer);
            }

            System.out.println("A fordítás sikeresen elkészült.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void reverseValues(Map<String, Object> data) {
        // Create a temporary map to store reversed values
        Map<String, Object> reversedData = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            reversedData.put(entry.getKey(), reverseValue(entry.getValue()));
        }

        // Update the original map with reversed values
        data.clear();
        data.putAll(reversedData);
    }

    private static Object reverseValue(Object value) {
        if (value instanceof String) {
            // Szöveg inverz
            return new StringBuilder((String) value).reverse().toString();
        } else if (value instanceof Map) {
            reverseValues((Map<String, Object>) value);
            return value;
        } else if (value instanceof List) {
            return reverseList((List<Object>) value);
        } else {
            return value;
        }
    }

    private static List<Object> reverseList(List<Object> list) {
        List<Object> reversedList = new ArrayList<>();
        for (Object element : list) {
            reversedList.add(reverseValue(element));
        }
        return reversedList;
    }
    
}