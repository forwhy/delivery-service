package ru.hofftech.delivery.service.output;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

@RequiredArgsConstructor
public class PrintService {

    public void printParcelsPlacementResult(List<Truck> loadedTrucks, LoadingAlgorithm algorithm) {
        System.out.printf(
                "%nTruck loading result using %s algorithm:%n",
                algorithm.name());

        for (var truck : loadedTrucks) {
            System.out.println("Truck #" + truck.getNumber() + ":");
            System.out.println(truck);
        }
    }
}
