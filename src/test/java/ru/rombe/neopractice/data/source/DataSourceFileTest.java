package ru.rombe.neopractice.data.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rombe.neopractice.BaseTest;

import java.util.*;
import java.util.function.Function;

public class DataSourceFileTest extends BaseTest {
    private static final String DATA_FILENAME = "data.json";

    @Test
    public void extractTest() {
        createFile(DATA_FILENAME);

        List<String> propertyKeys = Arrays.asList("WEIGHT", "HEIGHT", "SEX");
        Function<List<String>, Map<String, String>> dataConstructor = strings -> {
            Map<String, String> result = new HashMap<>();

            for (int i = 0; i < propertyKeys.size(); i++) {
                result.put(propertyKeys.get(i), strings.get(i));
            }

            return result;
        };

        List<Map<String, String>> data = new ArrayList<>();
        data.add(dataConstructor.apply(Arrays.asList("THIN", "LOW", "MALE")));
        data.add(dataConstructor.apply(Arrays.asList("AVERAGE", "MEDIUM", "FEMALE")));
        data.add(dataConstructor.apply(Arrays.asList("FAT", "HIGH", "FEMALE")));
        data.add(dataConstructor.apply(Arrays.asList("THIN", "MEDIUM", "MALE")));
        data.add(dataConstructor.apply(Arrays.asList("FAT", "MEDIUM", "FEMALE")));
        data.add(dataConstructor.apply(Arrays.asList("FAT", "LOW", "MALE")));
        data.add(dataConstructor.apply(Arrays.asList("FAT", "LOW", "MALE")));
        data.add(dataConstructor.apply(Arrays.asList("THIN", "HIGH", "FEMALE")));

        Set<Map<String, String>> expectedData = new HashSet<>(data);

        writeToFile(DATA_FILENAME, toJson(data));

        DataSource dataSource = new DataSourceFile(DATA_FILENAME);

        try {
            Set<Map<String, String>> actualData = dataSource.extract();

            Assertions.assertEquals(expectedData, actualData);
        } catch (Exception e) {
            Assertions.fail();
        } finally {
            deleteFile(DATA_FILENAME);
        }
    }
}