package ru.hofftech.delivery.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.service.CommandReaderService;
import ru.hofftech.delivery.service.PackingService;
import ru.hofftech.delivery.service.ParcelParameterReaderService;
import ru.hofftech.delivery.service.TruckParameterReaderService;
import ru.hofftech.delivery.service.UnpackingService;
import ru.hofftech.delivery.service.output.ExportToJsonService;
import ru.hofftech.delivery.service.output.ParcelsExportingService;
import ru.hofftech.delivery.service.output.PrintService;

@RequiredArgsConstructor
public class ConsoleController {

    private final CommandReaderService commandReaderService;
    private final PackingService packingService;
    private final UnpackingService unpackingService;
    private final ParcelParameterReaderService parcelParameterReaderService;
    private final TruckParameterReaderService truckParameterReaderService;
    private final ExportToJsonService exportToJsonService;
    private final PrintService printService;
    private final ParcelsExportingService parcelsExportingService;

    public void listen() {
        var isWorking = true;

        while (isWorking) {
            Command command = commandReaderService.readCommand();

            switch (command) {
                case IMPORT_TRUCKS_EXPORT_PARCELS -> {
                    try {
                        String filePath = truckParameterReaderService.readFilePath();
                        var parcels = unpackingService.unpack(filePath);
                        parcelsExportingService.exportParcelsToTxtFile(parcels);
                    } catch (Exception exception) {
                        System.out.printf(exception.getMessage());
                    }
                }
                case IMPORT_PARCELS_EXPORT_TRUCKS -> {
                    try {
                        String filePath = parcelParameterReaderService.readFilePath();
                        LoadingAlgorithm loadingAlgorithm = parcelParameterReaderService.readLoadingAlgorithm();
                        Integer trucksCountLimit = parcelParameterReaderService.readTrucksCountLimit();

                        var loadedTrucks = packingService.pack(filePath, loadingAlgorithm, trucksCountLimit);
                        exportToJsonService.exportParcelsPlacementResult(loadedTrucks, loadingAlgorithm);
                        printService.printParcelsPlacementResult(loadedTrucks, loadingAlgorithm);
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                    System.out.println("Processing finished");
                }
                case EXIT -> isWorking = false;
            }
        }
    }
}
