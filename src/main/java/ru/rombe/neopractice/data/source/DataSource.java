package ru.rombe.neopractice.data.source;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface DataSource<K, V> {
    Set<Map<K, V>> extract() throws IOException;
}