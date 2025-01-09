package ru.hofftech.delivery.exception;

public class InvalidAttemptToPutParcelIntoTruckException extends RuntimeException {

    public InvalidAttemptToPutParcelIntoTruckException(String message) {
        super(message);
    }
}
