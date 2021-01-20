package ru.rombe.neopractice.decoder;

import ru.rombe.neopractice.decoder.exception.DecoderErrorCodes;
import ru.rombe.neopractice.decoder.exception.DecoderException;
import ru.rombe.neopractice.manager.property.PropertiesManager;
import ru.rombe.neopractice.util.JsonUtils;
import ru.rombe.neopractice.util.lex.Token;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;
import ru.rombe.neopractice.util.lex.predicate.PredicateLexicalAnalyzer;
import ru.rombe.neopractice.util.lex.predicate.PredicateTokens;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This decoder encode String to Map<String, Predicate<Map<String, String>>>.
 */
public class FilterDecoder implements Decoder<String, Map<String, Predicate<Map<String, String>>>> {
    private static final String regexSeparator = "[.]";

    private final Predicate<Token> containsPropAndVal;

    /**
     * @param propertiesManager properties manager for checking properties and values
     * @see PropertiesManager
     */
    public FilterDecoder(PropertiesManager<String, String> propertiesManager) {
        containsPropAndVal = t -> {
            String[] propertyAndValue = t.getValue().split(regexSeparator);

            return propertiesManager.containsPropertyValue(propertyAndValue[0], propertyAndValue[1]);
        };
    }

    /**
     * Convert token to predicate token.
     *
     * @param token converting token
     * @return converted token
     * @see Token
     * @see PredicateTokens
     */
    private static PredicateTokens toPredicateToken(Token token) {
        return PredicateTokens.of(token.getAttribute());
    }

    /**
     * Convert tokens list in infix notation to tokens list in prefix notation (reverse polish notation).
     *
     * @param tokens tokens list parsed from string
     * @return tokens list in prefix notation (reverse polish notation)
     * @see Token
     */
    private static List<Token> toReversePolishNotation(List<Token> tokens) {
        Stack<Token> operators = new Stack<>();
        List<Token> result = new LinkedList<>();

        Map<PredicateTokens, Consumer<Token>> tokensActions = new HashMap<>();
        tokensActions.put(PredicateTokens.VALUE, result::add);
        tokensActions.put(PredicateTokens.NEGATION, operators::push);
        tokensActions.put(PredicateTokens.OPEN_BRACKET, operators::push);
        tokensActions.put(PredicateTokens.CLOSE_BRACKET, t -> {
            while (PredicateTokens.OPEN_BRACKET != toPredicateToken(operators.peek())) {
                result.add(operators.pop());
            }

            operators.pop();
        });
        tokensActions.put(PredicateTokens.OR, t -> {
            try {
                while (PredicateTokens.NEGATION == toPredicateToken(operators.peek())
                        || PredicateTokens.AND == toPredicateToken(operators.peek())) {
                    result.add(operators.pop());
                }
            } catch (EmptyStackException ignored) {
            }

            operators.add(t);
        });
        tokensActions.put(PredicateTokens.AND, t -> {
            try {
                while (PredicateTokens.NEGATION == toPredicateToken(operators.peek())) {
                    result.add(operators.pop());
                }
            } catch (EmptyStackException ignored) {
            }

            operators.add(t);
        });

        for (Token t : tokens) {
            tokensActions.get(toPredicateToken(t)).accept(t);
        }

        while (!operators.isEmpty()) {
            result.add(operators.pop());
        }

        return result;
    }

    /**
     * Convert tokens list in prefix notation to predicate.
     *
     * @param tokens tokens list in prefix notation
     * @return predicate
     * @see Token
     */
    private static Predicate<Map<String, String>> decodeReversePolishNotation(List<Token> tokens) {
        Queue<Token> queue = new LinkedList<>(tokens);
        Stack<Predicate<Map<String, String>>> predicateStack = new Stack<>();
        Function<Token, Predicate<Map<String, String>>> valuePredicateProducer = token -> {
            String[] values = token.getValue().split(regexSeparator);
            final String key = values[0];
            final String value = values[1];

            return m -> {
                String v = m.get(key);

                return v != null && v.equals(value);
            };
        };
        Map<PredicateTokens, Consumer<Token>> tokensActions = new HashMap<>();
        tokensActions.put(PredicateTokens.VALUE, t -> predicateStack.push(valuePredicateProducer.apply(t)));
        tokensActions.put(PredicateTokens.NEGATION, t -> predicateStack.push(predicateStack.pop().negate()));
        tokensActions.put(PredicateTokens.AND, t -> predicateStack.push(predicateStack.pop().and(predicateStack.pop())));
        tokensActions.put(PredicateTokens.OR, t -> predicateStack.push(predicateStack.pop().or(predicateStack.pop())));

        while (!queue.isEmpty()) {
            Token token = queue.remove();

            tokensActions.get(toPredicateToken(token)).accept(token);
        }

        return predicateStack.peek();
    }

    /**
     * Decode json string with mapped id to predicates.
     *
     * @param jsonRules json string with mapped id to predicates
     * @return mapped id to predicates
     * @throws AnalyzerException if analyzer find error
     * @throws DecoderException  if decoder find unknown property or value
     */
    @Override
    public Map<String, Predicate<Map<String, String>>> decode(String jsonRules) throws AnalyzerException, DecoderException {
        Map<String, String> rulesMap = JsonUtils.mapFromJson(jsonRules);
        Map<String, Predicate<Map<String, String>>> result = new HashMap<>(rulesMap.size());

        for (String s : rulesMap.keySet()) {
            String rule = rulesMap.get(s);

            if (rule != null && !rule.isBlank()) {
                List<Token> tokens = new PredicateLexicalAnalyzer(rule).getTokens();
                tokens = toReversePolishNotation(tokens);

                for (Token t : tokens) {
                    if (PredicateTokens.VALUE == toPredicateToken(t) && !containsPropAndVal.test(t)) {
                        throw new DecoderException(DecoderErrorCodes.UNKNOWN_PROPERTY_OR_VALUE, t.toString());
                    }
                }

                result.put(s, decodeReversePolishNotation(tokens));
            }
        }

        return result;
    }
}