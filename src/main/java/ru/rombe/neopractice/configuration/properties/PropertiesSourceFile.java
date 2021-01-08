package ru.rombe.neopractice.configuration.properties;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PropertiesSourceFile implements PropertiesSource {
    private final String filename;

    public PropertiesSourceFile(String filename) {
        this.filename = filename;
    }

    @Override
    public Map<String, Set<String>> extract() throws Exception {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Map<String, Set<String>> temp = JsonUtils.mapFromJson(sb.toString());
        Map<String, Set<String>> extractedProps = new LinkedHashMap<>(temp.size());

        for (String k : temp.keySet()) {
            extractedProps.put(k, new LinkedHashSet<>(temp.get(k)));
        }

        return extractedProps;
    }
}