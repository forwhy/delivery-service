package ru.hofftech.deliveryservice.billing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление типов операций к оплате
 */
@RequiredArgsConstructor
@Getter
public enum Operation {
    LOAD_PARCELS("Погрузка"),
    UNLOAD_PARCELS("Разгрузка");

    private final String operationName;
}
