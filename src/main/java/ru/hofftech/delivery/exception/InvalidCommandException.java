package ru.hofftech.delivery.exception;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException(Integer command) {
        super("Invalid command was chosen: %d".formatted(command));
    }
}
