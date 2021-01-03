package ru.rombe.neopractice.configuration;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class FilePropertiesSource<K, V> implements PropertiesSource<K, V> {
    private final String filename;

    public FilePropertiesSource(String filename) {
        this.filename = filename;
    }

    @Override
    public Map<K, Set<V>> extract() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return JsonUtils.mapFromJson(sb.toString());
        }
    }
}