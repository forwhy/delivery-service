package ru.hofftech.parcelsloader.model.dto;

import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;

public record LoadingOptionsDto(LoadingAlgorithm algorithm,
                                Integer trucksCountLimit) {
}
