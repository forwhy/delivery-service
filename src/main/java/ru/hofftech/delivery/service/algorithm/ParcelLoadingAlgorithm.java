package ru.hofftech.delivery.service.algorithm;

import ru.hofftech.delivery.exception.TrucksOverflowException;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

public interface ParcelLoadingAlgorithm {
    List<Truck> loadTrucks(List<Parcel> parcels, Integer trucksCountLimit);

    default void validateTrucksCountLimit(Integer trucksCountLimit, Integer trucksNeededCount) {
        if (trucksNeededCount > trucksCountLimit) {
            throw new TrucksOverflowException();
        }
    }
}
