package ru.rombe.neopractice.configuration;

import java.util.*;

public class PropertiesManagerImpl<K, V> implements PropertiesManager<K, V> {
    private final Map<K, Set<V>> properties = new HashMap<>();

    public PropertiesManagerImpl(Map<K, Collection<V>> properties) {
        properties.forEach((k, vs) -> this.properties.put(k, new HashSet<>(vs)));
    }

    @Override
    public boolean containsKey(K propertyKey) {
        return properties.containsKey(propertyKey);
    }

    @Override
    public boolean containsValue(K propertyKey, V propertyValue) {
        Set<V> values = properties.get(propertyKey);

        return values != null && values.contains(propertyValue);
    }
}