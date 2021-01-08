package ru.rombe.neopractice.configuration.properties;

public interface PropertiesManager {
    boolean containsKey(String propertyKey);

    boolean containsValue(String propertyKey, String propertyValue);

    void update() throws Exception;
}