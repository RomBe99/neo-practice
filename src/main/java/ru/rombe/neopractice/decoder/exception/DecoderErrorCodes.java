package ru.rombe.neopractice.decoder.exception;

/**
 * Enum with error codes for DecoderException.
 *
 * @see DecoderException
 */
public enum DecoderErrorCodes {
    UNKNOWN_PROPERTY_OR_VALUE("Unknown property or value");

    private final String errorDescription;

    DecoderErrorCodes(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}