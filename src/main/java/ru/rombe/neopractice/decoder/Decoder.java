package ru.rombe.neopractice.decoder;

/**
 * Classes implementing this interface can decode encoded data.
 *
 * @param <T> type of encoded data
 * @param <R> type of decoded data
 */
@FunctionalInterface
public interface Decoder<T, R> {
    /**
     * This method decode encoded data.
     *
     * @param encodedData encoded data
     * @return decoded data
     * @throws Exception if something going wrong decoder can throw checked exception
     */
    R decode(T encodedData) throws Exception;
}