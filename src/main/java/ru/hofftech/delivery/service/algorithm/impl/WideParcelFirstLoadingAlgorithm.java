package ru.hofftech.delivery.service.algorithm.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.delivery.exception.TruckMatrixPositionOutOfRangeException;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class WideParcelFirstLoadingAlgorithm implements ParcelLoadingAlgorithm {

    private final int NEXT_TRUCK_NUMBER_INCREMENT = 1;

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
            try {
                putParcelIntoTruck(parcel, truck);
                return;
            } catch (InvalidAttemptToPutParcelIntoTruckException | TruckMatrixPositionOutOfRangeException exception) {
                logPlacementErrorWarning(truck.getNumber(), parcel.getNumber());
            }
        }

        var truck = createTruck(trucks.size() + NEXT_TRUCK_NUMBER_INCREMENT);
        putParcelIntoTruck(parcel, truck);
        trucks.add(truck);
    }

    private void putParcelIntoTruck(Parcel parcel, Truck truck) {
        validateTruckAvailableVolumeForParcel(truck, parcel);

        var availablePositionForParcel = findAvailablePositionForParcel(truck, parcel);
        truck.putParcel(availablePositionForParcel, parcel);
        logParcelSuccessfullyPlaced(truck.getNumber(), parcel.getNumber(), availablePositionForParcel);
    }

    private MatrixPosition findAvailablePositionForParcel(Truck truck, Parcel parcel) {
        var placementPosition = truck.findNearestAvailablePosition(new MatrixPosition());

        while (placementPosition != null) {
            if (!truck.isTruckHeightAvailable(placementPosition, parcel.getParcelHeight())) {
                throw new InvalidAttemptToPutParcelIntoTruckException();
            }

            placementPosition = updatePlacementPositionConsideringNeededWidth(truck, parcel, placementPosition);
            if (truck.canPutParcel(placementPosition, parcel)) {
                return placementPosition;
            }

            placementPosition = truck.findNearestAvailablePosition(truck.findNextPosition(placementPosition));
        }

        throw new TruckMatrixPositionOutOfRangeException();
    }

    private MatrixPosition updatePlacementPositionConsideringNeededWidth(Truck truck, Parcel parcel, MatrixPosition currentPosition) {
        if (!truck.isTruckWidthAvailable(currentPosition, parcel.getParcelWidth())) {
            return new MatrixPosition(currentPosition.getNextRowNumber(), MatrixPosition.DEFAULT_START_COLUMN);
        }
        return currentPosition;
    }

    private void validateTruckAvailableVolumeForParcel(Truck truck, Parcel parcel) {
        if (truck.getAvailableVolume() < parcel.getParcelVolume()) {
            throw new InvalidAttemptToPutParcelIntoTruckException();
        }
    }

    private Truck createTruck(int truckNumber) {
        logNewTruckCreation(truckNumber);
        return new Truck(truckNumber);
    }

    private void logPlacementErrorWarning(int truckNumber, int parcelNumber) {
        log.warn(
                "Can't place parcel #{} into a truck #{}.",
                parcelNumber,
                truckNumber);
    }

    private void logParcelSuccessfullyPlaced(int truckNumber, int parcelNumber, MatrixPosition placementPosition) {
        log.info(
                "Truck #{}: parcel #{} placed at [{}:{}].",
                truckNumber,
                parcelNumber,
                placementPosition.getRowNumber(),
                placementPosition.getColumnNumber());
    }

    private void logNewTruckCreation(int truckNumber) {
        log.info("Creating new truck #{}", truckNumber);
    }
}
