package ru.rombe.neopractice.util.lex.exception;

/**
 * Enum with error codes for AnalyzerException.
 *
 * @see AnalyzerException
 */
public enum AnalyzerErrorCodes {
    UNKNOWN_TOKEN("Unknown token");

    private final String errorDescription;

    AnalyzerErrorCodes(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
