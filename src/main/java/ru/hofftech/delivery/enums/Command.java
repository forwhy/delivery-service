package ru.hofftech.delivery.enums;

import lombok.Getter;

@Getter
public enum Command {
    IMPORT_TRUCKS_EXPORT_PARCELS(1),
    IMPORT_PARCELS_EXPORT_TRUCKS(2);

    private final Integer commandNumber;

    Command(Integer commandNumber) {
        this.commandNumber = commandNumber;
    }
}
