package ru.rombe.neopractice.configuration.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rombe.neopractice.BaseTest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PropertiesSourceFileTest extends BaseTest {
    private static final String TEMP_FILENAME = "temp_props.json";

    @Test
    public void extractPropertiesTest() {
        createFile(TEMP_FILENAME);

        Map<String, Set<String>> expectedMap = new LinkedHashMap<>();
        expectedMap.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
        expectedMap.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
        expectedMap.put("SEX", Set.of("MALE", "FEMALE"));

        writeToFile(TEMP_FILENAME, toJson(expectedMap));

        PropertiesSource source = new PropertiesSourceFile(TEMP_FILENAME);

        try {
            Map<String, Set<String>> actualMap = source.extract();

            Assertions.assertEquals(expectedMap, actualMap);
        } catch (Exception e) {
            Assertions.fail();
        } finally {
            deleteFile(TEMP_FILENAME);
        }
    }
}