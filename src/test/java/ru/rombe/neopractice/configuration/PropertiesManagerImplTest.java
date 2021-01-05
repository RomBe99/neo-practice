package ru.rombe.neopractice.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

public class PropertiesManagerImplTest {
    @ParameterizedTest
    @MethodSource("containsKeyTestSource")
    public <K, V> void containsKeyTest(PropertiesManager<K, V> manager, K keyValue, boolean expectedValue) {

        boolean actualValue = manager.containsKey(keyValue);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @MethodSource("containsValueTest")
    public <K, V> void containsValueTest(PropertiesManager<K, V> manager, K keyValue, V value, boolean expectedValue) {

        boolean actualValue = manager.containsValue(keyValue, value);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    private static Stream<Arguments> containsValueTest() {
        List<Arguments> arguments = new LinkedList<>();

        {
            Map<String, Set<String>> properties = new HashMap<>();
            properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            properties.put("SEX", Set.of("MALE", "FEMALE"));

            PropertiesManager<String, String> manager = new PropertiesManagerImpl<>(properties);
            arguments.add(Arguments.of(manager, "WEIGHT", "AVERAGE", true));
            arguments.add(Arguments.of(manager, "HEIGHT", "HIGH", true));
            arguments.add(Arguments.of(manager, "SEX", "FEMALE", true));
            arguments.add(Arguments.of(manager, "SEX", "HIGH", false));
            arguments.add(Arguments.of(manager, "SEX", null, false));
            arguments.add(Arguments.of(manager, null, null, false));
        }

        {
            Map<Integer, Set<Integer>> properties = new HashMap<>();
            properties.put(1, Set.of(1, 2, 3));
            properties.put(2, Set.of(1, 2, 3));
            properties.put(3, Set.of(1, 2));

            PropertiesManager<Integer, Integer> manager = new PropertiesManagerImpl<>(properties);
            arguments.add(Arguments.of(manager, 1, 1, true));
            arguments.add(Arguments.of(manager, 2, 2, true));
            arguments.add(Arguments.of(manager, 3, 1, true));
            arguments.add(Arguments.of(manager, 3, 3, false));
            arguments.add(Arguments.of(manager, 3, -3, false));
            arguments.add(Arguments.of(manager, 3, null, false));
            arguments.add(Arguments.of(manager, null, null, false));
        }

        return arguments.stream();
    }

    private static Stream<Arguments> containsKeyTestSource() {
        List<Arguments> arguments = new LinkedList<>();

        {
            Map<String, Set<String>> properties = new HashMap<>();
            properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            properties.put("SEX", Set.of("MALE", "FEMALE"));

            PropertiesManager<String, String> manager = new PropertiesManagerImpl<>(properties);

            properties.keySet().forEach(s -> arguments.add(Arguments.of(manager, s, true)));
            arguments.add(Arguments.of(manager, "", false));
            arguments.add(Arguments.of(manager, null, false));
        }

        {
            Map<Integer, Set<Integer>> properties = new HashMap<>();
            properties.put(1, Set.of(1, 2, 3));
            properties.put(2, Set.of(1, 2, 3));
            properties.put(3, Set.of(1, 2));

            PropertiesManager<Integer, Integer> manager = new PropertiesManagerImpl<>(properties);

            properties.keySet().forEach(s -> arguments.add(Arguments.of(manager, s, true)));
            arguments.add(Arguments.of(manager, null, false));
        }

        return arguments.stream();
    }
}