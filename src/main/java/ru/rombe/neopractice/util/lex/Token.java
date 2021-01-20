package ru.rombe.neopractice.util.lex;

import java.util.Objects;

/**
 * Class serving as a container for tokens.
 */
public class Token {
    private final String attribute;
    private final String value;

    public Token(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(attribute, token.attribute) && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }

    @Override
    public String toString() {
        return "Token{" +
                "attribute='" + attribute + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}