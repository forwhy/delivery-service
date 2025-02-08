package ru.hofftech.parcelsloader.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadingAlgorithm {
    ONE_TRUCK_PER_PARCEL("Одна посылка - один грузовик"),
    WIDE_FIRST("Сначала широкие"),
    BALANCED("Равномерная погрузка");

    private final String algorithmName;
}
