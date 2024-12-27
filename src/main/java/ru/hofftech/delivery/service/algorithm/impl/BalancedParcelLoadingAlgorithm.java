package ru.hofftech.delivery.service.algorithm.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.TrucksOverflowException;
import ru.hofftech.delivery.model.DefaultValues;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BalancedParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {

    private static final Integer NEXT_TRUCK_NUMBER_INCREMENT = 1;

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels, Integer trucksCountLimit) {
        var trucks = initializeTrucksBatch(trucksCountLimit);
        parcels.sort(Comparator.comparingInt(Parcel::getWidth).reversed());

        for (var parcel : parcels) {
            putParcelIntoAnySuitableTruck(parcel, trucks);
            validateTrucksCountLimit(trucksCountLimit, trucks.size());
        }

        return removeUnusedTrucks(trucks);
    }

    private void putParcelIntoAnySuitableTruck(Parcel parcel, List<Truck> trucks) {
        trucks.sort(Comparator.comparingInt(Truck::getAvailableVolume).reversed());

        for (var truck : trucks) {
            if (isParcelPutIntoTruck(parcel, truck)) {

                return;
            } else {
                log.warn(
                        "Can't place parcel #{} into a truck #{}.",
                        parcel.getNumber(),
                        truck.getNumber());
            }
        }

        var truck = createNewTruck(trucks.size() + NEXT_TRUCK_NUMBER_INCREMENT);
        isParcelPutIntoTruck(parcel, truck);
        trucks.add(truck);
    }

    private List<Truck> initializeTrucksBatch(Integer count) {
        var trucks = new ArrayList<Truck>();
        while (trucks.size() < count) {
            trucks.add(createNewTruck(trucks.size() + NEXT_TRUCK_NUMBER_INCREMENT));
        }

        return trucks;
    }

    private Truck createNewTruck(Integer truckNumber) {
        log.info("Creating new truck #{}", truckNumber);

        return new Truck(truckNumber);
    }

    private List<Truck> removeUnusedTrucks(List<Truck> trucks) {
        return trucks.stream()
                .filter(truck -> truck.getAvailableVolume() < truck.getHeight() * truck.getWidth())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isParcelPutIntoTruck(Parcel parcel, Truck truck) {
        if (!isTruckAvailableVolumeForParcel(truck, parcel)) {

            return false;
        }

        var availablePositionForParcel = findAvailablePositionForParcel(truck, parcel);
        if (isAvailablePositionForParcelFound(availablePositionForParcel)) {

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

            if (truck.isTruckHeightNotAvailable(placementPosition, parcel.getHeight())) {

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
        if (truck.isTruckWidthNotAvailable(currentPosition, parcel.getWidth())) {

            return new MatrixPosition(currentPosition.getNextRowNumber(), DefaultValues.DEFAULT_START_COLUMN);
        }

        return currentPosition;
    }

    private boolean isTruckAvailableVolumeForParcel(Truck truck, Parcel parcel) {
        return truck.getAvailableVolume() >= parcel.getVolume();
    }

    private void validateTrucksCountLimit(Integer trucksCountLimit, Integer trucksNeededCount) {
        if (trucksNeededCount > trucksCountLimit) {
            throw new TrucksOverflowException(trucksNeededCount, trucksCountLimit);
        }
    }

    private boolean isAvailablePositionForParcelFound(MatrixPosition position) {
        return position != null;
    }
}
