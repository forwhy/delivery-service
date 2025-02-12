package ru.hofftech.deliveryservice.parcelsloader.exception;

public class JsonParsingException extends RuntimeException {

    public JsonParsingException(String fileName) {
        super("В процессе парсинга JSON файла %s возникла ошибка".formatted(fileName));
    }
}
