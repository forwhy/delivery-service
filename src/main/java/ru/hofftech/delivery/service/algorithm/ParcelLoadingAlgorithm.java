package ru.hofftech.delivery.service.algorithm;

import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

public interface ParcelLoadingAlgorithm {

    public List<Truck> loadTrucks(List<Parcel> parcels);
}
