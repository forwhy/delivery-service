package ru.hofftech.parcelsloader.service.validation;

public class FindParcelCommandValidator {

    public void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение id(name) для поиска");
        }
    }
}
