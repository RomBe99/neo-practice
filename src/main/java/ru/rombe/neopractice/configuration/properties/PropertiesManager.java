package ru.rombe.neopractice.configuration.properties;

public interface PropertiesManager<K, V> {
    boolean containsKey(K propertyKey);

    boolean containsValue(K propertyKey, V propertyValue);
}