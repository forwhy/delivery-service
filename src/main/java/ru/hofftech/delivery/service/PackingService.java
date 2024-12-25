package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.dto.LoadingOptionsDto;
import ru.hofftech.delivery.service.output.ExportToJsonService;
import ru.hofftech.delivery.service.output.PrintService;

@Slf4j
@RequiredArgsConstructor
public class PackingService {

    private final ParcelsLoadingService parcelsLoadingService;
    private final ParcelParameterReaderService parcelParameterReaderService;
    private final ExportToJsonService exportToJsonService;
    private final ParcelsParserService parcelsParserService;
    private final PrintService printService;

    public void start() {
        String filePath = parcelParameterReaderService.readFilePath();
        LoadingAlgorithm loadingAlgorithm = parcelParameterReaderService.readLoadingAlgorithm();
        Integer trucksCountLimit = parcelParameterReaderService.readTrucksCountLimit();
        log.info("Start truck loading using {} algorithm", loadingAlgorithm.name());

        try {
            var parcels = parcelsParserService.parseParcelsFile(filePath);
            var loadedTrucks = parcelsLoadingService.loadTrucks(
                    parcels,
                    new LoadingOptionsDto(loadingAlgorithm, trucksCountLimit));
            exportToJsonService.exportParcelsPlacementResult(loadedTrucks, loadingAlgorithm);
            printService.exportParcelsPlacementResult(loadedTrucks, loadingAlgorithm);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println("Processing finished");
    }
}
