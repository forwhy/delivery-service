package ru.hofftech.parcelsloader.service.algorithm;

import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.Truck;

import java.util.List;

public interface ParcelLoadingAlgorithm {

    List<Truck> loadTrucks(List<Parcel> parcels, List<Truck> trucks);
}
