package ru.rombe.neopractice.configuration.properties;

import java.util.Map;
import java.util.Set;

public interface PropertiesSource {
    Map<String, Set<String>> extract() throws Exception;
}