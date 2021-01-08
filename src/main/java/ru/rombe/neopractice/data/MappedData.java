package ru.rombe.neopractice.data;

import java.util.Map;
import java.util.Objects;

public class MappedData {
    private final Map<String, String> properties;

    public MappedData(Map<String, String> properties) {
        this.properties = properties;
    }

    public boolean containsPropertyValue(String propertyKey, String propertyValue) {
        String value = properties.get(propertyKey);

        return value != null && value.equals(propertyValue);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedData that = (MappedData) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}