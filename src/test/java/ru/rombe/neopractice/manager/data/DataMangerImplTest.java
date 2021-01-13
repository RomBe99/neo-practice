package ru.rombe.neopractice.manager.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.rombe.neopractice.source.Source;
import ru.rombe.neopractice.util.JsonUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataMangerImplTest {
    @Test
    public void getDataWithoutUpdateTest() {
        DataManagerImpl<String, String, String> dataManager = new DataManagerImpl<>(null, null);
        boolean isEmptyData = dataManager.getData().isEmpty();

        Assertions.assertTrue(isEmptyData);
    }

    @ParameterizedTest
    @MethodSource("getDataWithUpdateValues")
    public <K, V> void getDataWithUpdateTest(DataManagerImpl<?, K, V> dataManager, List<Map<K, V>> expectedData) throws Exception {
        dataManager.update();

        List<Map<K, V>> actualData = dataManager.getData();
        Assertions.assertEquals(expectedData, actualData);
    }

    public static Stream<Arguments> getDataWithUpdateValues() throws Exception {
        List<Arguments> arguments = new LinkedList<>();

        {
            Source<String> source = Mockito.mock(Source.class);
            DataManagerImpl<String, String, String> dataManager = new DataManagerImpl<>(source, JsonUtils::collectionFromJson);
            List<Map<String, String>> expectedData = new LinkedList<>();

            {
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("WEIGHT", "THIN");
                    map.put("HEIGHT", "MEDIUM");
                    map.put("SEX", "FEMALE");

                    expectedData.add(map);
                    expectedData.add(map);
                }

                {
                    Map<String, String> map = new HashMap<>();
                    map.put("WEIGHT", "FAT");
                    map.put("HEIGHT", "MEDIUM");
                    map.put("SEX", "MALE");

                    expectedData.add(map);
                }
            }

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(expectedData));

            arguments.add(Arguments.of(dataManager, expectedData));
        }

        {
            Source<String> source = Mockito.mock(Source.class);
            DataManagerImpl<String, String, String> dataManager = new DataManagerImpl<>(source, JsonUtils::collectionFromJson);
            List<Map<String, String>> expectedData = new LinkedList<>();

            {
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("WEIGHT", "THIN");
                    map.put("HEIGHT", "MEDIUM");
                    map.put("SEX", "FEMALE");

                    expectedData.add(map);
                }
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("WEIGHT", "THIN");
                    map.put("HEIGHT", "MEDIUM");
                    map.put("SEX", "MALE");

                    expectedData.add(map);
                }

                {
                    Map<String, String> map = new HashMap<>();
                    map.put("WEIGHT", "FAT");
                    map.put("HEIGHT", "LOW");
                    map.put("SEX", "FEMALE");

                    expectedData.add(map);
                }
            }

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(expectedData));

            arguments.add(Arguments.of(dataManager, expectedData));
        }

        {
            Source<String> source = Mockito.mock(Source.class);
            DataManagerImpl<String, String, Integer> dataManager = new DataManagerImpl<>(source, JsonUtils::collectionFromJson);
            List<Map<String, Integer>> expectedData = new LinkedList<>();

            {
                {
                    Map<String, Integer> map = new HashMap<>();
                    map.put("WEIGHT", 1);
                    map.put("HEIGHT", 2);
                    map.put("SEX", 2);

                    expectedData.add(map);
                }

                {
                    Map<String, Integer> map = new HashMap<>();
                    map.put("WEIGHT", 2);
                    map.put("HEIGHT", 1);
                    map.put("SEX", 1);

                    expectedData.add(map);
                }

                {
                    Map<String, Integer> map = new HashMap<>();
                    map.put("WEIGHT", 1);
                    map.put("HEIGHT", 3);
                    map.put("SEX", 1);

                    expectedData.add(map);
                    expectedData.add(map);
                }
            }

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(expectedData));

            arguments.add(Arguments.of(dataManager, expectedData));
        }

        {
            Source<String> source = Mockito.mock(Source.class);
            DataManagerImpl<String, String, String> dataManager = new DataManagerImpl<>(source, JsonUtils::collectionFromJson);
            List<Map<String, String>> expectedData = new LinkedList<>();

            Mockito.when(source.extract()).thenReturn(JsonUtils.toJson(expectedData));

            arguments.add(Arguments.of(dataManager, expectedData));
        }

        return arguments.stream();
    }
}