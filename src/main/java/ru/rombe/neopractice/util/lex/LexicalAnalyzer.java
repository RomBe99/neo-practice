package ru.rombe.neopractice.util.lex;

import java.util.List;

/**
 * Classes implemented this interface can do lexical analysis.
 *
 * @param <R> type of token
 */
public interface LexicalAnalyzer<R> {
    List<R> getTokens() throws Exception;
}