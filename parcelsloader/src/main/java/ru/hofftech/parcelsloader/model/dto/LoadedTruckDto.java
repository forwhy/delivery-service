package ru.hofftech.parcelsloader.model.dto;

import java.io.Serializable;
import java.util.List;

public record LoadedTruckDto(String truckType,
                             List<PlacedParcelDto> parcels) implements Serializable {
}
