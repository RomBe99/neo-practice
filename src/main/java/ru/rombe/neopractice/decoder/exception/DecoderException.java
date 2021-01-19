package ru.rombe.neopractice.decoder.exception;

public class DecoderException extends Exception {
    private final DecoderErrorCodes errorCode;
    private final String token;

    public DecoderException(DecoderErrorCodes errorCode, String token) {
        this.errorCode = errorCode;
        this.token = token;
    }

    public DecoderErrorCodes getErrorCode() {
        return errorCode;
    }

    public String getToken() {
        return token;
    }
}