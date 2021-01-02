package ru.rombe.neopractice.configuration;

import java.util.Map;

public interface PropertiesSource<K, V> {
    Map<K, V> extract() throws Exception;
}