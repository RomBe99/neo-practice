package ru.rombe.neopractice.configuration.properties;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FilePropertiesSource<K, V> implements PropertiesSource<K, V> {
    private final String filename;
    private Map<K, Set<V>> extractedProps;

    public FilePropertiesSource(String filename) {
        this.filename = filename;
    }

    @Override
    public Map<K, Set<V>> extractProperties() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Map<K, Set<V>> temp = JsonUtils.mapFromJson(sb.toString());
            extractedProps = new LinkedHashMap<>(temp.size());

            for (K k : temp.keySet()) {
                extractedProps.put(k, new LinkedHashSet<>(temp.get(k)));
            }

            return extractedProps;
        }
    }

    @Override
    public List<K> extractReadOrder() throws Exception {
        if (extractedProps == null) {
            extractProperties();
        }

        return new ArrayList<>(extractedProps.keySet());
    }
}