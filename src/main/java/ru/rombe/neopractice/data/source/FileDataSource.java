package ru.rombe.neopractice.data.source;

import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileDataSource<K, V> implements DataSource<K, V> {
    private final String filename;
    private final List<K> propertyReadOrder;

    public FileDataSource(String filename, List<K> propertyReadOrder) {
        this.filename = filename;
        this.propertyReadOrder = propertyReadOrder;
    }

    @Override
    public Set<Map<K, V>> extract() throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        List<List<V>> data =  JsonUtils.collectionFromJson(sb.toString());
        Set<Map<K, V>> result = new HashSet<>(data.size());

        final int readOrderSize = propertyReadOrder.size();

        for (List<V> d : data) {
            if (d.size() == readOrderSize) {
                Map<K, V> temp = new HashMap<>();

                for (int i = 0; i < readOrderSize; i++) {
                    temp.put(propertyReadOrder.get(i), d.get(i));
                }

                result.add(temp);
            }
        }

        return result;
    }
}