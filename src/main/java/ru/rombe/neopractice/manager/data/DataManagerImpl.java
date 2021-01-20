package ru.rombe.neopractice.manager.data;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class store data fpr analysis.
 *
 * @param <SR> source returned value type
 * @param <P>  keys type
 * @param <V>  values type
 * @see ru.rombe.neopractice.manager.AbstractManager
 * @see ru.rombe.neopractice.manager.data.DataManager
 * @see ru.rombe.neopractice.manager.Updatable
 * @see Source
 */
public class DataManagerImpl<SR, P, V> extends AbstractManager<SR, List<Map<P, V>>> implements DataManager<List<Map<P, V>>> {
    private List<Map<P, V>> data = Collections.emptyList();

    /**
     * @param source  data source
     * @param decoder decoder for encoded data extracted from source
     * @see Source
     * @see Decoder
     */
    public DataManagerImpl(Source<SR> source, Decoder<SR, List<Map<P, V>>> decoder) {
        super(source, decoder);
    }

    /**
     * Return already extracted and decoded data.
     *
     * @return data for analysis
     * @see DataManager
     */
    @Override
    public List<Map<P, V>> getData() {
        return data;
    }

    /**
     * Method extract and decode data for analysis.
     *
     * @throws Exception from source or decoder
     * @see ru.rombe.neopractice.manager.Updatable
     * @see Source
     * @see Decoder
     */
    @Override
    public void update() throws Exception {
        SR extractedData = source.extract();
        data = decoder.decode(extractedData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataManagerImpl<?, ?, ?> that = (DataManagerImpl<?, ?, ?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}