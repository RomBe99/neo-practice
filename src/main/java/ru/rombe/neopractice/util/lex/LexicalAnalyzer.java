package ru.rombe.neopractice.util.lex;

import java.util.List;

public interface LexicalAnalyzer<R> {
    List<R> getTokens() throws Exception;
}