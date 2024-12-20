package ru.hofftech.delivery.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SingleParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {
    private final int PARCEL_START_ROW_NUMBER = 0;
    private final int PARCEL_START_COLUMN_NUMBER = 0;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels) {
        var trucks = new ArrayList<Truck>();

        for (var parcel : parcels) {
                trucks.add(getLoadedTruck(parcel, parcel.getNumber()));
        }

        return trucks;
    }

    /**
     * Грузит посылку в грузовик.
     *
     * @param parcel Посылка для погрузки.
     * @param truckNumber Порядковый номер грузовика.
     * @return Грузовик с посылкой.
     */
    private Truck getLoadedTruck(Parcel parcel, int truckNumber) {
        var truck = new Truck(truckNumber);
        log.info("Truck #{} created.", parcel.getNumber());

        validateTruckCapacityEnoughForParcel(truck, parcel);
        loadTruck(truck, parcel);

        return truck;
    }

    private void validateTruckCapacityEnoughForParcel(Truck truck, Parcel parcel) {
        if (!truck.isEmptyTruckCapacityEnoughForParcel(parcel)) {
            logPlacementError(parcel, truck);
            throw new InvalidAttemptToPutParcelIntoTruckException();
        }
    }

    private void loadTruck(Truck truck, Parcel parcel) {
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
            logPlacementError(parcel, truck);
            throw new IllegalArgumentException("Can't place parcel into a truck");
        }
    }

    private void logPlacementError(Parcel parcel, Truck truck) {
        log.error(
                "Can't place parcel with size {}x{} into a truck with size {}x{}.",
                parcel.getParcelWidth(),
                parcel.getParcelHeight(),
                truck.getWidth(),
                truck.getHeight());
    }
}
