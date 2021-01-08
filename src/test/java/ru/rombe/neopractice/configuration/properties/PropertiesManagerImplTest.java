package ru.rombe.neopractice.configuration.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

public class PropertiesManagerImplTest {
    @ParameterizedTest
    @MethodSource("containsKeyTestSource")
    public void containsKeyTest(PropertiesManager manager, String keyValue, boolean expectedValue) {

        boolean actualValue = manager.containsKey(keyValue);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @MethodSource("containsValueTest")
    public void containsValueTest(PropertiesManager manager, String keyValue, String value, boolean expectedValue) {

        boolean actualValue = manager.containsValue(keyValue, value);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    private static Stream<Arguments> containsValueTest() throws Exception {
        List<Arguments> arguments = new LinkedList<>();

        Map<String, Set<String>> properties = new HashMap<>();
        properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
        properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
        properties.put("SEX", Set.of("MALE", "FEMALE"));

        PropertiesSource source = Mockito.mock(PropertiesSource.class);
        Mockito.when(source.extract()).thenReturn(properties);

        PropertiesManager manager = new PropertiesManagerImpl(source);
        arguments.add(Arguments.of(manager, "WEIGHT", "AVERAGE", true));
        arguments.add(Arguments.of(manager, "HEIGHT", "HIGH", true));
        arguments.add(Arguments.of(manager, "SEX", "FEMALE", true));
        arguments.add(Arguments.of(manager, "SEX", "HIGH", false));
        arguments.add(Arguments.of(manager, "SEX", null, false));
        arguments.add(Arguments.of(manager, null, null, false));

        return arguments.stream();
    }

    private static Stream<Arguments> containsKeyTestSource() throws Exception {
        List<Arguments> arguments = new LinkedList<>();

        Map<String, Set<String>> properties = new HashMap<>();
        properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
        properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
        properties.put("SEX", Set.of("MALE", "FEMALE"));

        PropertiesSource source = Mockito.mock(PropertiesSource.class);
        Mockito.when(source.extract()).thenReturn(properties);

        PropertiesManager manager = new PropertiesManagerImpl(source);

        properties.keySet().forEach(s -> arguments.add(Arguments.of(manager, s, true)));
        arguments.add(Arguments.of(manager, "", false));
        arguments.add(Arguments.of(manager, null, false));

        return arguments.stream();
    }
}