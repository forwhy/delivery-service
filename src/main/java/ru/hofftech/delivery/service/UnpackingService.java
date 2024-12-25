package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.model.dto.PlacedParcelDto;
import ru.hofftech.delivery.service.output.ParcelsExportingService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UnpackingService {

    private final TrucksParserService trucksParserService;
    private final TruckParameterReaderService truckParameterReaderService;
    private final ParcelsExportingService parcelsExportingService;

    public void start() {
        String filePath = truckParameterReaderService.readFilePath();

        try {
            List<PlacedParcelDto> parcels = trucksParserService.parseTrucksFile(filePath).stream()
                    .flatMap(t -> t.getParcels().stream())
                    .toList();
            parcelsExportingService.exportParcelsToTxtFile(parcels);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
    }
}
