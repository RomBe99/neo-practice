package ru.rombe.neopractice.processor;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rombe.neopractice.BaseTestFileOperations;
import ru.rombe.neopractice.factory.DataManagerFactory;
import ru.rombe.neopractice.factory.FiltersManagerFactory;
import ru.rombe.neopractice.factory.PropertiesManagerFactory;
import ru.rombe.neopractice.manager.property.PropertiesManager;
import ru.rombe.neopractice.util.JsonUtils;

import java.util.*;
import java.util.stream.Stream;

public class ProcessorFileBasedTest extends BaseTestFileOperations {
    private static final String propsFilename = "props.json";
    private static final String filtersFilename = "filters.json";
    private static final String dataFilename = "data,json";
    private static Processor<String, String, String> processor;

    @BeforeAll
    public static void prepareFiles() throws Exception {
        createFile(propsFilename);
        Map<String, Set<String>> props = new HashMap<>();
        props.put("WEIGHT", Set.of("LIGHT", "MEDIUM", "HEAVY"));
        props.put("HEIGHT", Set.of("LITTLE", "LOW", "TALL"));
        props.put("TYPE", Set.of("HERBIVOROUS", "CARNIVOROUS", "OMNIVOROUS"));
        writeToFile(propsFilename, JsonUtils.toJson(props));

        createFile(filtersFilename);
        Map<String, String> filters = new HashMap<>();
        filters.put("number herbivores",
                "TYPE.HERBIVOROUS");
        filters.put("number herbivores or carnivores and they small",
                "(TYPE.HERBIVOROUS OR TYPE.CARNIVOROUS) AND HEIGHT.LITTLE");
        filters.put("number omnivores but not high",
                "TYPE.OMNIVOROUS AND NOT HEIGHT.TALL");
        writeToFile(filtersFilename, JsonUtils.toJson(filters));

        PropertiesManager<String, String> propertiesManager =
                PropertiesManagerFactory.getPropertiesManagerFileBasedWithJsonDecoder(propsFilename);

        createFile(dataFilename);


        List<Map<String, String>> data = new LinkedList<>();

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "LIGHT");
            temp.put("HEIGHT", "LITTLE");
            temp.put("TYPE", "OMNIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "HEAVY");
            temp.put("HEIGHT", "LITTLE");
            temp.put("TYPE", "OMNIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "HEAVY");
            temp.put("HEIGHT", "LOW");
            temp.put("TYPE", "HERBIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "HEAVY");
            temp.put("HEIGHT", "LITTLE");
            temp.put("TYPE", "HERBIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "MEDIUM");
            temp.put("HEIGHT", "LOW");
            temp.put("TYPE", "CARNIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "HEAVY");
            temp.put("HEIGHT", "TALL");
            temp.put("TYPE", "OMNIVOROUS");
            data.add(temp);
        }

        {
            Map<String, String> temp = new HashMap<>();
            temp.put("WEIGHT", "HEAVY");
            temp.put("HEIGHT", "RAND_VALUE");
            temp.put("TYPE", "OMNIVOROUS");
            data.add(temp);
        }

        data.add(Collections.emptyMap());

        writeToFile(dataFilename, JsonUtils.toJson(data));

        processor = new Processor<>(propertiesManager,
                DataManagerFactory.getDataManagerFileBasedWithJsonDecoder(dataFilename),
                FiltersManagerFactory.getFiltersManagerFileBased(filtersFilename, propertiesManager));
        processor.update();
    }

    @AfterAll
    public static void deleteFiles() {
        deleteFile(propsFilename);
        deleteFile(filtersFilename);
        deleteFile(dataFilename);
    }

    @ParameterizedTest
    @MethodSource("countDataByFilterIdValuesBasedOnString")
    public void countDataByFilterIdTest(String filterId, int expectedCount) {
        int actualCount = processor.countDataByFilterId(filterId);

        Assertions.assertEquals(expectedCount, actualCount);
    }

    private static Stream<Arguments> countDataByFilterIdValuesBasedOnString() {
        return Stream.of(Arguments.of("number herbivores", 2),
                Arguments.of("number herbivores or carnivores and they small", 1),
                Arguments.of("number omnivores but not high", 2));
    }
}