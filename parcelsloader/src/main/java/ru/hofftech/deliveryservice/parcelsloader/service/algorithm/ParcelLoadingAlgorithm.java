package ru.hofftech.deliveryservice.parcelsloader.service.algorithm;

import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptionsDto;

import java.util.List;

public interface ParcelLoadingAlgorithm {

    List<Truck> loadTrucks(List<Parcel> parcels, List<TruckOptionsDto> truckOptions);
}
