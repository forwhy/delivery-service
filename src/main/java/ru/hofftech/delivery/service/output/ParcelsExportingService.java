package ru.hofftech.delivery.service.output;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.ExportToJsonFileException;
import ru.hofftech.delivery.model.dto.PlacedParcelDto;
import ru.hofftech.delivery.mapper.ParcelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ParcelsExportingService {

    private static final String OUTPUT_FILE_NAME = "parcels.txt";
    private final ParcelMapper parcelMapper;

    public void exportParcelsToTxtFile(List<PlacedParcelDto> parcels) {
        log.info("Exporting parcels to txt file {} started...", OUTPUT_FILE_NAME);

        try (FileWriter writer = new FileWriter(OUTPUT_FILE_NAME)) {
            writer.write(collectStringFromParcels(parcels));
            log.info("Exporting parcels to txt file {} finished.", OUTPUT_FILE_NAME);
        } catch (IOException e) {
            throw new ExportToJsonFileException(e.getMessage());
        }
    }

    private String collectStringFromParcels(List<PlacedParcelDto> parcels) {
        return parcels.stream()
                .map(parcelMapper::mapPlacedParcelDtoToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
