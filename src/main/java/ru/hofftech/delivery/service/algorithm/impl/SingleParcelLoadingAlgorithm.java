package ru.hofftech.delivery.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.delivery.exception.TrucksOverflowException;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SingleParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {

    private static final Integer PARCEL_START_ROW_NUMBER = 0;
    private static final Integer PARCEL_START_COLUMN_NUMBER = 0;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels, Integer trucksCountLimit) {
        validateTrucksCountLimit(trucksCountLimit, parcels.size());

        var trucks = new ArrayList<Truck>();
        for (var parcel : parcels) {
            trucks.add(loadNewTruck(parcel, parcel.getNumber()));
        }

        return trucks;
    }

    private void validateTrucksCountLimit(Integer trucksCountLimit, Integer trucksNeededCount) {
        if (trucksNeededCount > trucksCountLimit) {
            throw new TrucksOverflowException(trucksNeededCount, trucksCountLimit);
        }
    }

    private Truck loadNewTruck(Parcel parcel, Integer truckNumber) {
        var truck = createNewTruck(truckNumber);
        validateTruckCapacityEnoughForParcel(truck, parcel);

        return putParcelIntoTruck(truck, parcel);
    }

    private void validateTruckCapacityEnoughForParcel(Truck truck, Parcel parcel) {
        if (!truck.isEmptyTruckCapacityEnoughForParcel(parcel)) {
            throw new InvalidAttemptToPutParcelIntoTruckException(
                    "Invalid attempt to put parcel of size %dx%d into truck #%d of size %dx%d".formatted(
                            parcel.getHeight(),
                            parcel.getWidth(),
                            truck.getNumber(),
                            truck.getHeight(),
                            truck.getWidth())
            );
        }
    }

    private Truck putParcelIntoTruck(Truck truck, Parcel parcel) {
        var parcelsPosition = new MatrixPosition(PARCEL_START_ROW_NUMBER, PARCEL_START_COLUMN_NUMBER);

        if (truck.canPutParcel(parcelsPosition, parcel)) {
            truck.putParcel(parcelsPosition, parcel);
            log.info(
                    "Truck #{}: parcel #{} placed at [{}:{}].",
                    truck.getNumber(),
                    parcel.getNumber(),
                    PARCEL_START_ROW_NUMBER,
                    PARCEL_START_COLUMN_NUMBER);
        } else {
            throw new IllegalArgumentException("Can't place parcel into a truck");
        }

        return truck;
    }

    private Truck createNewTruck(Integer truckNumber) {
        log.info("Creating new truck #{}", truckNumber);

        return new Truck(truckNumber);
    }
}
