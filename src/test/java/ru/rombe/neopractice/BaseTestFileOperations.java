package ru.rombe.neopractice;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTestFileOperations {
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
}