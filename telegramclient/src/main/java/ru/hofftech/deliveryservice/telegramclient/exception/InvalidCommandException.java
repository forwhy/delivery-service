package ru.hofftech.deliveryservice.telegramclient.exception;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException(String command) {
        super("Передана некорректная команда: %s".formatted(command));
    }
}
