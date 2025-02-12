package ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.exception.TrucksOverflowException;
import ru.hofftech.deliveryservice.parcelsloader.model.constants.DefaultValues;
import ru.hofftech.deliveryservice.parcelsloader.model.MatrixPosition;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptionsDto;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.TrucksConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BalancedParcelLoadingAlgorithm implements ParcelLoadingAlgorithm {

    @Override
    public List<Truck> loadTrucks(List<Parcel> parcels, List<TruckOptionsDto> truckOptions) {
        List<Truck> trucks = TrucksConstructor.createTrucks(truckOptions);
        parcels.sort(Comparator.comparingInt(Parcel::getWidth).reversed());

        for (var parcel : parcels) {
            putParcelIntoAnySuitableTruck(parcel, trucks);
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
                        "Не удалось поместить посылку {} в грузовик размера {}.",
                        parcel.getName(),
                        truck.getTruckType());
            }
        }

        throw new TrucksOverflowException(
                String.format("Посылка %s не помещается ни в один из предоставленных грузовиков", parcel.getName()));
    }

    private List<Truck> removeUnusedTrucks(List<Truck> trucks) {
        return trucks.stream()
                .filter(truck -> !truck.getPlacedParcels().isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isParcelPutIntoTruck(Parcel parcel, Truck truck) {
        if (!isTruckAvailableVolumeForParcel(truck, parcel)) {

            return false;
        }

        var availablePositionForParcel = findAvailablePositionForParcel(truck, parcel);
        if (!isAvailablePositionForParcelFound(availablePositionForParcel)) {

            return false;
        }
        truck.putParcel(availablePositionForParcel, parcel);
        log.info(
                "Посылка {} успешно погружена в грузовик размера {}",
                parcel.getName(),
                truck.getTruckType());

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

    private boolean isAvailablePositionForParcelFound(MatrixPosition position) {
        return position != null;
    }
}
