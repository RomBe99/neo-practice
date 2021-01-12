package ru.rombe.neopractice.source;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rombe.neopractice.BaseTest;
import ru.rombe.neopractice.util.JsonUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class FileSourceTest extends BaseTest {
    private static final String sourceFileName = "temp_props.json";
    final FileSource fileSource = new FileSource(sourceFileName);

    @BeforeAll
    public static void createFile() {
        createFile(sourceFileName);
    }

    @AfterAll
    public static void deleteFile() {
        deleteFile(sourceFileName);
    }

    @ParameterizedTest
    @MethodSource("extractTestValues")
    public void extractTest(String expectedString) throws IOException {
        writeToFile(sourceFileName, expectedString);

        String actualString = fileSource.extract();

        Assertions.assertEquals(expectedString, actualString);
    }

    private static Stream<Arguments> extractTestValues() {
        List<Arguments> arguments = new LinkedList<>();

        {
            Map<String, Set<String>> map = new HashMap<>();
            map.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            map.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            map.put("SEX", Set.of("MALE", "FEMALE"));

            String expectedString = JsonUtils.toJson(map);
            arguments.add(Arguments.of(expectedString));
        }

        {
            Map<String, Set<String>> map = new HashMap<>();
            map.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
            map.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
            map.put("SEX", Set.of("MALE", "FEMALE"));

            String expectedString = map.toString();
            arguments.add(Arguments.of(expectedString));
        }

        {
            List<String> list = Arrays.asList("One", "Two", "Three");

            String expectedString = JsonUtils.toJson(list);
            arguments.add(Arguments.of(expectedString));
        }

        {
            String expectedString = "";
            arguments.add(Arguments.of(expectedString));
        }

        return arguments.stream();
    }
}