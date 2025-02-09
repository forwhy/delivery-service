package ru.hofftech.parcelsloader.service.validation;

import lombok.RequiredArgsConstructor;
import ru.hofftech.parcelsloader.exception.InvalidParcelFormException;
import ru.hofftech.parcelsloader.model.dto.request.CreateParcelCommandDto;

@RequiredArgsConstructor
public class CreateParcelCommandValidator {

    private static final Integer MAX_SYMBOL_LENGTH = 1;
    private final ParcelValidator parcelValidator;

    public void validate(CreateParcelCommandDto commandDto)
            throws IllegalArgumentException, InvalidParcelFormException {
        if (commandDto == null) {
            throw new IllegalArgumentException("Не переданы входные параметры");
        }

        validateName(commandDto.name());
        validateSymbol(commandDto.symbol());
        validateForm(commandDto.form());
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

        if (!parcelValidator.isFormValid(form)) {
            throw new InvalidParcelFormException(form);
        }
    }

    private void validateSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Не указано значение параметра symbol");
        }

        if (symbol.length() > MAX_SYMBOL_LENGTH) {
            throw new IllegalArgumentException("Значением параметра symbol должен быть одиночный символ");
        }

        if (symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Символ посылки не может быть пробелом");
        }
    }
}
