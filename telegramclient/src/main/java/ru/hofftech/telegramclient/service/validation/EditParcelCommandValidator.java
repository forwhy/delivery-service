package ru.hofftech.telegramclient.service.validation;

import org.springframework.stereotype.Component;


@Component
public class EditParcelCommandValidator {

    private static final Character EMPTY_SPACE = ' ';
    private static final String ALLOWED_CHARACTERS_PATTERN = "^[%s]+$";
    private static final String LINE_DELIMITER = "%n";
    private static final Integer MAX_SYMBOL_LENGTH = 1;
    private static final Integer FIRST_SYMBOL_INDEX = 0;

    public void validateCommandArguments(String id, String name, String form, String symbol) {
        validateId(id);
        validateName(name);
        validateSymbol(symbol);
        validateForm(form, symbol.charAt(FIRST_SYMBOL_INDEX));
    }

    private void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение параметра id");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение параметра name");
        }
    }

    private void validateForm(String form, Character symbol) {
        if (form == null || form.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение параметра form");
        }
    }

    private void validateSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение параметра symbol");
        }

        if (symbol.length() > MAX_SYMBOL_LENGTH) {
            throw new IllegalArgumentException("Значением параметра symbol должен быть одиночный символ");
        }
    }

    private String getAllowedCharactersPattern(Character formSymbol) {
        return ALLOWED_CHARACTERS_PATTERN.formatted(formSymbol + EMPTY_SPACE + LINE_DELIMITER);
    }
}
