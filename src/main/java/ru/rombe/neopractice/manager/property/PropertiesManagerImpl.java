package ru.rombe.neopractice.manager.property;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.*;

public class PropertiesManagerImpl<SR, P, V> extends AbstractManager<SR, Map<P, Set<V>>> implements PropertiesManager<P, V> {
    private Map<P, Set<V>> properties = Collections.emptyMap();

    public PropertiesManagerImpl(Source<SR> propertiesSource, Decoder<SR, Map<P, Set<V>>> decoder) {
        super(propertiesSource, decoder);
    }

    @Override
    public boolean containsProperty(P property) {
        return properties.containsKey(property);
    }

    @Override
    public boolean containsPropertyValue(P property, V propertyValue) {
        Set<V> values = properties.get(property);

        return values != null && values.contains(propertyValue);
    }

    @Override
    public void update() throws Exception {
        SR extractResult = source.extract();
        properties = decoder.decode(extractResult);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertiesManagerImpl<?, ?, ?> that = (PropertiesManagerImpl<?, ?, ?>) o;
        return Objects.equals(source, that.source) && Objects.equals(decoder, that.decoder) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, decoder, properties);
    }
}