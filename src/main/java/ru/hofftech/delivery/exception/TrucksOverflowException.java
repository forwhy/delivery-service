package ru.hofftech.delivery.exception;

public class TrucksOverflowException extends RuntimeException {

    public TrucksOverflowException() {
        super("Trucks count exceeded the limit");
    }
}
