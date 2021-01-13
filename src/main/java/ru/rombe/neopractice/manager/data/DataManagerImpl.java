package ru.rombe.neopractice.manager.data;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataManagerImpl<SR, P, V> extends AbstractManager<SR, List<Map<P, V>>> implements DataManager<List<Map<P, V>>> {
    private List<Map<P, V>> data = Collections.emptyList();

    public DataManagerImpl(Source<SR> propertiesSource, Decoder<SR, List<Map<P, V>>> decoder) {
        super(propertiesSource, decoder);
    }

    @Override
    public List<Map<P, V>> getData() {
        return data;
    }

    @Override
    public void update() throws Exception {
        SR extractedData = source.extract();
        data = decoder.decode(extractedData);
    }
}