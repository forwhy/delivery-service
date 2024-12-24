package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;

@RequiredArgsConstructor
@Getter
public class LoadingOptionsDto {
    private final LoadingAlgorithm algorithm;
    private final Integer trucksCountLimit;
}
