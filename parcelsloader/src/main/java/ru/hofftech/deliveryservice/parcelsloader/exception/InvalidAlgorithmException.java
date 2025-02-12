package ru.hofftech.deliveryservice.parcelsloader.exception;

public class InvalidAlgorithmException extends RuntimeException {

    public InvalidAlgorithmException(String algorithm) {
        super("Выбран несуществующий алгоритм: %s".formatted(algorithm));
    }
}
