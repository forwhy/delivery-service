package ru.hofftech.delivery.exception;

public class InvalidAlgorithmException extends RuntimeException {

    public InvalidAlgorithmException() {
        super("Invalid algorithm was chosen");
    }
}
