package ru.hofftech.delivery.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadingAlgorithm {
    ONE_TRUCK_PER_PARCEL(1),
    WIDE_FIRST(2),
    BALANCED(3);

    private final Integer algorithmNumber;
}
