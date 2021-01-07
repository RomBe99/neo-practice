package ru.rombe.neopractice.data.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rombe.neopractice.BaseTest;
import ru.rombe.neopractice.configuration.properties.FilePropertiesSource;
import ru.rombe.neopractice.configuration.properties.PropertiesSource;

import java.util.*;

public class FileDataSourceTest extends BaseTest {
    private static final String PROPS_FILENAME = "animal_props.json";
    private static final String DATA_FILENAME = "data.json";

    @Test
    public void extractTest() {
        createFile(PROPS_FILENAME);

        Map<String, Set<String>> propsMap = new LinkedHashMap<>();
        propsMap.put("WEIGHT", Set.of("THIN", "AVERAGE", "FAT"));
        propsMap.put("HEIGHT", Set.of("LOW", "MEDIUM", "HIGH"));
        propsMap.put("SEX", Set.of("MALE", "FEMALE"));

        writeToFile(PROPS_FILENAME, toJson(propsMap));

        createFile(DATA_FILENAME);

        PropertiesSource<String, String> propsSource = new FilePropertiesSource<>(PROPS_FILENAME);
        List<List<String>> data = Arrays.asList(
                Arrays.asList("THIN", "LOW", "MALE"),
                Arrays.asList("AVERAGE", "MEDIUM", "FEMALE"),
                Arrays.asList("FAT", "HIGH", "FEMALE"),
                Arrays.asList("THIN", "MEDIUM", "MALE"),
                Arrays.asList("FAT", "MEDIUM", "FEMALE"),
                Arrays.asList("FAT", "LOW", "MALE"),
                Arrays.asList("THIN", "HIGH", "FEMALE")
        );

        writeToFile(DATA_FILENAME, toJson(data));

        try {
            List<String> propsReadOrder = propsSource.extractReadOrder();

            DataSource<String, String> dataSource = new FileDataSource<>(DATA_FILENAME, propsReadOrder);
            Set<Map<String, String>> actualData = dataSource.extract();
            Set<Map<String, String>> expectedData = new LinkedHashSet<>(actualData.size());

            for (List<String> p : data) {
                Map<String, String> temp = new HashMap<>();

                for (int i = 0; i < propsReadOrder.size(); i++) {
                    temp.put(propsReadOrder.get(i), p.get(i));
                }

                expectedData.add(temp);
            }

            Assertions.assertEquals(expectedData, actualData);

        } catch (Exception e) {
            Assertions.fail();
        } finally {
            deleteFile(PROPS_FILENAME);
            deleteFile(DATA_FILENAME);
        }
    }
}