package ru.rombe.neopractice.data.source;

import java.util.Map;
import java.util.Set;

public interface DataSource {
    Set<Map<String, String>> extract() throws Exception;
}