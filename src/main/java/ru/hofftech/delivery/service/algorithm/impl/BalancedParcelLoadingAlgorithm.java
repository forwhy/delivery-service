package ru.hofftech.delivery.service.algorithm.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BalancedParcelLoadingAlgorithm extends WideParcelFirstLoadingAlgorithm implements ParcelLoadingAlgorithm {

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

    @Override
    protected void putParcelIntoAnySuitableTruck(Parcel parcel, List<Truck> trucks) {
        trucks.sort(Comparator.comparingInt(Truck::getAvailableVolume).reversed());

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

    private List<Truck> initializeTrucksBatch(Integer count) {
        var trucks = new ArrayList<Truck>();
        while (trucks.size() < count) {
            var newTruckNumber = trucks.size() + NEXT_TRUCK_NUMBER_INCREMENT;
            log.info("Creating new truck #{}", newTruckNumber);
            trucks.add(new Truck(newTruckNumber));
        }
        return trucks;
    }

    private List<Truck> removeUnusedTrucks(List<Truck> trucks) {
        return trucks.stream()
                .filter(truck -> truck.getAvailableVolume() < truck.getHeight() * truck.getWidth())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
