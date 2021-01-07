package ru.rombe.neopractice.configuration.properties;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PropertiesSource<K, V> {
    Map<K, Set<V>> extractProperties() throws Exception;

    List<K> extractReadOrder() throws Exception;
}