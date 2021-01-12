package ru.rombe.neopractice.source;

public interface Source<R> {
    R extract() throws Exception;
}