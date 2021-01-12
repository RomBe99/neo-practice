package ru.rombe.neopractice.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSource implements Source<String> {
    private Path filename;

    public FileSource(String filename) {
        setFilename(Path.of(filename));
    }

    public FileSource(Path filename) {
        setFilename(filename);
    }

    public void setFilename(Path filename) {
        this.filename = filename;
    }

    @Override
    public String extract() throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(filename)) {
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        return sb.toString();
    }
}