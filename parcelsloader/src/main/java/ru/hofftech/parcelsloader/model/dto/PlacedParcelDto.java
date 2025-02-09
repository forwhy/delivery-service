package ru.hofftech.parcelsloader.model.dto;

import java.io.Serializable;

public record PlacedParcelDto(String name,
                              Integer volume,
                              Integer startRow,
                              Integer startColumn) implements Serializable {
    @Override
    public String toString() {
        return String.format("\"%s\"", name);
    }
}
