package ru.hofftech.deliveryservice.parcelsloader.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadInputMode;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadOutputMode;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.LoadTrucksOptions;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptions;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadTrucksCommandMapper {

    private static final String TRUCK_OPTIONS_DELIMITER = "%n";
    private static final String TRUCK_HEIGHT_WIDTH_SEPARATOR_EN = "x";
    private static final String TRUCK_HEIGHT_WIDTH_SEPARATOR_RU = "х";
    private static final Integer TRUCK_WIDTH_INDEX = 0;
    private static final Integer TRUCK_HEIGHT_INDEX = 1;
    private static final String OUT_FILE_FORMAT = "json-file";
    private final LoadingAlgorithmMapper loadingAlgorithmMapper;

    public LoadTrucksOptions toLoadTrucksCommand(ru.hofftech.deliveryservice.parcelsloader.model.dto.request.LoadTrucksCommandDto loadTrucksCommandDto) {
        LoadInputMode inputMode = isStringParameterDefined(loadTrucksCommandDto.parcelsText())
                ? LoadInputMode.TEXT
                : LoadInputMode.FILE;

        return new LoadTrucksOptions(
                loadTrucksCommandDto.user(),
                inputMode,
                inputMode == LoadInputMode.TEXT
                        ? loadTrucksCommandDto.parcelsText()
                        : loadTrucksCommandDto.parcelsFile(),
                parseTruckOptions(loadTrucksCommandDto.trucks()),
                loadingAlgorithmMapper.mapNameToLoadingAlgorithm(loadTrucksCommandDto.type()),
                defineLoadOutputMode(loadTrucksCommandDto.out()),
                loadTrucksCommandDto.outFilename());
    }

    private Boolean isStringParameterDefined(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }

    private List<TruckOptions> parseTruckOptions(String truckVariants) {
        List<TruckOptions> truckOptionDtos = new ArrayList<>();
        String[] truckOptionStrings = truckVariants.split(TRUCK_OPTIONS_DELIMITER);

        for (String truckOptionString : truckOptionStrings) {
            truckOptionDtos.add(extractTruckOptions(truckOptionString));
        }

        return truckOptionDtos;
    }

    private TruckOptions extractTruckOptions(String truckOptionString) {
        String[] truckDimension = truckOptionString.split(
                String.format("[%s%s]",
                        TRUCK_HEIGHT_WIDTH_SEPARATOR_EN,
                        TRUCK_HEIGHT_WIDTH_SEPARATOR_RU));
        try {
            Integer width = Integer.parseInt(truckDimension[TRUCK_WIDTH_INDEX].trim());
            Integer height = Integer.parseInt(truckDimension[TRUCK_HEIGHT_INDEX].trim());
            return new TruckOptions(height, width);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(
                    String.format("Ошибка при разборе размера грузовика %s: %s",
                            truckOptionString,
                            e.getMessage()));
        }
    }

    private LoadOutputMode defineLoadOutputMode(String out) {
        if (out.equals(OUT_FILE_FORMAT)) {
            return LoadOutputMode.FILE;
        }
        return LoadOutputMode.TEXT;
    }
}
