package ru.hofftech.delivery.exception;

public class InvalidAttemptToPutParcelIntoTruckException extends RuntimeException {

    public InvalidAttemptToPutParcelIntoTruckException() {
        super("Invalid attempt to put parcel into truck");
    }
}
