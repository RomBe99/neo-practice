package ru.rombe.neopractice.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class FilePropertiesSource implements IPropertiesSource<String, Set<String>> {
    private final Gson mapper = new GsonBuilder().create();
    private final String fileName;

    public FilePropertiesSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, Set<String>> extract() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return mapper.fromJson(sb.toString(), new TypeToken<Map<String, Set<String>>>() {
            }.getType());
        }
    }
}
