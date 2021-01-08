package ru.rombe.neopractice.data.source;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataSourceFile implements DataSource {
    private final String filename;

    public DataSourceFile(String filename) {
        this.filename = filename;
    }

    @Override
    public Set<List<String>> extract() throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        List<List<String>> data = JsonUtils.collectionFromJson(sb.toString());

        return new HashSet<>(data);
    }
}