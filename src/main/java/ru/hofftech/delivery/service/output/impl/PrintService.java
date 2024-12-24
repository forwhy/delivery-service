package ru.hofftech.delivery.service.output.impl;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.service.output.ExportService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PrintService implements ExportService {

    public void exportParcelsPlacementResult(List<Truck> loadedTrucks, LoadingAlgorithm algorithm) {

        System.out.printf(
                "%nTruck loading result using %s algorithm:%n",
                algorithm.name());

        for (var truck : loadedTrucks) {

            System.out.println("Truck #" + truck.getNumber() + ":");
            System.out.println(truck);
        }
    }
}
