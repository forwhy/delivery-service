package ru.hofftech.parcelsloader.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.parcelsloader.exception.TrucksOverflowException;
import ru.hofftech.parcelsloader.model.MatrixPosition;
import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.Truck;
import ru.hofftech.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SingleParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {

    private static final Integer PARCEL_START_ROW_NUMBER = 0;
    private static final Integer PARCEL_START_COLUMN_NUMBER = 0;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels, List<Truck> trucks)
            throws InvalidAttemptToPutParcelIntoTruckException, TrucksOverflowException {
        validateTrucksCount(trucks.size(), parcels.size());
        parcels.sort(Comparator.comparingInt(Parcel::getVolume).reversed());
        trucks.sort(Comparator.comparingInt(Truck::getAvailableVolume));

        List<Truck> loadedTrucks = new ArrayList<>(trucks.size());

        for (var parcel : parcels) {
            loadedTrucks.add(loadAnyTruckWithParcel(parcel, trucks));
            trucks.removeIf(truck -> !truck.getPlacedParcels().isEmpty());
        }

        return loadedTrucks;
    }

    private void validateTrucksCount(Integer trucksCount, Integer trucksNeededCount) {
        if (trucksNeededCount > trucksCount) {
            throw new TrucksOverflowException(trucksNeededCount, trucksCount);
        }
    }

    private Truck loadAnyTruckWithParcel(Parcel parcel, List<Truck> trucks) {
        for (var truck : trucks) {
            if (truck.isEmptyTruckCapacityEnoughForParcel(parcel)) {
                return putParcelIntoTruck(truck, parcel);
            } else {
                log.warn(
                        "Не удалось поместить посылку {} в грузовик размера {}.",
                        parcel.getName(),
                        truck.getTruckType());
            }
        }
        throw new InvalidAttemptToPutParcelIntoTruckException(
                String.format("Не удалось разместить посылку %s ни в один из грузовиков",
                        parcel.getName()));
    }

    private Truck putParcelIntoTruck(Truck truck, Parcel parcel) {
        var parcelsPosition = new MatrixPosition(PARCEL_START_ROW_NUMBER, PARCEL_START_COLUMN_NUMBER);

        if (truck.canPutParcel(parcelsPosition, parcel)) {
            truck.putParcel(parcelsPosition, parcel);
            log.info(
                    "Посылка {} успешно погружена в грузовик размера {}",
                    parcel.getName(),
                    truck.getTruckType());
        } else {
            throw new InvalidAttemptToPutParcelIntoTruckException(
                    String.format("При попытке разместить посылку %s в грузовик размера %s произошла ошибка",
                            parcel.getName(),
                            truck.getTruckType()));
        }

        return truck;
    }
}
