package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

@RequiredArgsConstructor
public class ExportOutputService {

    public void printParcelsPlacementResult(LoadingAlgorithm algorithm, List<Truck> loadedTrucks) {

        System.out.printf(
                "\nTruck loading result using %s algorithm:\n%n",
                algorithm.name());

        for (var truck : loadedTrucks) {

            System.out.println("Truck #" + truck.getNumber() + ":");
            System.out.println(truck);
        }
    }
}
