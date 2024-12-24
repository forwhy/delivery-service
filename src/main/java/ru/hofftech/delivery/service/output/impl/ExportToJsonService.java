package ru.hofftech.delivery.service.output.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.ExportToJsonFileException;
import ru.hofftech.delivery.model.dto.LoadedTruckDto;
import ru.hofftech.delivery.model.dto.LoadedTrucksDto;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.model.mapper.TruckMapper;
import ru.hofftech.delivery.service.output.ExportService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExportToJsonService implements ExportService {
    private static final String EXPORT_FILE_EXTENSION = "json";
    private final TruckMapper truckMapper;

    @Override
    public void exportParcelsPlacementResult(List<Truck> loadedTrucks, LoadingAlgorithm algorithm) {
        String outputFileName = defineExportFileName(algorithm);
        log.info("Exporting loading result to json file %s started...".formatted(outputFileName));
        List<LoadedTruckDto> trucks = initializeTrucksDto(loadedTrucks);

        try (FileWriter writer = new FileWriter(outputFileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LoadedTrucksDto(trucks), writer);
            log.info("Exporting loading result to json file %s finished.".formatted(outputFileName));
        } catch (IOException e) {
            throw new ExportToJsonFileException(e.getMessage());
        }
    }

    private List<LoadedTruckDto> initializeTrucksDto(List<Truck> loadedTrucks) {
        List<LoadedTruckDto> trucks = new ArrayList<>();
        for (Truck truck : loadedTrucks) {
            trucks.add(truckMapper.mapTruckToDto(truck));
        }

        return trucks;
    }

    private String defineExportFileName(LoadingAlgorithm algorithm) {
        return "%s.%s".formatted(algorithm.name(), EXPORT_FILE_EXTENSION);
    }
}
