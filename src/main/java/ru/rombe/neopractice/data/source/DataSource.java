package ru.rombe.neopractice.data.source;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DataSource {
    Set<List<String>> extract() throws IOException;
}