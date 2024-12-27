package ru.hofftech.delivery.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.InvalidAlgorithmException;

import java.util.Map;

@RequiredArgsConstructor
public class LoadingAlgorithmMapper {

    private static final Map<Integer, LoadingAlgorithm> ALGORITHM_MAP = Map.of(
            1, LoadingAlgorithm.ONE_TRUCK_PER_PARCEL,
            2, LoadingAlgorithm.WIDE_FIRST,
            3, LoadingAlgorithm.BALANCED

    );

    public LoadingAlgorithm mapIntToLoadingAlgorithm(Integer algorithmNumber) {
        if (!ALGORITHM_MAP.containsKey(algorithmNumber)) {
            throw new InvalidAlgorithmException(algorithmNumber);
        }

        return ALGORITHM_MAP.get(algorithmNumber);
    }
}
