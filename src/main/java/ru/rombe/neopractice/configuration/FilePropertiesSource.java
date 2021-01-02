package ru.rombe.neopractice.configuration;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FilePropertiesSource<K, V> implements PropertiesSource<K, V> {
    private final String fileName;

    public FilePropertiesSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<K, V> extract() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return JsonUtils.mapFromJson(sb.toString());
        }
    }
}
