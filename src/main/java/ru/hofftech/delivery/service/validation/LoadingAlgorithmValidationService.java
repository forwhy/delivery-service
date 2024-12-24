package ru.hofftech.delivery.service.validation;

import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.InvalidAlgorithmException;

import java.util.Arrays;

public class LoadingAlgorithmValidationService {

    public void validateAlgorithmNumber(Integer algorithmNumber) {
        if (Arrays.stream(LoadingAlgorithm.values()).noneMatch(a -> a.getAlgorithmNumber().equals(algorithmNumber))) {
            throw new InvalidAlgorithmException(algorithmNumber);
        }
    }
}
