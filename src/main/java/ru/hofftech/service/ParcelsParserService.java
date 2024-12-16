package ru.hofftech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.model.dto.ParcelDto;
import ru.hofftech.util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParcelsParserService {
    private static final Logger logger = LoggerFactory.getLogger(ParcelsParserService.class);
    private final ParcelValidationService parcelValidationService;
    private final FileReader fileReader;

    public ParcelsParserService(ParcelValidationService parcelValidationService, FileReader fileReader) {
        this.parcelValidationService = parcelValidationService;
        this.fileReader = fileReader;
    }

    public List<ParcelDto> parseParcelsFile(String fileName) {

        var fileLines = fileReader.readAllLines(fileName);

        if (fileLines.isEmpty()) {
            logger.warn("No data to process");
            return Collections.emptyList();
        }

        return extractParcels(fileLines);
    }

    private ArrayList<ParcelDto> extractParcels(List<String> fileLines) {
        var parcelString = new StringBuilder();
        boolean isReadingParcel = false;
        var parcels = new ArrayList<ParcelDto>();

        for (var line : fileLines) {
            if (!isReadingParcel){
                if (line.trim().isEmpty()) {
                    continue;
                }
                isReadingParcel = true;
            }

            if (line.trim().isEmpty()) {
                tryExtractParcel(parcelString, parcels);
                parcelString = new StringBuilder();
                isReadingParcel = false;
            } else {
                parcelString.append(line).append("\n");
            }
        }

        if (isReadingParcel) {
            tryExtractParcel(parcelString, parcels);
        }
        return parcels;
    }

    private void tryExtractParcel(StringBuilder parcelString, ArrayList<ParcelDto> parcels) {
        if (!parcelValidationService.isParcelStringValid(parcelString.toString())) {
            System.out.printf("Invalid parcel will be skipped:\n%s%n", parcelString);
        }
        else {
            parcels.add(mapStringToParcelDto(parcelString));
        }
    }

    private ParcelDto mapStringToParcelDto(StringBuilder parcelString) {
        var parcelLines = Arrays.stream(parcelString.toString().split("\n")).toList();

        var parcelMatrix = new ArrayList<char[]>();

        for (var line : parcelLines.reversed()) {
            parcelMatrix.add(line.toCharArray());
        }

        return new ParcelDto(parcelMatrix);
    }
}
