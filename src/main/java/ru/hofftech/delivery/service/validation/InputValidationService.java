package ru.hofftech.delivery.service.validation;

public class InputValidationService {

    public void validateInputStringConvertibleToInteger(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void validateInputString(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
