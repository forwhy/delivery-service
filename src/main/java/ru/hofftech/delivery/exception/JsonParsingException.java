package ru.hofftech.delivery.exception;

public class JsonParsingException extends RuntimeException {

    public JsonParsingException() {
        super("Error occurred while parsing JSON");
    }
}
