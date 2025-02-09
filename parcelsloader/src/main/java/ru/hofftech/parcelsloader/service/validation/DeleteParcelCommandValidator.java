package ru.hofftech.parcelsloader.service.validation;

public class DeleteParcelCommandValidator {

    public void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение id(name) для удаления");
        }
    }
}
