package ru.rombe.neopractice.util.lex.predicate;

import ru.rombe.neopractice.util.lex.LexicalAnalyzer;
import ru.rombe.neopractice.util.lex.Token;
import ru.rombe.neopractice.util.lex.exception.AnalyzerErrorCodes;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PredicateLexicalAnalyzer implements LexicalAnalyzer<Token> {
    private static final List<Character> skipChars = List.of(' ');

    private final Queue<Character> query = new LinkedList<>();

    public PredicateLexicalAnalyzer(String query) {
        setQuery(query);
    }

    private boolean isBracket(Character c) {
        String temp = String.valueOf(c);

        return temp.matches(PredicateTokens.OPEN_BRACKET.getRegexp()) || temp.matches(PredicateTokens.CLOSE_BRACKET.getRegexp());
    }

    private boolean hasNext() {
        return !query.isEmpty();
    }

    private void skipWhiteSpaces() {
        while (skipChars.contains(query.peek())) {
            query.poll();
        }
    }

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

    private Token generateToken() throws AnalyzerException {
        String lexeme = readLexeme();

        for (PredicateTokens t : PredicateTokens.values()) {
            if (lexeme.matches(t.getRegexp())) {
                return new Token(t.getAttribute(), lexeme);
            }
        }

        throw new AnalyzerException(AnalyzerErrorCodes.UNKNOWN_TOKEN, lexeme);
    }

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

    public void setQuery(String query) {
        this.query.clear();

        char[] chars = query.toCharArray();

        for (char c : chars) {
            this.query.add(c);
        }
    }
}