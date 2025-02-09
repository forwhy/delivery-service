package ru.hofftech.parcelsloader.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Operation {
    LOAD_PARCELS("Погрузка"),
    UNLOAD_PARCELS("Разгрузка");

    private final String operationName;
}
