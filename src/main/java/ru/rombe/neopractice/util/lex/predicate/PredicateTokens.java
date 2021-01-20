package ru.rombe.neopractice.util.lex.predicate;

import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Enum for predicate operations.
 * Additionally contains regular expressions for recognizing operators and operands.
 */
public enum PredicateTokens {
    NEGATION("NOT", "NOT"),
    AND("AND", "AND"),
    OR("OR", "OR"),
    OPEN_BRACKET("(", "[(]"),
    CLOSE_BRACKET(")", "[)]"),
    VALUE("value", "[a-zA-z]++\\.[a-zA-z]++");

    private static final Map<String, PredicateTokens> operatorValues = Stream.of(values())
            .collect(Collectors.toMap(op -> op.attribute, op -> op));

    private final String attribute;
    private final String regexp;

    PredicateTokens(String attribute, String regexp) {
        this.attribute = attribute;
        this.regexp = regexp;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getRegexp() {
        return regexp;
    }

    /**
     * Return instance of PredicateTokens by his attribute.
     *
     * @param attribute attribute of PredicateTokens
     * @return one of PredicateTokens
     */
    public static PredicateTokens of(String attribute) {
        return operatorValues.get(attribute);
    }
}