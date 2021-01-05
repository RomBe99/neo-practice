package ru.rombe.neopractice;

import org.junit.jupiter.api.Assertions;
import ru.rombe.neopractice.util.JsonUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTest {
    public static void createFile(String filename) {
        try {
            Files.createFile(Paths.get(filename));
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    public static void deleteFile(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    public static void writeToFile(String filename, String data) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            writer.write(data);
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    public static String toJson(Object o){
        return JsonUtils.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }
}