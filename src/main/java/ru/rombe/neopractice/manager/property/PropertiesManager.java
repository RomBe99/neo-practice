package ru.rombe.neopractice.manager.property;

import ru.rombe.neopractice.manager.Updatable;

public interface PropertiesManager<P, V> extends Updatable {
    boolean containsProperty(P property);

    boolean containsPropertyValue(P property, V propertyValue);
}