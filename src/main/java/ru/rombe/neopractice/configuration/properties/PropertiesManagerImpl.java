package ru.rombe.neopractice.configuration.properties;

import java.util.Map;
import java.util.Set;

public class PropertiesManagerImpl implements PropertiesManager {
    private Map<String, Set<String>> properties;
    private PropertiesSource source;

    public PropertiesManagerImpl(PropertiesSource source) throws Exception {
        setSource(source);
        update();
    }

    @Override
    public boolean containsKey(String propertyKey) {
        return properties.containsKey(propertyKey);
    }

    @Override
    public boolean containsValue(String propertyKey, String propertyValue) {
        Set<String> values = properties.get(propertyKey);

        return values != null && propertyValue != null && values.contains(propertyValue);
    }

    @Override
    public void update() throws Exception {
        properties = source.extract();
    }

    public void setSource(PropertiesSource source) {
        this.source = source;
    }
}