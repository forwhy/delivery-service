package ru.hofftech.delivery.service.output;

import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

public interface ExportService {

    void exportParcelsPlacementResult(List<Truck> loadedTrucks, LoadingAlgorithm algorithm);
}
