package ru.rombe.neopractice.decoder;

@FunctionalInterface
public interface Decoder<T, R> {
    R decode(T encodedData) throws Exception;
}