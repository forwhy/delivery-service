package ru.hofftech.parcelsloader.service.validation;

import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.parcelsloader.model.dto.request.LoadTrucksCommandDto;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class LoadTrucksCommandValidator {

    public void validate(LoadTrucksCommandDto dto) {
        validateUser(dto.user());
        validateParcelsSource(dto.parcelsText(), dto.parcelsFile());
        validateTruckVariants(dto.trucks());
        validateLoadType(dto.type());
        validateOutType(dto.out());
        validateOutputParameters(dto.out(), dto.outFilename());
    }

    private void validateUser(String user) {
        if (!isStringParameterProvided(user)) {
            throw new IllegalArgumentException("Не задан пользователь -u");
        }
    }

    private void validateParcelsSource(Optional<String> parcelsText, Optional<String> parcelsFile) {
        if (parcelsText.isPresent() && parcelsFile.isPresent()) {
            throw new IllegalArgumentException("Должен быть задан ровно один источник информации о посылках " +
                    "(-parcels-text ИЛИ -parcels-file)");
        }
        if (parcelsText.isEmpty()&& parcelsFile.isEmpty()) {
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

    private void validateOutputParameters(String outType, Optional<String> outFile) {
        if (Objects.equals(outType, "json-file") && outFile.isEmpty()) {
            throw new IllegalArgumentException("Не указано имя файла для вывода результата -out-filename");
        }
    }

    private void validateLoadType(String loadType) {
        if (!isStringParameterProvided(loadType)) {
            throw new IllegalArgumentException("Не задан алгоритм погрузки -type");
        }

        if (Arrays.stream(LoadingAlgorithm.values())
                .noneMatch(algorithm -> algorithm.getAlgorithmName().equals(loadType))) {
            throw new IllegalArgumentException(String.format("Неизвестный алгоритм погрузки %s", loadType));
        }
    }

    private void validateTruckVariants(String truckVariants) {
        if (!isStringParameterProvided(truckVariants)) {
            throw new IllegalArgumentException("Не заданы доступные размеры грузовиков -trucks");
        }
    }
}
