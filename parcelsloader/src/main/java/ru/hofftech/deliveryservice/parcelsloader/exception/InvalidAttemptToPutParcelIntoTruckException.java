package ru.hofftech.deliveryservice.parcelsloader.exception;

public class InvalidAttemptToPutParcelIntoTruckException extends RuntimeException {

    public InvalidAttemptToPutParcelIntoTruckException(String message) {
        super(message);
    }
}
