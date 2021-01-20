package ru.rombe.neopractice.manager.filter;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class store filters fpr analysis.
 *
 * @param <SR> source returned value type
 * @param <FI> type of filter id
 * @param <PA> type of argument accepted by predicate
 * @see ru.rombe.neopractice.manager.AbstractManager
 * @see ru.rombe.neopractice.manager.filter.FiltersManager
 * @see ru.rombe.neopractice.manager.Updatable
 * @see Source
 */
public class FiltersManagerImpl<SR, FI, PA> extends AbstractManager<SR, Map<FI, Predicate<PA>>>
        implements FiltersManager<FI, Predicate<PA>> {
    private Map<FI, Predicate<PA>> filterIdToPredicate = Collections.emptyMap();

    /**
     * @param source  filters source
     * @param decoder decoder for encoded data extracted from source
     * @see Source
     * @see Decoder
     */
    public FiltersManagerImpl(Source<SR> source, Decoder<SR, Map<FI, Predicate<PA>>> decoder) {
        super(source, decoder);
    }

    /**
     * Method extract and decode filters.
     *
     * @throws Exception from source or decoder
     * @see ru.rombe.neopractice.manager.Updatable
     * @see Source
     * @see Decoder
     */
    @Override
    public void update() throws Exception {
        SR extractedData = source.extract();
        filterIdToPredicate = decoder.decode(extractedData);
    }

    /**
     * Return filter by filter id.
     *
     * @param filterId filter id
     * @return predicate filter
     * @see FiltersManager
     */
    @Override
    public Predicate<PA> getFilter(FI filterId) {
        return filterIdToPredicate.get(filterId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FiltersManagerImpl<?, ?, ?> that = (FiltersManagerImpl<?, ?, ?>) o;
        return Objects.equals(filterIdToPredicate, that.filterIdToPredicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), filterIdToPredicate);
    }
}