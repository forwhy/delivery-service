package ru.hofftech.deliveryservice.parcelsloader.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.exception.InvalidAlgorithmException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoadingAlgorithmMapper {

    private static final Map<String, LoadingAlgorithm> ALGORITHM_MAP = Map.of(
            "Одна посылка - один грузовик", LoadingAlgorithm.ONE_TRUCK_PER_PARCEL,
            "Сначала широкие", LoadingAlgorithm.WIDE_FIRST,
            "Равномерная погрузка", LoadingAlgorithm.BALANCED

    );

    public LoadingAlgorithm mapNameToLoadingAlgorithm(String algorithmName) {
        if (!ALGORITHM_MAP.containsKey(algorithmName)) {
            throw new InvalidAlgorithmException(algorithmName);
        }

        return ALGORITHM_MAP.get(algorithmName);
    }
}
