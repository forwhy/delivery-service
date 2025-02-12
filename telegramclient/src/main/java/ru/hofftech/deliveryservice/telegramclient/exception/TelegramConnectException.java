package ru.hofftech.deliveryservice.telegramclient.exception;

public class TelegramConnectException extends RuntimeException {
    public TelegramConnectException(String message) {
        super(message);
    }
}
