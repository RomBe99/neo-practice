package ru.rombe.neopractice.manager.data;

import ru.rombe.neopractice.manager.Updatable;

/**
 * Classes implementing this interface store data for analysis.
 *
 * @param <R> type of data stored in manager
 */
public interface DataManager<R> extends Updatable {
    /**
     * Method return data for analysis.
     *
     * @return data for analysis
     */
    R getData();
}