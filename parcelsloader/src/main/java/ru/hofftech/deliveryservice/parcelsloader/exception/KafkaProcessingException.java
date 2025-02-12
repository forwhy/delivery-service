package ru.hofftech.deliveryservice.parcelsloader.exception;

public class KafkaProcessingException extends RuntimeException {
    public KafkaProcessingException(String message) {
        super(message);
    }
}
