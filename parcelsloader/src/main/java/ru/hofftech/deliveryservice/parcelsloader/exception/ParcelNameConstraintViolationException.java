package ru.hofftech.deliveryservice.parcelsloader.exception;

public class ParcelNameConstraintViolationException extends RuntimeException {
    public ParcelNameConstraintViolationException(String message) {
        super(message);
    }
}
