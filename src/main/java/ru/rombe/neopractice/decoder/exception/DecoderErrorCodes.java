package ru.rombe.neopractice.decoder.exception;

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