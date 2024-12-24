package ru.hofftech.delivery.controller.impl;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.controller.ProcessingController;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.dto.LoadingOptionsDto;
import ru.hofftech.delivery.service.ParcelParameterReaderService;
import ru.hofftech.delivery.service.ParcelsLoadingService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.output.ExportService;

@RequiredArgsConstructor
public class ParcelController implements ProcessingController {
    private final ParcelsLoadingService parcelsLoadingService;
    private final ParcelParameterReaderService parcelParameterReaderService;
    private final ExportService exportService;
    private final ParcelsParserService parcelsParserService;

    public void start() {
        String filePath = parcelParameterReaderService.readFilePath();

        while (!filePath.isEmpty()) {
            LoadingAlgorithm loadingAlgorithm = parcelParameterReaderService.readLoadingAlgorithm();
            Integer trucksCountLimit = parcelParameterReaderService.readTrucksCountLimit();
            System.out.printf("%nStart truck loading using %s algorithm:%n", loadingAlgorithm.name());

            try {
                var parcels = parcelsParserService.parseParcelsFile(filePath);
                var loadedTrucks = parcelsLoadingService.loadTrucks(
                        parcels,
                        new LoadingOptionsDto(loadingAlgorithm, trucksCountLimit));
                exportService.exportParcelsPlacementResult(loadedTrucks, loadingAlgorithm);

                filePath = parcelParameterReaderService.readFilePath();
            } catch (Exception exception) {
                System.out.printf(exception.getMessage());
            }
        }
        System.out.println("Processing finished");
    }
}
