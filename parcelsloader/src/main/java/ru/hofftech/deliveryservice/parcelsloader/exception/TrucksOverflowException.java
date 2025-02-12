package ru.hofftech.deliveryservice.parcelsloader.exception;

public class TrucksOverflowException extends RuntimeException {

    public TrucksOverflowException(Integer trucksCount, Integer trucksLimitCount) {
        super("Количество грузовиков превысило лимит: текущее количество - %d, лимит - %d"
                .formatted(trucksCount, trucksLimitCount));
    }
}
