package ru.hofftech.delivery.exception;

public class TrucksOverflowException extends RuntimeException {

    public TrucksOverflowException(Integer trucksCount, Integer trucksLimitCount) {
        super("Trucks count exceeded the limit: current trucks number - %d, limit - %d"
                .formatted(trucksCount, trucksLimitCount));
    }
}
