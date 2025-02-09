package ru.hofftech.telegramclient.service.validation;

import org.springframework.stereotype.Component;

@Component
public class BillingCommandValidator {

    public void validateCommandArguments(String user) {
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан параметр пользователь --u");
        }
    }
}
