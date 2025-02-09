package ru.hofftech.telegramclient.service.validation;

import org.springframework.stereotype.Component;

@Component
public class UnloadTrucksCommandValidator {

    public void validateCommandArguments(String user, String inFile, String outFile) {
        validateUser(user);
        validateFile(inFile, "infile");
        validateFile(outFile, "outfile");
    }

    private void validateUser(String user) {
        if (!isStringParameterProvided(user)) {
            throw new IllegalArgumentException("Не задан пользователь -u");
        }
    }

    private void validateFile(String fileName, String parameterName) {
        if (!isStringParameterProvided(fileName)) {
            throw new IllegalArgumentException("Не указано значение " + parameterName);
        }
    }

    private Boolean isStringParameterProvided(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }
}
