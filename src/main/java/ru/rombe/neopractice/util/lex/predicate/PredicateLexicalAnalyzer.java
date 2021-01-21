package ru.rombe.neopractice.util.lex.predicate;

import ru.rombe.neopractice.util.lex.LexicalAnalyzer;
import ru.rombe.neopractice.util.lex.Token;
import ru.rombe.neopractice.util.lex.exception.AnalyzerErrorCodes;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class using for lexical analysis and convert query string to tokens list.
 */
public class PredicateLexicalAnalyzer implements LexicalAnalyzer<Token> {
    private static final List<Character> skipChars = List.of(' ');

    private final Queue<Character> query = new LinkedList<>();

    /**
     * @param query string for lexical analysis
     */
    public PredicateLexicalAnalyzer(String query) {
        setQuery(query);
    }

    /**
     * Check character for parenthesis.
     *
     * @param c char for check
     * @return if the character is a closing or opening parenthesis return true else false
     */
    private boolean isBracket(Character c) {
        String temp = String.valueOf(c);

        return temp.matches(PredicateTokens.OPEN_BRACKET.getRegexp())
                || temp.matches(PredicateTokens.CLOSE_BRACKET.getRegexp());
    }


    /**
     * Check query char queue on emptiness.
     *
     * @return if queue empty return false else true
     */
    private boolean hasNext() {
        return !query.isEmpty();
    }

    /**
     * Skips whitespace in the queue until no whitespace is found.
     */
    private void skipWhiteSpaces() {
        while (skipChars.contains(query.peek())) {
            query.poll();
        }
    }

    /**
     * Parse next lexeme in queue.
     *
     * @return lexeme from queue
     */
    private String readLexeme() {
        if (isBracket(query.peek())) {
            return String.valueOf(query.poll());
        }

        StringBuilder sb = new StringBuilder();

        while (hasNext() && !skipChars.contains(query.peek())) {
            sb.append(query.poll());

            if (isBracket(query.peek())) {
                break;
            }
        }

        return sb.toString();
    }

    /**
     * Generate next token from queue.
     *
     * @return token from queue
     * @throws AnalyzerException if token not found
     * @see AnalyzerException
     */
    private Token generateToken() throws AnalyzerException {
        String lexeme = readLexeme();

        for (PredicateTokens t : PredicateTokens.values()) {
            if (lexeme.matches(t.getRegexp())) {
                return new Token(t.getAttribute(), lexeme);
            }
        }

        throw new AnalyzerException(AnalyzerErrorCodes.UNKNOWN_TOKEN, lexeme);
    }

    /**
     * Get all tokens from query.
     *
     * @return tokens list
     * @throws AnalyzerException if token not found
     * @see AnalyzerException
     */
    @Override
    public List<Token> getTokens() throws AnalyzerException {
        List<Token> result = new LinkedList<>();

        while (hasNext()) {
            result.add(generateToken());

            if (hasNext()) {
                skipWhiteSpaces();
            }
        }

        return result;
    }

    /**
     * Set new query.
     *
     * @param query string for lexical analysis
     */
    public void setQuery(String query) {
        this.query.clear();

        char[] chars = query.toCharArray();

        for (char c : chars) {
            this.query.add(c);
        }
    }
}