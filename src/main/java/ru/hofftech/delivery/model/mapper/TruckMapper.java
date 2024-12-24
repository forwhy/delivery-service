package ru.hofftech.delivery.model.mapper;

import ru.hofftech.delivery.model.dto.LoadedTruckDto;
import ru.hofftech.delivery.model.entity.Truck;

public class TruckMapper {

    public LoadedTruckDto mapTruckToDto(Truck truck) {
        return new LoadedTruckDto(truck.getNumber(), truck.getPlacedParcels());
    }
}
