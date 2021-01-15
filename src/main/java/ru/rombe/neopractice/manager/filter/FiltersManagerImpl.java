package ru.rombe.neopractice.manager.filter;

import ru.rombe.neopractice.decoder.Decoder;
import ru.rombe.neopractice.manager.AbstractManager;
import ru.rombe.neopractice.source.Source;

import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

public class FiltersManagerImpl<SR, FI, PA> extends AbstractManager<SR, Map<FI, Predicate<PA>>>
        implements FiltersManager<FI, Predicate<PA>> {
    private Map<FI, Predicate<PA>> filterIdToPredicate = Collections.emptyMap();

    public FiltersManagerImpl(Source<SR> source, Decoder<SR, Map<FI, Predicate<PA>>> decoder) {
        super(source, decoder);
    }

    @Override
    public void update() throws Exception {
        SR extractedData = source.extract();
        filterIdToPredicate = decoder.decode(extractedData);
    }

    @Override
    public Predicate<PA> getRule(FI filterId) {
        return filterIdToPredicate.get(filterId);
    }
}