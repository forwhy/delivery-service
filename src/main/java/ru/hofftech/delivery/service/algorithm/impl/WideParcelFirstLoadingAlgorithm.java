package ru.hofftech.delivery.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class WideParcelFirstLoadingAlgorithm implements ParcelLoadingAlgorithm {
    private static final Integer NEXT_TRUCK_NUMBER_INCREMENT = 1;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels) {
        var trucks = new ArrayList<Truck>();
        parcels.sort(Comparator.comparingInt(Parcel::getParcelWidth).reversed());

        for (var parcel : parcels) {
            putParcelIntoAnySuitableTruck(parcel, trucks);
        }

        return trucks;
    }

    private void putParcelIntoAnySuitableTruck(Parcel parcel, List<Truck> trucks) {
        for (var truck : trucks) {
            if (putParcelIntoTruck(parcel, truck)) {
                return;
            } else {
                log.warn(
                        "Can't place parcel #{} into a truck #{}.",
                        parcel.getNumber(),
                        truck.getNumber());
            }
        }

        var newTruckNumber = trucks.size() + NEXT_TRUCK_NUMBER_INCREMENT;
        log.info("Creating new truck #{}", newTruckNumber);
        var truck = new Truck(newTruckNumber);
        putParcelIntoTruck(parcel, truck);
        trucks.add(truck);
    }

    private boolean putParcelIntoTruck(Parcel parcel, Truck truck) {
        if (!isTruckAvailableVolumeForParcel(truck, parcel)) {
            return false;
        }

        var availablePositionForParcel = findAvailablePositionForParcel(truck, parcel);
        if (availablePositionForParcel == null) {
            return false;
        }
        truck.putParcel(availablePositionForParcel, parcel);
        log.info(
                "Truck #{}: parcel #{} placed at [{}:{}].",
                truck.getNumber(),
                parcel.getNumber(),
                availablePositionForParcel.getRowNumber(),
                availablePositionForParcel.getColumnNumber());
        return true;
    }

    private MatrixPosition findAvailablePositionForParcel(Truck truck, Parcel parcel) {
        var placementPosition = truck.findNearestAvailablePosition(new MatrixPosition());

        while (placementPosition != null) {
            placementPosition = updatePlacementPositionConsideringNeededWidth(truck, parcel, placementPosition);

            if (!truck.isTruckHeightAvailable(placementPosition, parcel.getParcelHeight())) {
                return null;
            }

            if (truck.canPutParcel(placementPosition, parcel)) {
                return placementPosition;
            }

            placementPosition = truck.findNearestAvailablePosition(truck.findNextPosition(placementPosition));
        }

        return null;
    }

    private MatrixPosition updatePlacementPositionConsideringNeededWidth(Truck truck, Parcel parcel, MatrixPosition currentPosition) {
        if (!truck.isTruckWidthAvailable(currentPosition, parcel.getParcelWidth())) {
            return new MatrixPosition(currentPosition.getNextRowNumber(), MatrixPosition.DEFAULT_START_COLUMN);
        }
        return currentPosition;
    }

    private boolean isTruckAvailableVolumeForParcel(Truck truck, Parcel parcel) {
        return truck.getAvailableVolume() >= parcel.getParcelVolume();
    }
}
