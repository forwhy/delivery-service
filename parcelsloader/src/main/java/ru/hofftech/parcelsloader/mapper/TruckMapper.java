package ru.hofftech.parcelsloader.mapper;

import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.parcelsloader.model.Truck;

public class TruckMapper {

    public LoadedTruckDto mapTruckToDto(Truck truck) {
        return new LoadedTruckDto(truck.getTruckType(), truck.getPlacedParcels());
    }
}
