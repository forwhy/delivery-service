package ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.deliveryservice.parcelsloader.exception.TrucksOverflowException;
import ru.hofftech.deliveryservice.parcelsloader.model.MatrixPosition;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptions;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.TrucksConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SingleParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {

    private static final Integer PARCEL_START_ROW_NUMBER = 0;
    private static final Integer PARCEL_START_COLUMN_NUMBER = 0;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels, List<TruckOptions> truckOptions) {
        List<Truck> trucks = TrucksConstructor.createTrucks(truckOptions);
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

    private void validateTrucksCount(Integer trucksCount, Integer parcelsCount) {
        if (parcelsCount > trucksCount) {
            throw new TrucksOverflowException(
                    String.format(
                            "Недостаточное количество грузовиков для алгоритма: предоставлено %d грузовиков, но %d посылок",
                            trucksCount, parcelsCount));
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
