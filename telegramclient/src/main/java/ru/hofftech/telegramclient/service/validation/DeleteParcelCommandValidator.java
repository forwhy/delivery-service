package ru.hofftech.telegramclient.service.validation;

import org.springframework.stereotype.Component;

@Component
public class DeleteParcelCommandValidator {

    public void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение id(name) для удаления");
        }
    }
}
