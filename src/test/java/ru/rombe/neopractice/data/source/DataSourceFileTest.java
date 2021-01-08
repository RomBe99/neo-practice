package ru.rombe.neopractice.data.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rombe.neopractice.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DataSourceFileTest extends BaseTest {
    private static final String DATA_FILENAME = "data.json";

    @Test
    public void extractTest() {
        createFile(DATA_FILENAME);

        List<List<String>> data = Arrays.asList(
                Arrays.asList("THIN", "LOW", "MALE"),
                Arrays.asList("AVERAGE", "MEDIUM", "FEMALE"),
                Arrays.asList("FAT", "HIGH", "FEMALE"),
                Arrays.asList("THIN", "MEDIUM", "MALE"),
                Arrays.asList("FAT", "MEDIUM", "FEMALE"),
                Arrays.asList("FAT", "LOW", "MALE"),
                Arrays.asList("FAT", "LOW", "MALE"),
                Arrays.asList("THIN", "HIGH", "FEMALE")
        );
        Set<List<String>> expectedData = new HashSet<>(data);

        writeToFile(DATA_FILENAME, toJson(data));

        DataSource dataSource = new DataSourceFile(DATA_FILENAME);

        try {
            Set<List<String>> actualData = dataSource.extract();

            Assertions.assertEquals(expectedData, actualData);
        } catch (Exception e) {
            Assertions.fail();
        } finally {
            deleteFile(DATA_FILENAME);
        }
    }
}