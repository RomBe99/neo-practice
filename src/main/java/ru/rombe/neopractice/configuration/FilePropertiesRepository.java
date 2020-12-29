package ru.rombe.neopractice.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilePropertiesRepository<K, V extends Collection<?>> implements IPropertiesRepository<K, V> {
    private final Map<K, V> propertiesToValues = new HashMap<>();

    private FilePropertiesRepository() {
    }

    @Override
    public boolean containsKey(K propertyKey) {
        return propertiesToValues.containsKey(propertyKey);
    }

    @Override
    public boolean containsValue(K propertyKey, V propertyValue) {
        V propertyValues = propertiesToValues.get(propertyKey);

        return propertyValues != null && propertyValues.contains(propertyValue);
    }
}
