package ru.hofftech.deliveryservice.parcelsloader.model.dto;

import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;

public record LoadingOptionsDto(LoadingAlgorithm algorithm,
                                Integer trucksCountLimit) {
}
