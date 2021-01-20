package ru.rombe.neopractice.manager.property;

import ru.rombe.neopractice.manager.Updatable;

/**
 * Classes implemented this interface store properties for analysis.
 *
 * @param <P> type of property
 * @param <V> type of value
 */
public interface PropertiesManager<P, V> extends Updatable {
    /**
     * Check contain property.
     *
     * @param property property
     * @return true if manager contains property, else false
     */
    boolean containsProperty(P property);

    /**
     * Check contain property and value.
     *
     * @param property      property
     * @param propertyValue property value
     * @return true if manager contains property and value, else false
     */
    boolean containsPropertyValue(P property, V propertyValue);
}