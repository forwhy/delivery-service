package ru.hofftech.delivery.model.dto;

import ru.hofftech.delivery.enums.LoadingAlgorithm;

public record LoadingOptionsDto(LoadingAlgorithm algorithm, Integer trucksCountLimit) {
}
