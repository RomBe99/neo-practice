package ru.rombe.neopractice.factory;

import ru.rombe.neopractice.decoder.FilterDecoder;
import ru.rombe.neopractice.manager.filter.FiltersManager;
import ru.rombe.neopractice.manager.filter.FiltersManagerImpl;
import ru.rombe.neopractice.manager.property.PropertiesManager;
import ru.rombe.neopractice.source.FileSource;

import java.util.Map;
import java.util.function.Predicate;

public class FiltersManagerFactory {
    public static FiltersManager<String, Predicate<Map<String, String>>> getFiltersManagerFileBased(String sourceFilename,
                                                                                                    PropertiesManager<String, String> propertiesManager) {
        return new FiltersManagerImpl<>(new FileSource(sourceFilename), new FilterDecoder(propertiesManager));
    }
}