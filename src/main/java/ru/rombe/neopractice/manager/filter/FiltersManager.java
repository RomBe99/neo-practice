package ru.rombe.neopractice.manager.filter;

import ru.rombe.neopractice.manager.Updatable;

/**
 * Classes implemented this interface store filters for analysis.
 *
 * @param <FI> type of filter id
 * @param <F>  type of filter
 */
public interface FiltersManager<FI, F> extends Updatable {
    /**
     * Return filter by filter id.
     *
     * @param filterId filter id
     * @return filter
     */
    F getFilter(FI filterId);
}