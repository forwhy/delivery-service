package ru.hofftech.delivery.service.validation;

public class InputValidationService {

    public void validateInputStringConvertibleToInteger(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input value cannot be empty");
        }

        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("Integer value expected, but got %s".formatted(input));
        }
    }

    public void validateInputString(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input value cannot be empty");
        }
    }
}
