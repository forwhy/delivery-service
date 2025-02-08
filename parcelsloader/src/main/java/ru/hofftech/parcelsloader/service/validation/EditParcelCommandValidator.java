package ru.hofftech.parcelsloader.service.validation;

import ru.hofftech.parcelsloader.model.dto.request.EditParcelCommandDto;

public class EditParcelCommandValidator {

    private static final Integer MAX_SYMBOL_LENGTH = 1;

    public void validate(EditParcelCommandDto commandDto) throws IllegalArgumentException {
        if (commandDto == null) {
            throw new IllegalArgumentException("Не переданы входные параметры");
        }

        validateId(commandDto.currentId());
        validateName(commandDto.name());
        validateSymbol(commandDto.symbol());
        validateForm(commandDto.form());
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

    private void validateForm(String form) {
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
}
