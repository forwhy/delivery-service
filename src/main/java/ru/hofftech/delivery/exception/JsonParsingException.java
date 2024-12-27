package ru.hofftech.delivery.exception;

public class JsonParsingException extends RuntimeException {

    public JsonParsingException(String fileName) {
        super("Error occurred while parsing JSON file %s".formatted(fileName));
    }
}
