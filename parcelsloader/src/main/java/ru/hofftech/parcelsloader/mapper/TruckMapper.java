package ru.hofftech.parcelsloader.mapper;

import org.springframework.stereotype.Component;
import ru.hofftech.parcelsloader.model.Truck;
import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;

@Component
public class TruckMapper {

    public LoadedTruckDto mapTruckToDto(Truck truck) {
        return new LoadedTruckDto(truck.getTruckType(), truck.getPlacedParcels());
    }
}
