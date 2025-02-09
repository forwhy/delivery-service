package ru.hofftech.telegramclient.service.validation;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoadTrucksCommandValidator {

    public void validateCommandArguments(
            String user,
            String parcelsText,
            String parcelsFile,
            String truckVariants,
            String loadType,
            String outType,
            String outFile) {
        validateUser(user);
        validateParcelsSource(parcelsText, parcelsFile);
        validateTruckVariants(truckVariants);
        validateLoadType(loadType);
        validateOutType(outType);
        validateOutputParameters(outType, outFile);
    }

    private void validateUser(String user) {
        if (!isStringParameterProvided(user)) {
            throw new IllegalArgumentException("Не задан пользователь -u");
        }
    }

    private void validateParcelsSource(String parcelsText, String parcelsFile) {
        if (isStringParameterProvided(parcelsText) && isStringParameterProvided(parcelsFile)) {
                throw new IllegalArgumentException("Должен быть задан ровно один источник информации о посылках " +
                        "(-parcels-text ИЛИ -parcels-file)");
        }
        if (!isStringParameterProvided(parcelsText) && !isStringParameterProvided(parcelsFile)) {
            throw new IllegalArgumentException("Не задан источник информации о посылках (-parcels-text или -parcels-file)");
        }
    }

    private Boolean isStringParameterProvided(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }

    private void validateOutType(String outType) {
        if (!isStringParameterProvided(outType)) {
            throw new IllegalArgumentException("Неверно задан тип вывода результатов -out");
        }
    }

    private void validateOutputParameters(String outType, String outFile) {
        if (Objects.equals(outType, "json-file") && !isStringParameterProvided(outFile)) {
            throw new IllegalArgumentException("Не указано имя файла для вывода результата -out-filename");
        }
    }

    private void validateLoadType(String loadType) {
        if (!isStringParameterProvided(loadType)) {
            throw new IllegalArgumentException("Не задан алгоритм погрузки -type");
        }
    }

    private void validateTruckVariants(String truckVariants) {
        if (!isStringParameterProvided(truckVariants)) {
            throw new IllegalArgumentException("Не заданы доступные размеры грузовиков -trucks");
        }
    }
}
