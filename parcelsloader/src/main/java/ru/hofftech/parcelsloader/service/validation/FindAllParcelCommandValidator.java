package ru.hofftech.parcelsloader.service.validation;

import ru.hofftech.parcelsloader.model.dto.request.FindAllParcelCommandDto;

public class FindAllParcelCommandValidator {

    public void validate(FindAllParcelCommandDto dto) {
        if (!dto.offset().isEmpty() && dto.offset().get() < 0) {
            throw new IllegalArgumentException("Значение сдвига не может быть отрицательным");
        }
        if (!dto.limit().isEmpty() && dto.limit().get() < 0) {
            throw new IllegalArgumentException("Значение лимита выборки не может быть отрицательным");
        }
    }
}
