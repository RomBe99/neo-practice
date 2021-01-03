package ru.rombe.neopractice.configuration;

import java.util.Map;
import java.util.Set;

public interface PropertiesSource<K, V> {
    Map<K, Set<V>> extract() throws Exception;
}