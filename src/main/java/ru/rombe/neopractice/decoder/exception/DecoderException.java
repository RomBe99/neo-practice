package ru.rombe.neopractice.decoder.exception;

/**
 * Decoders can throw this exception.
 *
 * @see ru.rombe.neopractice.decoder.Decoder
 */
public class DecoderException extends Exception {
    private final DecoderErrorCodes errorCode;
    private final String token;

    /**
     * @param errorCode error code from enum DecoderErrorCodes
     * @param token     token with error
     * @see DecoderErrorCodes
     */
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

    @Override
    public String toString() {
        return "DecoderException{" +
                "errorCode=" + errorCode +
                ", token='" + token + '\'' +
                '}';
    }
}