package ru.rombe.neopractice.factory;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.data.DataManager;
import ru.rombe.neopractice.manager.data.DataManagerImpl;
import ru.rombe.neopractice.source.FileSource;
import ru.rombe.neopractice.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class DataManagerFactory {
    public static <P, V> DataManager<List<Map<P, V>>> getDataManagerFileBased(String sourceFilename,
                                                                              Decoder<String, List<Map<P, V>>> decoder) {
        return new DataManagerImpl<>(new FileSource(sourceFilename), decoder);
    }

    public static <P, V> DataManager<List<Map<P, V>>> getDataManagerFileBasedWithJsonDecoder(String sourceFilename) {
        return getDataManagerFileBased(sourceFilename, JsonUtils::collectionFromJson);
    }
}