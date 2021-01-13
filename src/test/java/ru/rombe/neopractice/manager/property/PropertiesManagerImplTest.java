package ru.rombe.neopractice.manager.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.rombe.neopractice.decoder.JsonDecoders;
import ru.rombe.neopractice.source.Source;
import ru.rombe.neopractice.util.JsonUtils;

import java.util.*;
import java.util.stream.Stream;

public class PropertiesManagerImplTest {
    @ParameterizedTest
    @MethodSource("containsPropertyValues")
    public <P> void containsPropertyTest(PropertiesManagerImpl<?, P, ?> propertiesManager,
                                         P property, boolean expectedResult) throws Exception {
        propertiesManager.update();
        boolean actualResult = propertiesManager.containsProperty(property);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("containsPropertyValueValues")
    public <P, V> void containsPropertyValueTest(PropertiesManagerImpl<?, P, V> propertiesManager,
                                                 P property, V propertyValue, boolean expectedResult) throws Exception {
        propertiesManager.update();
        boolean actualResult = propertiesManager.containsPropertyValue(property, propertyValue);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> containsPropertyValues() throws Exception {
        List<Arguments> arguments = new LinkedList<>();

        {
            Map<String, Set<String>> properties = new HashMap<>();
            properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            properties.put("SEX", Set.of("MALE", "FEMALE"));

            Source<String> source = Mockito.mock(Source.class);
            PropertiesManagerImpl<String, String, String> propertiesManager = new PropertiesManagerImpl<>(source, JsonDecoders::mapToSetFromJson);

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(properties));

            arguments.add(Arguments.of(propertiesManager, "HEIGHT", true));
            arguments.add(Arguments.of(propertiesManager, "SEX", true));
            arguments.add(Arguments.of(propertiesManager, "", false));
            arguments.add(Arguments.of(propertiesManager, null, false));
        }

        return arguments.stream();
    }

    public static Stream<Arguments> containsPropertyValueValues() throws Exception {
        List<Arguments> arguments = new LinkedList<>();

        {
            Map<String, Set<String>> properties = new HashMap<>();
            properties.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            properties.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            properties.put("SEX", Set.of("MALE", "FEMALE"));

            Source<String> source = Mockito.mock(Source.class);
            PropertiesManagerImpl<String, String, String> propertiesManager = new PropertiesManagerImpl<>(source, JsonDecoders::mapToSetFromJson);

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(properties));

            arguments.add(Arguments.of(propertiesManager, "HEIGHT", "MEDIUM" , true));
            arguments.add(Arguments.of(propertiesManager, "HEIGHT", "HIGH" , true));
            arguments.add(Arguments.of(propertiesManager, "SEX", "FEMALE" , true));
            arguments.add(Arguments.of(propertiesManager, "WEIGHT", null , false));
            arguments.add(Arguments.of(propertiesManager, "WEIGHT", "heh" , false));
            arguments.add(Arguments.of(propertiesManager, "SEX", "" , false));
            arguments.add(Arguments.of(propertiesManager, "", null, false));
            arguments.add(Arguments.of(propertiesManager, null, null, false));
        }

        return arguments.stream();
    }
}