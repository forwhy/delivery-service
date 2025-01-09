package ru.hofftech.delivery.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Command {
    IMPORT_TRUCKS_EXPORT_PARCELS(1),
    IMPORT_PARCELS_EXPORT_TRUCKS(2),
    EXIT(3);

    private final Integer commandNumber;
}
