package ru.hofftech.parcelsloader.service.validation;

public class FindBillingCommandValidator {

    public void validate(String user) {
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("Пользователь не задан");
        }
    }
}
