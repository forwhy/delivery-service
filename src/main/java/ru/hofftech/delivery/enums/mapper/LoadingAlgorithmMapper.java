package ru.hofftech.delivery.enums.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.InvalidAlgorithmException;
import ru.hofftech.delivery.service.validation.LoadingAlgorithmValidationService;

@RequiredArgsConstructor
public class LoadingAlgorithmMapper {
    private final LoadingAlgorithmValidationService loadingAlgorithmValidationService;

    public LoadingAlgorithm mapIntToLoadingAlgorithm(Integer algorithmNumber) {
        loadingAlgorithmValidationService.validateAlgorithmNumber(algorithmNumber);
        return switch (algorithmNumber) {
            case 1 -> LoadingAlgorithm.ONE_TRUCK_PER_PARCEL;
            case 2 -> LoadingAlgorithm.WIDE_FIRST;
            case 3 -> LoadingAlgorithm.BALANCED;
            default -> throw new InvalidAlgorithmException(algorithmNumber);
        };
    }
}
