package ru.hofftech.delivery.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.InvalidAlgorithmException;

@RequiredArgsConstructor
public class LoadingAlgorithmMapper {

    public LoadingAlgorithm mapIntToLoadingAlgorithm(Integer algorithmNumber) {
        return switch (algorithmNumber) {
            case 1 -> LoadingAlgorithm.ONE_TRUCK_PER_PARCEL;
            case 2 -> LoadingAlgorithm.WIDE_FIRST;
            case 3 -> LoadingAlgorithm.BALANCED;
            default -> throw new InvalidAlgorithmException(algorithmNumber);
        };
    }
}
