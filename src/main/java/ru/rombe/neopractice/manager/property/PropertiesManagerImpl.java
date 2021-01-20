package ru.rombe.neopractice.manager.property;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.*;

/**
 * This class store properties for analysis.
 *
 * @param <SR> source returned value type
 * @param <P>  keys type
 * @param <V>  values type
 * @see ru.rombe.neopractice.manager.AbstractManager
 * @see ru.rombe.neopractice.manager.property.PropertiesManager
 * @see ru.rombe.neopractice.manager.Updatable
 * @see Source
 */
public class PropertiesManagerImpl<SR, P, V> extends AbstractManager<SR, Map<P, Set<V>>> implements PropertiesManager<P, V> {
    private Map<P, Set<V>> properties = Collections.emptyMap();

    /**
     * @param source  properties source
     * @param decoder decoder for encoded data extracted from source
     * @see Source
     * @see Decoder
     */
    public PropertiesManagerImpl(Source<SR> source, Decoder<SR, Map<P, Set<V>>> decoder) {
        super(source, decoder);
    }

    /**
     * Check contain property.
     *
     * @param property property
     * @return true if manager contains property, else false
     */
    @Override
    public boolean containsProperty(P property) {
        return properties.containsKey(property);
    }

    /**
     * Check contain property and value.
     *
     * @param property      property
     * @param propertyValue property value
     * @return true if manager contains property and value, else false
     */
    @Override
    public boolean containsPropertyValue(P property, V propertyValue) {
        Set<V> values = properties.get(property);

        return values != null && values.contains(propertyValue);
    }

    /**
     * Method extract and decode properties for analysis.
     *
     * @throws Exception from source or decoder
     * @see ru.rombe.neopractice.manager.Updatable
     * @see Source
     * @see Decoder
     */
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