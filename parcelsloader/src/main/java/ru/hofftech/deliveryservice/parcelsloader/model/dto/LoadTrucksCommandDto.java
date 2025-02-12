package ru.hofftech.deliveryservice.parcelsloader.model.dto;

import ru.hofftech.deliveryservice.parcelsloader.enums.LoadInputMode;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadOutputMode;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;

import java.util.List;

public record LoadTrucksCommandDto(String user,
                                   LoadInputMode inputMode,
                                   String parcelsSource,
                                   List<TruckOptionsDto> truckOptionDtos,
                                   LoadingAlgorithm loadingAlgorithm,
                                   LoadOutputMode outputMode,
                                   String outputFileName) {
}
