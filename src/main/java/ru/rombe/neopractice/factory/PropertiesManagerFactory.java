package ru.rombe.neopractice.factory;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.decoder.JsonDecoders;
import ru.rombe.neopractice.manager.property.PropertiesManager;
import ru.rombe.neopractice.manager.property.PropertiesManagerImpl;
import ru.rombe.neopractice.source.FileSource;

import java.util.Map;
import java.util.Set;

public class PropertiesManagerFactory {
    public static <P, V> PropertiesManager<P, V> getPropertiesManagerFileBased(String sourceFilename, Decoder<String, Map<P, Set<V>>> decoder) {
        return new PropertiesManagerImpl<>(new FileSource(sourceFilename), decoder);
    }

    public static <P, V> PropertiesManager<P, V> getPropertiesManagerFileBasedWithJsonDecoder(String sourceFilename) {
        return getPropertiesManagerFileBased(sourceFilename, JsonDecoders::mapToSetFromJson);
    }
}