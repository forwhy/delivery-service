package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.service.validation.ParcelValidationService;
import ru.hofftech.delivery.util.FileReader;
import ru.hofftech.delivery.model.mapper.ParcelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class ParcelsParserService {
    private static final Integer PARCEL_NUMERATION_START = 1;
    private static final String PARCEL_LINE_DELIMITER = "%n";
    private final ParcelValidationService parcelValidationService;
    private final FileReader fileReader;
    private final ParcelMapper parcelMapper;

    public List<Parcel> parseParcelsFile(String fileName) {
        var fileLines = fileReader.readAllLines(fileName);

        if (fileLines.isEmpty()) {
            throw new IllegalArgumentException("No data to process");
        }
        return extractParcels(fileLines);
    }

    private List<Parcel> extractParcels(List<String> fileLines) {
        var parcelString = new StringBuilder();
        var parcelNumber = new AtomicInteger(PARCEL_NUMERATION_START);
        var parcels = new ArrayList<Parcel>();

        for (var line : fileLines) {
            if (!isLineOnlyContainsWhitespaces(line)) {
                parcelString.append(line).append(PARCEL_LINE_DELIMITER);
            } else {
                parcelValidationService.validateParcelString(parcelString.toString());
                parcels.add(parcelMapper.mapStringToParcel(parcelString.toString(), parcelNumber.getAndIncrement()));
                parcelString = new StringBuilder();
            }
        }

        log.info("{} valid parcels successfully parsed", parcels.size());
        return parcels;
    }

    private boolean isLineOnlyContainsWhitespaces(String line) {
        return line.trim().isEmpty();
    }
}
