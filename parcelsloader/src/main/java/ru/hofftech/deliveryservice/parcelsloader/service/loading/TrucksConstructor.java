package ru.hofftech.deliveryservice.parcelsloader.service.loading;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptions;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TrucksConstructor {

    public static List<Truck> createTrucks(List<TruckOptions> truckOptionDtos) {
        List<Truck> trucks = new ArrayList<>();

        for (TruckOptions truckOption : truckOptionDtos) {
            trucks.add(new Truck(truckOption.width(), truckOption.height()));
        }
        return trucks;
    }
}
