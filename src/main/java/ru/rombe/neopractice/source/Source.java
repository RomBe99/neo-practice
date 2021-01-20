package ru.rombe.neopractice.source;

/**
 * Classes implemented this interface can extract data from custom source (file, ethernet e.g).
 *
 * @param <R> type of data extracted from source
 */
public interface Source<R> {
    /**
     * Extract encoded data from source.
     *
     * @return encoded data from source
     * @throws Exception if extracting process can't end success
     */
    R extract() throws Exception;
}