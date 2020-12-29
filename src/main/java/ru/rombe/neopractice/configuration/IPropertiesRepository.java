package ru.rombe.neopractice.configuration;

import java.util.Map;

public interface IPropertiesRepository<K, V> {
    boolean containsKey(K propertyKey);

    boolean containsValue(K propertyKey, V propertyValue);
}