package ru.rombe.neopractice.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class can extract data from file.
 *
 * @see Source
 */
public class FileSource implements Source<String> {
    private Path filename;

    /**
     * @param filename filename
     */
    public FileSource(String filename) {
        setFilename(Path.of(filename));
    }

    public void setFilename(Path filename) {
        this.filename = filename;
    }

    /**
     * Extract encoded data from file.
     *
     * @return encoded data
     * @throws IOException when an error occurs while reading a file
     * @see Source
     */
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