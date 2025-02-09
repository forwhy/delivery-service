package ru.hofftech.parcelsloader.service.validation;

import ru.hofftech.parcelsloader.model.dto.request.UnloadTrucksCommandDto;

public class UnloadTrucksCommandValidator {

    public void validate(UnloadTrucksCommandDto dto) {
        validateUser(dto.user());
        validateFile(dto.sourceFileName(), "infile");
        validateFile(dto.targetFileName(), "outfile");
    }

    private void validateUser(String user) {
        if (!isStringParameterProvided(user)) {
            throw new IllegalArgumentException("Не задан пользователь");
        }
    }

    private void validateFile(String fileName, String parameterName) {
        if (!isStringParameterProvided(fileName)) {
            throw new IllegalArgumentException(String.format("Не указано значение %s", parameterName));
        }
    }

    private Boolean isStringParameterProvided(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }
}
