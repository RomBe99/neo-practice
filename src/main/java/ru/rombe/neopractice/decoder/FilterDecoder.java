package ru.rombe.neopractice.decoder;

import ru.rombe.neopractice.util.JsonUtils;
import ru.rombe.neopractice.util.lex.Token;
import ru.rombe.neopractice.util.lex.exception.AnalyzerException;
import ru.rombe.neopractice.util.lex.predicate.PredicateLexicalAnalyzer;
import ru.rombe.neopractice.util.lex.predicate.PredicateTokens;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FilterDecoder implements Decoder<String, Map<String, Predicate<Map<String, String>>>> {
    private static List<Token> toReversePolishNotation(List<Token> tokens) {
        Stack<Token> operators = new Stack<>();
        List<Token> result = new LinkedList<>();

        Map<PredicateTokens, Consumer<Token>> tokensActions = new HashMap<>();
        tokensActions.put(PredicateTokens.VALUE, result::add);
        tokensActions.put(PredicateTokens.NEGATION, operators::push);
        tokensActions.put(PredicateTokens.OPEN_BRACKET, operators::push);
        tokensActions.put(PredicateTokens.CLOSE_BRACKET, t -> {
            while (PredicateTokens.OPEN_BRACKET != PredicateTokens.of(operators.peek().getAttribute())) {
                result.add(operators.pop());
            }

            operators.pop();
        });
        tokensActions.put(PredicateTokens.OR, t -> {
            try {
                while (PredicateTokens.NEGATION == PredicateTokens.of(operators.peek().getAttribute())
                        || PredicateTokens.AND == PredicateTokens.of(operators.peek().getAttribute())) {
                    result.add(operators.pop());
                }
            } catch (EmptyStackException ignored) {
            }

            operators.add(t);
        });
        tokensActions.put(PredicateTokens.AND, t -> {
            try {
                while (PredicateTokens.NEGATION == PredicateTokens.of(operators.peek().getAttribute())) {
                    result.add(operators.pop());
                }
            } catch (EmptyStackException ignored) {
            }

            operators.add(t);
        });

        for (Token t : tokens) {
            tokensActions.get(PredicateTokens.of(t.getAttribute())).accept(t);
        }

        while (!operators.isEmpty()) {
            result.add(operators.pop());
        }

        return result;
    }

    private static Predicate<Map<String, String>> decodeReversePolishNotation(List<Token> tokens) {
        Queue<Token> queue = new LinkedList<>(tokens);
        Stack<Predicate<Map<String, String>>> predicateStack = new Stack<>();
        Function<Token, Predicate<Map<String, String>>> valuePredicateProducer = token -> {
            String[] values = token.getValue().split("[.]");
            final String key = values[0];
            final String value = values[1];

            return m -> {
                String v = m.get(key);

                return v != null && v.equals(value);
            };
        };
        Map<PredicateTokens, Consumer<Token>> tokensActions = new HashMap<>();
        tokensActions.put(PredicateTokens.VALUE, t -> predicateStack.push(valuePredicateProducer.apply(t)));
        tokensActions.put(PredicateTokens.NEGATION, t -> Predicate.not(predicateStack.peek()));
        tokensActions.put(PredicateTokens.AND, t -> predicateStack.push(predicateStack.pop().and(predicateStack.pop())));
        tokensActions.put(PredicateTokens.OR, t -> predicateStack.push(predicateStack.pop().or(predicateStack.pop())));

        while (!queue.isEmpty()) {
            Token token = queue.remove();

            tokensActions.get(PredicateTokens.of(token.getAttribute())).accept(token);
        }

        return predicateStack.peek();
    }

    @Override
    public Map<String, Predicate<Map<String, String>>> decode(String jsonRules) throws AnalyzerException {
        Map<String, String> rulesMap = JsonUtils.mapFromJson(jsonRules);
        Map<String, Predicate<Map<String, String>>> result = new HashMap<>(rulesMap.size());

        for (String s : rulesMap.keySet()) {
            String rule = rulesMap.get(s);

            if (rule != null && !rule.isBlank()) {
                result.put(s, decodeReversePolishNotation(toReversePolishNotation(new PredicateLexicalAnalyzer(rule).getTokens())));
            }
        }

        return result;
    }
}
