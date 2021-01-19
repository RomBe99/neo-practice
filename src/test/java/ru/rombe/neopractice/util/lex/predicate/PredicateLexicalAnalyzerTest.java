package ru.rombe.neopractice.util.lex.predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rombe.neopractice.util.lex.Token;
import ru.rombe.neopractice.util.lex.exception.AnalyzerErrorCodes;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;

import java.util.List;
import java.util.stream.Stream;
import static ru.rombe.neopractice.util.lex.predicate.PredicateTokens.*;

public class PredicateLexicalAnalyzerTest {
    private static Token getToken(PredicateTokens predicateToken) {
        return new Token(predicateToken.getAttribute(), predicateToken.getAttribute());
    }

    private static Token getValueToken(String value) {
        return new Token(VALUE.getAttribute(), value);
    }

    @ParameterizedTest
    @MethodSource("getTokensValues")
    public void getTokensTest(PredicateLexicalAnalyzer lexAnalyzer, List<Token> expectedTokens) {
        try {
            List<Token> actualTokens = lexAnalyzer.getTokens();

            Assertions.assertEquals(expectedTokens, actualTokens);
        } catch (AnalyzerException e) {
            Assertions.fail();
        }
    }

    @ParameterizedTest
    @MethodSource("tryGetIncorrectTokensValues")
    public void tryGetIncorrectTokensTest(PredicateLexicalAnalyzer lexAnalyzer) {
        try {
            lexAnalyzer.getTokens();
        } catch (AnalyzerException e) {
            Assertions.assertEquals(AnalyzerErrorCodes.UNKNOWN_TOKEN, e.getErrorCode());
        }
    }

    private static Stream<Arguments> getTokensValues() {
        return Stream.of(
                Arguments.of(new PredicateLexicalAnalyzer("NOT a.a AND b.b"),
                        List.of(getToken(NEGATION), getValueToken("a.a"), getToken(AND), getValueToken("b.b"))),
                Arguments.of(new PredicateLexicalAnalyzer("NOT (a.a OR b.b)"),
                        List.of(getToken(NEGATION), getToken(OPEN_BRACKET), getValueToken("a.a"), getToken(OR), getValueToken("b.b"), getToken(CLOSE_BRACKET))),
                Arguments.of(new PredicateLexicalAnalyzer("a.a"),
                        List.of(getValueToken("a.a"))),
                Arguments.of(new PredicateLexicalAnalyzer("NOT a.a         AND NOT ( z.z    OR        b.b) OR NOT x.x"),
                        List.of(getToken(NEGATION), getValueToken("a.a"), getToken(AND), getToken(NEGATION), getToken(OPEN_BRACKET), getValueToken("z.z"), getToken(OR), getValueToken("b.b"), getToken(CLOSE_BRACKET), getToken(OR), getToken(NEGATION), getValueToken("x.x")))
        );
    }

    private static Stream<Arguments> tryGetIncorrectTokensValues() {
        return Stream.of(
                Arguments.of(new PredicateLexicalAnalyzer("")),
                Arguments.of(new PredicateLexicalAnalyzer("NOTN a.a")),
                Arguments.of(new PredicateLexicalAnalyzer("a.a AR b.b")),
                Arguments.of(new PredicateLexicalAnalyzer("(.a)"))
        );
    }
}