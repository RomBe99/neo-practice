package ru.rombe.neopractice.configuration;

import java.util.Collection;
import java.util.Map;

public interface IPropertiesSource<K, V> {
    Map<K, V> extract() throws Exception;
}