package ru.hofftech.parcelsloader.service.loading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.parcelsloader.exception.ExportToJsonFileException;
import ru.hofftech.parcelsloader.mapper.TruckMapper;
import ru.hofftech.parcelsloader.model.Truck;
import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.parcelsloader.model.dto.LoadedTrucksDto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadingResultExporter {

    private static final String EXPORT_FILE_EXTENSION = "json";
    private final TruckMapper truckMapper;

    public String exportParcelsPlacementResultAsText(List<Truck> loadedTrucks, LoadingAlgorithm algorithm) {
        StringBuilder result = new StringBuilder();

        result.append("Результаты погрузки с использованием алгоритма ")
                .append(algorithm.name())
                .append(System.lineSeparator());

        for (var truck : loadedTrucks) {
            result.append("Грузовик ")
                    .append(truck.getTruckType())
                    .append(":")
                    .append(System.lineSeparator())
                    .append(truck);
        }

        return result.toString();
    }

    public String exportParcelsPlacementResultAsFile(
            List<Truck> loadedTrucks,
            LoadingAlgorithm algorithm,
            String fileName) {
        String outputFileName = defineExportFileName(fileName);
        List<LoadedTruckDto> trucks = initializeTrucksDto(loadedTrucks);

        try (FileWriter writer = new FileWriter(outputFileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LoadedTrucksDto(trucks), writer);

            return String.format("Результат погрузки алгоритма %s сохранён в файл %s", algorithm.name(), outputFileName);
        } catch (IOException e) {
            throw new ExportToJsonFileException(String.format(
                    "Возникла ошибка при попытке выгрузить результаты погрузки в файл: %s",
                    e.getMessage()));
        }
    }

    private List<LoadedTruckDto> initializeTrucksDto(List<Truck> loadedTrucks) {
        List<LoadedTruckDto> trucks = new ArrayList<>();
        for (Truck truck : loadedTrucks) {
            trucks.add(truckMapper.mapTruckToDto(truck));
        }

        return trucks;
    }

    private String defineExportFileName(String fileName) {
        return "%s.%s".formatted(fileName, EXPORT_FILE_EXTENSION);
    }
}
