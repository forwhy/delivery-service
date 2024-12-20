package ru.hofftech.delivery.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.service.ConsoleReaderService;
import ru.hofftech.delivery.service.ExportOutputService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.ParcelsLoadingService;

@RequiredArgsConstructor
public class ConsoleController {
    private final ParcelsLoadingService parcelsLoadingService;
    private final ConsoleReaderService consoleReaderService;
    private final ExportOutputService exportOutputService;
    private final ParcelsParserService parcelsParserService;

    public void listen() {

        var filePath = consoleReaderService.getFilePath();
        var loadingAlgorithm = consoleReaderService.getLoadingAlgorithm();

        System.out.printf("\nStart truck loading using %s algorithm:\n%n", loadingAlgorithm.name());

        try {
            var parcels = parcelsParserService.parseParcelsFile(filePath);

            var loadedTrucks = parcelsLoadingService.loadTrucks(
                    parcels,
                    loadingAlgorithm);
            exportOutputService.printParcelsPlacementResult(loadingAlgorithm, loadedTrucks);
        } catch (RuntimeException exception) {
            System.out.printf(exception.getMessage());
        }

        System.out.println("Processing finished");
    }
}
