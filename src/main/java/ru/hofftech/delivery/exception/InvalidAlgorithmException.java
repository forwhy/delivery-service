package ru.hofftech.delivery.exception;

public class InvalidAlgorithmException extends RuntimeException {

    public InvalidAlgorithmException(Integer algorithm) {
        super("Invalid algorithm was chosen: %d".formatted(algorithm));
    }
}
