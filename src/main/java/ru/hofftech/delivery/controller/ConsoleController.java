package ru.hofftech.delivery.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.service.ConsoleReaderService;
import ru.hofftech.delivery.service.PrintService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.ParcelsLoadingService;

@RequiredArgsConstructor
public class ConsoleController {
    private final ParcelsLoadingService parcelsLoadingService;
    private final ConsoleReaderService consoleReaderService;
    private final PrintService printService;
    private final ParcelsParserService parcelsParserService;

    public void listen() {
        String filePath = consoleReaderService.getFilePath();

        while (!filePath.isEmpty()) {
            LoadingAlgorithm loadingAlgorithm = consoleReaderService.getLoadingAlgorithm();
            System.out.printf("%nStart truck loading using %s algorithm:%n", loadingAlgorithm.name());

            try {
                var parcels = parcelsParserService.parseParcelsFile(filePath);
                var loadedTrucks = parcelsLoadingService.loadTrucks(
                        parcels,
                        loadingAlgorithm);
                printService.printParcelsPlacementResult(loadingAlgorithm, loadedTrucks);

                filePath = consoleReaderService.getFilePath();
            } catch (Exception exception) {
                System.out.printf(exception.getMessage());
            }
        }
        System.out.println("Processing finished");
    }
}
